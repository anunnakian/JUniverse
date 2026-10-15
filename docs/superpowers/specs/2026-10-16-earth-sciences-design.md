# Earth sciences layer — design

Date: 2026-10-16
Sub-project 2 of 4 in "describe every aspect of the Universe". Depends on sub-project 1 (chemistry):
atmospheres are `Mixture`s, minerals have a `Formula`.

## Goal

Describe a planet as a physical world rather than a point mass: what it is made of inside, the air around it and how
that air warms it, the plates and earthquakes that shape its surface, its rocks and minerals, and the geological time
scale that dates its history. Earth gets real values; Mars and Venus get their atmospheres.

## Non-goals

- Weather, ocean currents, climate models, the carbon cycle.
- Seismic wave propagation; any earthquake beyond its magnitude and energy.
- Plate motion as a simulation (plates have a speed, but no positions on a map).
- Interiors of planets other than Earth.

## Layout

- `com.enlil.universe.geoscience` — generic types: `InternalLayer`, `Interior`, `Atmosphere`, `AtmosphericLayer`,
  `TectonicPlate`, `PlateType`, `PlateBoundary`, `BoundaryType`, `Earthquake`, `Mineral`, `Rock`, `RockType`,
  `GeologicTimeUnit`, `GeologicRank`, `MassExtinction`.
- `com.enlil.universe.world.geoscience` — real instances: `Minerals`, `Rocks`, `TectonicPlates`, `Earthquakes`,
  `GeologicTimeScale`, `MassExtinctions`.
- `celestial` changes: `Planet` gains overridable `getAtmosphere()` and `getInterior()`; `Star` gains
  `equilibriumTemperatureKelvin(planet, bondAlbedo)`.

`geoscience` depends on `matter`, `matter.chemistry` and `celestial`. `celestial` depends on `geoscience` only
through the two overridable getters' return types.

## Planet hooks

Following the `Polity` pattern (`getSameSexMarriageYear()` empty by default and overridden by real nations), rather
than adding more `Planet` constructor overloads:

- `Optional<Atmosphere> getAtmosphere()`: empty by default; overridden by `Earth`, `Mars`, `Venus`.
- `Optional<Interior> getInterior()`: empty by default; overridden by `Earth`.

The returned objects are built once, in a `private static final` constant of each real planet.

`Star.equilibriumTemperatureKelvin(Planet planet, double bondAlbedo)`
= T★ · √(R★ / 2a) · (1 − A)^¼, with a the planet's semi-major axis. Albedo must be in [0, 1). Earth (A = 0.306)
gives ≈ 254 K.

## Interior

`InternalLayer`: name, `outerRadiusMeters`, mean `densityKgPerCubicMeter`, `StateOfMatter` (all positive, state
SOLID or LIQUID).

`Interior`: an ordered list of layers from the center out. Validation: at least one layer, outer radii strictly
increasing. Methods:

- `getLayers()`;
- `layerAt(double depthMeters)`, depth below the surface (the last layer's outer radius); throws outside
  [0, radius];
- `innerRadiusMeters(layer)`;
- `layerMassKg(layer)` = density × shell volume; `massKg()` = sum, which tests check against the planet's mass.

Earth (PREM boundaries, mean layer densities):

| Layer | Outer radius (km) | Density (kg/m³) | State |
|---|---|---|---|
| Inner core | 1 221 | 12 900 | solid |
| Outer core | 3 480 | 10 900 | liquid |
| Lower mantle | 5 701 | 4 900 | solid |
| Upper mantle | 6 346.6 | 3 700 | solid |
| Crust | 6 371 | 2 800 | solid |

Summed mass ≈ 6.0e24 kg, within 1 % of Earth's 5.972e24 kg.

## Atmosphere

`Atmosphere`: composition (`Mixture`), `surfacePressurePascals > 0`, `meanSurfaceTemperatureKelvin > 0`. Methods:

- `scaleHeightMeters(double surfaceGravity)` = R·T / (M·g), with M the mixture's molar mass in kg/mol;
- `pressureAtPascals(double altitudeMeters, double surfaceGravity)` = p₀·e^(−h/H) (isothermal barometric formula);
- `greenhouseWarmingKelvin(double equilibriumTemperatureKelvin)` = mean surface temperature − equilibrium
  temperature.

The atmosphere doesn't point back to its planet, so callers pass `planet.surfaceGravity()`.

Real atmospheres:

- Earth: `Mixtures.DRY_AIR`, 101 325 Pa, 288 K;
- Mars: CO2 0.951, N2 0.028, Ar 0.021 (Curiosity SAM, normalized), 610 Pa, 210 K;
- Venus: CO2 0.965, N2 0.035, 9.2 MPa, 737 K.

These two compositions are added to `world.chemistry.Mixtures` as `MARTIAN_AIR` and `VENUSIAN_AIR`.

`AtmosphericLayer` enum (Earth's standard atmosphere, by altitude): TROPOSPHERE 0–12 km, STRATOSPHERE 12–50 km,
MESOSPHERE 50–80 km, THERMOSPHERE 80–700 km, EXOSPHERE 700–10 000 km; `of(altitudeMeters)` throws outside
[0, 10 000 km]. It also has `KARMAN_LINE_METERS = 100_000` as a constant, the conventional edge of space.

## Plate tectonics

`TectonicPlate`: name, `areaSquareKm > 0`, `PlateType` (OCEANIC, CONTINENTAL, MIXED), `speedMillimetersPerYear ≥ 0`.

- `yearsToMove(double distanceKm)`; throws if the speed is 0.

`PlateBoundary`: two distinct plates, a `BoundaryType` (DIVERGENT, CONVERGENT, TRANSFORM), a name, and
`spreadingRateMillimetersPerYear` (the relative speed across the boundary, ≥ 0).

- `yearsToWiden(double distanceKm)` (divergent boundaries only, else `IllegalStateException`).

Real plates (`TectonicPlates`, areas in millions of km²): PACIFIC 103.3, NORTH_AMERICAN 75.9, EURASIAN 67.8,
AFRICAN 61.3, ANTARCTIC 60.9, INDO_AUSTRALIAN 58.9, SOUTH_AMERICAN 43.6, plus NAZCA 15.6 and the major boundaries
MID_ATLANTIC_RIDGE (N. American / Eurasian, divergent, 25 mm/yr), SAN_ANDREAS_FAULT (Pacific / N. American,
transform, 46 mm/yr), HIMALAYAS (Indo-Australian / Eurasian, convergent, 40 mm/yr). `TectonicPlates.MAJOR` lists
the seven major plates. `Earth.getTectonicPlates()` returns every plate.

## Earthquakes

`Earthquake`: name, year (historical year, as `Polity`), `momentMagnitude` (0 < Mw ≤ 10), and an optional
`PlateBoundary` (nullable field, `Optional` getter).

- `seismicMomentNewtonMeters()` = 10^(1.5·Mw + 9.1) (Hanks & Kanamori);
- `energyRatioTo(Earthquake other)` = 10^(1.5·ΔMw), so one magnitude ≈ 31.6× the energy.

Real: VALDIVIA_1960 (9.5), ALASKA_1964 (9.2), SUMATRA_2004 (9.1), TOHOKU_2011 (9.1), SAN_FRANCISCO_1906 (7.9, San
Andreas), LISBON_1755 (8.5, estimated).

## Minerals and rocks

`Mineral`: name, `Formula`, `mohsHardness` in [1, 10], `densityKgPerCubicMeter > 0`.

- `scratches(Mineral other)`: hardness strictly greater.

`Rock`: name, `RockType` (IGNEOUS, SEDIMENTARY, METAMORPHIC), a non-empty list of `Mineral`s.

`RockType` knows the rock cycle: `transformsInto()` returns which types a rock can become (igneous → sedimentary,
metamorphic; sedimentary → metamorphic, igneous; metamorphic → igneous, sedimentary), with the process named
(weathering, metamorphism, melting) by a `RockCycleProcess` enum.

Real minerals (`Minerals`), the ten Mohs reference minerals: TALC Mg3Si4O10(OH)2, GYPSUM CaSO4·2H2O, CALCITE CaCO3,
FLUORITE CaF2, APATITE Ca5(PO4)3F, ORTHOCLASE KAlSi3O8, QUARTZ SiO2, TOPAZ Al2SiO4F2, CORUNDUM Al2O3, DIAMOND C;
plus HEMATITE Fe2O3, OLIVINE Mg2SiO4 and HALITE NaCl. `Minerals.MOHS_SCALE` lists the ten in order.

Real rocks (`Rocks`): GRANITE (igneous: quartz, orthoclase), BASALT (igneous: olivine), SANDSTONE (sedimentary:
quartz), LIMESTONE (sedimentary: calcite), MARBLE (metamorphic: calcite), ROCK_SALT (sedimentary: halite).

## Geological time

`GeologicRank`: EON, ERA, PERIOD (in decreasing size).

`GeologicTimeUnit`: name, rank, `startMillionYearsAgo > endMillionYearsAgo ≥ 0`, an optional parent (nullable,
`Optional` getter) whose rank must be the next one up and whose span must contain this one.

- `durationMillionYears()`, `contains(double millionYearsAgo)` (start inclusive, end exclusive, except that the
  present belongs to the last unit).

`GeologicTimeScale` (ICS International Chronostratigraphic Chart, latest version checked when implementing):

- eons: HADEAN 4 567–4 031, ARCHEAN 4 031–2 500, PROTEROZOIC 2 500–538.8, PHANEROZOIC 538.8–0;
- eras of the Phanerozoic: PALEOZOIC 538.8–251.902, MESOZOIC 251.902–66.0, CENOZOIC 66.0–0;
- periods: CAMBRIAN, ORDOVICIAN, SILURIAN, DEVONIAN, CARBONIFEROUS, PERMIAN, TRIASSIC, JURASSIC, CRETACEOUS,
  PALEOGENE, NEOGENE (from 23.04) and QUATERNARY (from 2.58);
- `at(double millionYearsAgo, GeologicRank rank)`: the unit of that rank, `Optional` empty when the rank isn't
  modeled at that age (no eras or periods before the Phanerozoic);
- `all()` in chronological order.

`MassExtinction`: name, `millionYearsAgo`, `speciesLostFraction` in (0, 1).
`MassExtinctions.BIG_FIVE`: END_ORDOVICIAN (443.8, 0.85), LATE_DEVONIAN (372, 0.75), END_PERMIAN (251.9, 0.81),
END_TRIASSIC (201.4, 0.76), CRETACEOUS_PALEOGENE (66.0, 0.76). Tests check that the end-Permian and K–Pg extinctions
fall on period boundaries.

## Errors

Invalid input throws `IllegalArgumentException` in constructors; a query that makes no sense for the object
(widening a transform fault) throws `IllegalStateException`.

## Testing

- `InteriorTest`: Earth's summed layer mass within 2 % of 5.972e24 kg; the outer core is liquid; depth 3 000 km is
  in the lower mantle; non-increasing radii rejected.
- `AtmosphereTest`: Earth's scale height ≈ 8.4 km (±0.2); pressure at Everest's summit (8 849 m) ≈ 33.7 kPa (±3);
  Earth's greenhouse warming ≈ 34 K (±2) from the Sun's equilibrium temperature; Venus warming > 400 K; Mars has a
  scale height ≈ 11 km (±1).
- `StarTest` (extended): Earth's equilibrium temperature 254 K (±2); albedo 1 rejected.
- `AtmosphericLayerTest`: 10 km → troposphere, 30 km → stratosphere, Kármán line in the thermosphere.
- `TectonicPlateTest`: the seven major plates cover 90–95 % of Earth's 510 million km²; Atlantic widening 1 km in
  40 000 years; transform fault can't widen.
- `EarthquakeTest`: Valdivia vs San Francisco energy ratio ≈ 10^(1.5·1.6) ≈ 251; Mw 9.1 moment ≈ 5.6e22 N·m;
  magnitude 0 rejected.
- `MineralTest`: diamond scratches quartz; quartz doesn't scratch topaz; Mohs scale is strictly increasing; quartz
  molar mass 60.08; hardness 11 rejected.
- `RockTypeTest`: every type can become the two others.
- `GeologicTimeScaleTest`: units of each rank tile their parent with no gaps or overlaps; 100 Ma is Cretaceous,
  Mesozoic, Phanerozoic; the dinosaurs' extinction (66 Ma) starts the Paleogene; nothing at period rank 3 000 Ma.
- `MassExtinctionTest`: big five in chronological order; fraction 1 rejected.

## Integration

- `Main`: Earth's interior and atmosphere, its greenhouse warming, and the current geological period.
- `CLAUDE.md`: describe the `geoscience` layer and the new `Planet` / `Star` methods.
