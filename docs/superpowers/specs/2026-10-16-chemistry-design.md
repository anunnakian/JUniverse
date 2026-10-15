# Chemistry layer — design

Date: 2026-10-16
Sub-project 1 of 4 in "describe every aspect of the Universe" (then Earth sciences, stellar evolution & black holes,
cosmology & time).

## Goal

Let JUniverse describe matter above the single atom: the full periodic table, compounds and molecules with their
bonds, balanced chemical reactions with their energy, mixtures, and aqueous solutions with their pH. Values are real
(IUPAC, NIST, CRC Handbook) and checked by tolerance-based tests, as everywhere else in the repo.

## Non-goals

- Reaction kinetics, equilibrium beyond Ka/Kb, electrochemistry, buffers and titration curves.
- 3D molecular geometry (VSEPR), resonance, formal charges.
- Solvents other than water for pH; temperatures other than 25 °C for pH.
- Aufbau exceptions (Cr, Cu...) stay unmodeled.

## Layout

- `com.enlil.universe.matter` — existing; `ChemicalElement` extended, new `Block`, `ElementCategory`.
- `com.enlil.universe.matter.chemistry` — new generic types: `ChemicalConstants`, `Formula`, `Compound`,
  `Molecule`, `Bond`, `BondOrder`, `BondType`, `Reaction`, `RationalMatrix` (package-private), `Mixture`,
  `Solution`, `Acidity`.
- `com.enlil.universe.world.chemistry` — real instances: `Compounds`, `Reactions`, `Mixtures`.

Chemistry depends only on `matter`; nothing in `matter` depends on `world`.

## Periodic table

`ChemicalElement` covers all 118 elements, hydrogen to oganesson, with no gap in atomic numbers.

Each constant has its symbol, atomic number, standard atomic weight, most abundant mass number, melting point and
boiling point (as today). New fields:

- `period` (1–7) and `group` (1–18) as a nullable `Integer` exposed as `OptionalInt getGroup()`, empty for the
  lanthanides and actinides, which IUPAC's 18-column layout gives no group number;
- `Block` (S, P, D, F), derived from the last subshell filled by `ElectronConfiguration`, not stored (so helium is
  in the s-block, its true electronic block, even though tables draw it above the noble gases);
- `ElementCategory`: ALKALI_METAL, ALKALINE_EARTH_METAL, TRANSITION_METAL, POST_TRANSITION_METAL, METALLOID,
  REACTIVE_NONMETAL, HALOGEN, NOBLE_GAS, LANTHANIDE, ACTINIDE, UNKNOWN (superheavy elements whose chemistry is
  unmeasured);
- Pauling electronegativity as a nullable `Double`, `@Getter(AccessLevel.NONE)`, exposed as `OptionalDouble
  getElectronegativity()`; empty for He, Ne, Ar and elements without a measured value.

Sources: IUPAC 2021 standard atomic weights (abridged, conventional values where an interval is given); for elements
without stable isotopes, the mass number of the longest-lived isotope, as the IUPAC table does. CRC Handbook melting
and boiling points; `NaN` where unknown. Pauling electronegativities from the CRC Handbook.

`Atom`, `Nucleus` and `ElectronConfiguration` need no change: `Subshell` already reaches 7p (118 electrons).
`StateOfMatter.of` already handles `NaN` points.

## Formula

Immutable. `Formula.parse(String)`:

- element symbols followed by an optional positive count: `H2O`, `C6H12O6`;
- nested groups in `()` or `[]` with a multiplier: `Ca(OH)2`, `K4[Fe(CN)6]`;
- hydrates joined by `·` or `*` with an optional leading multiplier: `CuSO4·5H2O`;
- an optional trailing charge: `SO4^2-`, `NH4^+`, `Na^+`.

Stored as an unmodifiable `EnumMap<ChemicalElement, Integer>` and an `int charge`.

- `count(element)`, `getElements()`, `getCharge()`;
- `molarMassGramsPerMole()` = Σ count × standard atomic weight;
- `massFraction(element)` in [0, 1];
- `toString()` in Hill order (C, then H, then the rest alphabetically; alphabetical only when there's no carbon), with
  the charge appended (`SO4^2-`);
- `equals` / `hashCode` on counts and charge, so `"OH2"` equals `"H2O"`.

Errors (`IllegalArgumentException`): empty text, unknown symbol, zero count, unbalanced or mismatched brackets,
garbage characters.

## Compound

Built with a Lombok `@Builder`; `name` and `formula` are required.

- `name`, `formula`;
- `standardState`: `StateOfMatter` at 298.15 K and 1 bar (required);
- `enthalpyOfFormationKjPerMole`: nullable `Double`, `OptionalDouble` getter; 0 for elements in their standard
  state (O2, N2, H2);
- `acidDissociationConstant` (Ka) and `baseDissociationConstant` (Kb): nullable `Double`s, `OptionalDouble` getters,
  for weak acids and bases;
- `strongAcid` / `strongBase` booleans (default false), for full dissociation.

Validation: Ka and Kb positive; at most one of {Ka, Kb, strongAcid, strongBase}.
`molarMassGramsPerMole()` delegates to the formula. Equality on name and formula.

## Molecule, Bond

`Molecule extends Compound`: a covalent compound whose bonds are listed. Its own builder takes the name, standard
state, thermochemistry, and:

- `atom(String label)`: the label's leading capital letter plus any lowercase letters are the element symbol
  (`"C"`, `"O1"`, `"Cl2"`); labels are unique;
- `bond(String a, String b, BondOrder order)`.

The `Formula` is derived from the atoms (charge 0). Validation at build time:

- every bond links two known, distinct labels, with at most one bond per pair;
- the molecule is connected;
- for elements with a single standard valence (H 1, O 2, N 3, C 4, F/Cl/Br/I 1), the sum of bond orders around the
  atom equals that valence; other elements (S, P, metals...) are not checked.

`BondOrder`: SINGLE (1), DOUBLE (2), TRIPLE (3).

`Bond`: two labels, their elements, its order, and:

- `electronegativityDifference()`, which throws `IllegalStateException` if either element has none;
- `getType()`: `BondType` NONPOLAR_COVALENT (Δχ < 0.4), POLAR_COVALENT (0.4 ≤ Δχ < 1.7), IONIC (≥ 1.7);
- `averageEnergyKjPerMole()`: `OptionalDouble` from a textbook table of average bond enthalpies (H–H 436, C–H 413,
  C–C 348, C=C 614, C≡C 839, C–O 358, C=O 745, O–H 463, O=O 498, N–H 391, N≡N 945, H–Cl 431, C–Cl 328, Cl–Cl 242);
  empty when absent.

`Molecule.atomizationEnergyKjPerMole()` is the sum of the bond energies; it throws `IllegalStateException` if any bond
lacks one. Ionic solids (NaCl, CaCO3, Fe2O3) stay plain `Compound`s.

## Reaction

Immutable; reactants and products as unmodifiable `LinkedHashMap<Compound, Integer>`, coefficients > 0.

- `new Reaction(reactants, products)` checks that every element and the total charge are conserved, otherwise
  `IllegalArgumentException`; a compound may not appear on both sides.
- `Reaction.balance(List<Compound> reactants, List<Compound> products)`: builds the matrix with one row per element
  plus one for charge, and one column per compound (products negated). Its null space is computed by Gauss–Jordan
  elimination with exact rational arithmetic (`RationalMatrix`, `long` numerator/denominator, reduced by gcd). It
  needs a one-dimensional null space with all-positive entries, which it scales to the smallest integers. Otherwise
  `IllegalArgumentException` ("cannot be balanced" / "several independent balancings").
- `enthalpyKjPerMole()` = Σ ν·ΔHf(products) − Σ ν·ΔHf(reactants) (Hess's law); `IllegalStateException` if a
  compound has no ΔHf.
- `isExothermic()`: enthalpy < 0.
- `massGrams(Compound given, double givenGrams, Compound wanted)`: stoichiometric mass; both must take part in the
  reaction.
- `toString()`: `CH4 + 2 O2 -> CO2 + 2 H2O` (formulas, coefficient 1 omitted).

## Mixture

`Map<Compound, Double>` mole fractions, each in (0, 1], summing to 1 within 1e-3.

- `molarMassGramsPerMole()` = Σ xᵢ·Mᵢ;
- `moleFraction(compound)`, `massFraction(compound)`.

## Solution

An aqueous solution: a solute `Compound`, solvent water (the constructor rejects any solvent that isn't H2O), and
`molarityMolesPerLiter > 0`.

`ph()` at 25 °C, with Kw = 1.0e-14:

- strong acid of concentration C: [H+] = C/2 + √(C²/4 + Kw), which counts water's own ions, so 1e-9 M HCl stays
  near 7;
- strong base: the same for [OH−], then pH = 14 − pOH;
- weak acid: [H+] is the positive root of x² + Ka·x − Ka·C = 0;
- weak base: the same with Kb for [OH−];
- otherwise neutral: 7.

Strong acids and bases are counted as monoprotic (one H+ or OH−); sulfuric acid's second proton is not modeled.

`acidity()`: `Acidity` ACIDIC (pH < 6.99), NEUTRAL, BASIC (pH > 7.01).

## ChemicalConstants

Avogadro constant 6.022_140_76e23 /mol, molar gas constant 8.314_462_618 J/(mol·K), water ion product 1.0e-14 at
25 °C, standard temperature 298.15 K, standard pressure 100 000 Pa.

## Real instances (`world.chemistry`)

`Compounds`, with NIST WebBook ΔHf and CRC Ka/Kb:

- molecules with bonds: WATER, CARBON_DIOXIDE, OXYGEN_GAS, NITROGEN_GAS, HYDROGEN_GAS, CHLORINE_GAS, METHANE,
  ETHANE, PROPANE, ETHYLENE, ACETYLENE, ETHANOL, AMMONIA, HYDROGEN_CHLORIDE;
- formula-only compounds: CARBON_MONOXIDE and OZONE (their real bonding breaks the simple valence rule: C≡O and
  O=O–O carry formal charges), GLUCOSE, ACETIC_ACID (Ka 1.75e-5), HYDROCHLORIC_ACID (strong acid, the aqueous HCl),
  SULFURIC_ACID (strong), NITRIC_ACID (strong), SODIUM_HYDROXIDE (strong base), SODIUM_CHLORIDE, CALCIUM_CARBONATE,
  SILICON_DIOXIDE, IRON_III_OXIDE, IRON (solid metal, ΔHf 0), ARGON (monatomic gas);
- AMMONIA carries Kb 1.8e-5.

Water is liquid at the standard state (ΔHf −285.83), as is the water used by every reaction below.

`Reactions`: METHANE_COMBUSTION, GLUCOSE_COMBUSTION (cellular respiration), PHOTOSYNTHESIS, RUSTING
(4 Fe + 3 O2 → 2 Fe2O3), HABER_PROCESS (N2 + 3 H2 → 2 NH3), NEUTRALIZATION
(HCl + NaOH → NaCl + H2O); each built with `Reaction.balance`.

`Mixtures`: DRY_AIR (NOAA mole fractions: N2 0.780 8, O2 0.209 5, Ar 0.009 34, CO2 0.000 42), SEAWATER (water and NaCl
at 35 g/kg salinity, converted to mole fractions).

## Errors

Invalid input throws `IllegalArgumentException` in constructors and factories. Asking for a quantity whose data is
missing (ΔHf, bond energy, electronegativity) throws `IllegalStateException`.

## Testing

JUnit 5 `Assertions`, snake_case + `@DisplayName`, tolerances against real values:

- `ChemicalElementTest`: 118 elements with atomic numbers 1..118 contiguous; Au 196.97, Og 294; F has the highest
  electronegativity at 3.98; noble gases He/Ne/Ar have none; Fe is a transition metal in period 4, group 8, block d.
- `FormulaTest`: H2O 18.015, Ca(OH)2 74.09, CuSO4·5H2O 249.69; SO4^2- charge −2; Hill order (`C2H6O` for ethanol);
  `OH2` equals `H2O`; syntax errors rejected.
- `MoleculeTest`: CO2 bonds polar covalent; H2 nonpolar; derived formula; carbon with five bonds rejected; a
  disconnected molecule rejected; atomization energy of CH4 = 4 × 413.
- `BondTest`: NaCl-like Δχ 2.23 → ionic; a bond to neon has no electronegativity.
- `ReactionTest`: methane combustion balances to 1, 2, 1, 2 with ΔH ≈ −890 kJ/mol (±1); glucose combustion
  6, 6, 6; Haber 1, 3, 2; an impossible reaction and an ambiguous one (H2 + O2 → H2O + H2O2) throw; unbalanced
  explicit coefficients throw; 1 kg CH4 → 2 743 g CO2 (±1); toString format.
- `MixtureTest`: dry air 28.96 g/mol (±0.01); fractions not summing to 1 rejected.
- `SolutionTest`: 0.01 M HCl → 2.00; 0.83 M acetic acid → 2.42; 0.1 M ammonia → 11.13; 0.01 M NaOH → 12.00;
  1e-9 M HCl → ≈7.00 and NEUTRAL; NaCl → 7; non-water solvent rejected.
- `CompoundTest`: both Ka and strong acid rejected; negative Ka rejected.

## Integration

- `Main`: a short chemistry paragraph (water's molar mass, methane combustion balanced with its energy, the pH of
  vinegar, the molar mass of air).
- `CLAUDE.md`: describe `matter.chemistry` and `world.chemistry` in the architecture section.
