# Cosmology & time — design

Date: 2026-10-16
Sub-project 4 of 4 in "describe every aspect of the Universe". Builds on `PrimitiveAtom` and `Universe`; depends on
`celestial.PhysicalConstants` (the speed of light and the Julian year added by sub-project 3).

## Goal

Give the universe a history: an expanding spacetime described by a cosmological model with real parameters, from
which its age, the distance and look-back time of a redshift, and the temperature of the cosmic background follow.
Name the epochs from the Big Bang to today, and place cosmic events on Carl Sagan's cosmic calendar.

## Non-goals

- Inflation, dark matter or dark energy as physics; they are parameters of the model only.
- Non-flat or non-ΛCDM models (Ω_k is fixed at 0 apart from rounding).
- Structure formation, the CMB power spectrum, Big Bang nucleosynthesis yields.
- Peculiar velocities: the Hubble law is the smooth expansion only.

## Layout

- `com.enlil.universe.cosmology` — generic types: `CosmologicalModel`, `Redshift`, `CosmicEpoch`, `CosmicEvent`,
  `CosmicCalendar`.
- `com.enlil.universe.world.cosmology` — real instances: `Planck2018` (a no-arg `CosmologicalModel` subclass, like
  `Sun`), `CosmicHistory` (events as constants).
- Changes at the root: `Universe` holds an optional model; `PrimitiveAtom.bigBand()` gives it Planck 2018.

## Constants

Added to `PhysicalConstants`: parsec = 3.085_677_581_49e16 m and megaparsec = 10⁶ pc. `CosmologicalModel` keeps
`SECONDS_PER_YEAR` from the Julian year.

## CosmologicalModel

A flat ΛCDM model. Constructor arguments: `hubbleConstantKmPerSecondPerMegaparsec`, `matterDensity` (Ω_m),
`darkEnergyDensity` (Ω_Λ), `radiationDensity` (Ω_r), `cmbTemperatureKelvin`. Validation: all positive, and
Ω_m + Ω_Λ + Ω_r within 0.01 of 1 (flatness).

All integrals use composite Simpson's rule over the scale factor a = 1/(1+z), with enough intervals (10 000) for
four significant digits; the integrand is regular at a = 0 once multiplied out.

- `hubbleParameterAt(z)` = H₀ · √(Ω_r(1+z)⁴ + Ω_m(1+z)³ + Ω_Λ) (E(z) exposed as `expansionRateRatio(z)`);
- `hubbleTimeYears()` = 1/H₀;
- `ageYears()` = age today; `ageAtYears(z)` = ∫₀^a da / (a·H(a));
- `lookbackTimeYears(z)` = age today − age at z;
- `scaleFactorAt(z)` = 1/(1+z);
- `comovingDistanceMegaparsecs(z)` = c ∫₀^z dz / H(z);
- `luminosityDistanceMegaparsecs(z)` = (1+z)·D_C; `angularDiameterDistanceMegaparsecs(z)` = D_C/(1+z);
- `recessionVelocityKmPerSecond(distanceMegaparsecs)` = H₀·d (Hubble–Lemaître law);
- `cmbTemperatureAtKelvin(z)` = T₀(1+z);
- `redshiftAtAge(double ageYears)`: the inverse of `ageAtYears`, by bisection; throws outside (0, age today];
- `matterDarkEnergyEqualityRedshift()` = (Ω_Λ/Ω_m)^⅓ − 1.

All take z ≥ 0 (redshifts below 0 belong to nearby peculiar motion, outside the model) and throw otherwise.

`Planck2018` (Planck 2018 VI, TT,TE,EE+lowE+lensing): H₀ 67.4, Ω_m 0.315, Ω_r 9.0e-5, Ω_Λ = 1 − Ω_m − Ω_r,
T₀ 2.725 5 K (FIRAS). Its documented age is 13.787 ± 0.020 Gyr.

## Redshift

A small immutable value with `z > −1`:

- `Redshift.fromWavelengths(observedNanometers, emittedNanometers)`;
- `Redshift.of(z)`;
- `isBlueshift()` (z < 0), `stretchFactor()` = 1+z;
- `observedWavelengthNanometers(emittedNanometers)`.

The model's methods take a plain `double z`; `Redshift` is for spectroscopy (Andromeda's blueshift is legal here,
even though the model refuses it).

## CosmicEpoch

A chronological enum, contiguous, each with start and end in seconds after the Big Bang (ends exclusive; the last
one is open-ended):

| Epoch | From | To |
|---|---|---|
| PLANCK | 0 | 10⁻⁴³ s |
| GRAND_UNIFICATION | 10⁻⁴³ s | 10⁻³⁶ s |
| INFLATION | 10⁻³⁶ s | 10⁻³² s |
| ELECTROWEAK | 10⁻³² s | 10⁻¹² s |
| QUARK | 10⁻¹² s | 10⁻⁶ s |
| HADRON | 10⁻⁶ s | 1 s |
| LEPTON | 1 s | 10 s |
| NUCLEOSYNTHESIS | 10 s | 20 min |
| PHOTON | 20 min | 377 000 yr |
| DARK_AGES | 377 000 yr | 150 Myr |
| REIONIZATION | 150 Myr | 1 Gyr |
| GALAXY_FORMATION | 1 Gyr | 10.3 Gyr |
| DARK_ENERGY_DOMINATED | 10.3 Gyr | — |

- `at(double secondsAfterBigBang)`, `atYears(double yearsAfterBigBang)`;
- `getStartSeconds()`, `getEndSeconds()` (`OptionalDouble`, empty for the last);
- a one-line `getDescription()`.

Textbook boundaries; documented as orders of magnitude for the earliest epochs. The 10.3 Gyr dark energy boundary is
the moment dark energy overtook matter, which tests cross-check against `Planck2018`'s equality redshift
(z ≈ 0.3).

## Cosmic events and calendar

`CosmicEvent`: name, `yearsAgo ≥ 0`. `yearsAfterBigBang(model)` = model age − years ago, which must be ≥ 0.

`CosmicHistory` (real events, consensus values): BIG_BANG (13.787 Gyr, computed from `Planck2018`), RECOMBINATION
(13.787 Gyr − 377 000 yr), FIRST_STARS (13.6 Gyr), MILKY_WAY_DISK (8.8 Gyr), SOLAR_SYSTEM (4.567 Gyr), EARTH
(4.543 Gyr), MOON (4.51 Gyr), FIRST_LIFE (3.7 Gyr), OXYGEN_ATMOSPHERE (2.4 Gyr, the Great Oxidation Event),
CAMBRIAN_EXPLOSION (538.8 Myr), DINOSAURS_EXTINCTION (66 Myr), HOMO_SAPIENS (300 000 yr), WRITING (5 200 yr).
`CosmicHistory.all()` in chronological order.

The geological events reuse the Earth sciences values (Cambrian start, K–Pg) as literals; there's no code dependency
on `geoscience`.

`CosmicCalendar` (Sagan's *The Dragons of Eden*, 1977): the universe's age compressed into one non-leap year, Big
Bang at 1 January 00:00, now at 31 December 24:00.

- `new CosmicCalendar(CosmologicalModel model)`;
- `dateOf(CosmicEvent event)` / `dateOf(double yearsAgo)`: a `LocalDateTime` in an arbitrary non-leap year (2023),
  with 31 December 24:00 represented as 1 January of the next year 00:00;
- `realYearsPerCalendarSecond()` ≈ 437 years.

With Planck 2018, the Solar System forms around 2 September, the dinosaurs die on 30 December, and Homo sapiens
appears on 31 December around 23:48.

## Universe

`Universe` gets a nullable `CosmologicalModel cosmology` builder field, exposed as `Optional<CosmologicalModel>
getCosmology()` (hand-written; Lombok's getter excluded with `@Getter(AccessLevel.NONE)` even under `@Data`).
`PrimitiveAtom.bigBand()` now returns `Universe.builder().cosmology(new Planck2018()).build()`, so the universe born
from the primeval atom knows its age. `Main` sets the model on the universe it assembles.

`Discoveries` gets two entries if missing: Hubble's law (1929) and the CMB (Penzias & Wilson, 1965).

## Errors

Invalid input throws `IllegalArgumentException` (negative redshift for the model, z ≤ −1 for `Redshift`, a non-flat
model, an event before the Big Bang).

## Testing

- `CosmologicalModelTest`: Planck 2018 age 13.79 Gyr (±0.03); Hubble time 14.5 Gyr (±0.1); age at recombination
  (z = 1 089.8) ≈ 377 000 yr (±10 %); look-back time to z = 1 ≈ 7.9 Gyr (±0.1); comoving distance to z = 1
  ≈ 3 400 Mpc (±50); luminosity distance = (1+z)² × angular diameter distance; CMB at recombination ≈ 2 973 K (±1);
  recession velocity at 100 Mpc = 6 740 km/s; `redshiftAtAge` inverts `ageAtYears`; dark energy overtook matter at
  z ≈ 0.30 (±0.02); a non-flat model and a negative z rejected.
- `RedshiftTest`: Hα at 656.3 nm observed at 721.9 nm → z ≈ 0.1; blueshift detected; z = −1 rejected.
- `CosmicEpochTest`: epochs are contiguous; 1 µs after the Big Bang is the hadron epoch boundary; 3 minutes is
  nucleosynthesis; today is dark energy dominated; the DARK_ENERGY_DOMINATED boundary matches the
  model's age at the equality redshift (10.3 Gyr with Planck 2018, ±0.1).
- `CosmicCalendarTest`: Big Bang on 1 January 00:00; Solar System in early September; dinosaur extinction on
  30 December; Homo sapiens after 23:45 on 31 December; today at year end.
- `CosmicHistoryTest`: events are in chronological order and none is before the Big Bang.
- `PrimitiveAtomTest`: the universe it creates has a cosmology whose age is about 13.8 Gyr.

## Integration

- `Main`: the age of the universe, the epoch we live in, and a few events on the cosmic calendar.
- `CLAUDE.md`: describe the `cosmology` layer and the change to `Universe` and `PrimitiveAtom`.
