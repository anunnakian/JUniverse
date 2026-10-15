# Stellar evolution & black holes — design

Date: 2026-10-16
Sub-project 3 of 4 in "describe every aspect of the Universe". Independent of chemistry and Earth sciences; extends
`celestial`.

## Goal

Make stars shine and age: their luminosity and magnitude from their size and temperature, how long they live, where
their habitable zone lies, what they become when they die. Add the dead stars themselves (white dwarfs, neutron
stars, black holes) with their real physics, and put a supermassive black hole at the heart of the Milky Way and
Andromeda.

## Non-goals

- Stellar interiors, nuclear reaction networks, real evolutionary tracks (MESA-like models).
- Binary star evolution, novae, accretion.
- Rotating (Kerr) or charged black holes; only Schwarzschild.
- Brown dwarfs as a type.

## Layout

All in `com.enlil.universe.celestial`, with real instances in `world`:

- extended: `Star`, `Galaxy`, `PhysicalConstants`;
- new: `HabitableZone`, `StellarFate`, `StellarStage`, `StellarRemnant` (abstract), `WhiteDwarf`, `NeutronStar`,
  `BlackHole`;
- new in `world`: `SiriusB`, `CrabPulsar`, `CygnusX1`, `SagittariusAStar`, `M31Star` (Andromeda's central black
  hole).

## Constants (`PhysicalConstants`)

Speed of light c = 299 792 458 m/s; Stefan–Boltzmann σ = 5.670_374_419e-8 W·m⁻²·K⁻⁴; reduced Planck constant
ħ = 1.054_571_817e-34 J·s; Boltzmann constant k = 1.380_649e-23 J/K; nominal solar luminosity L☉ = 3.828e26 W and
radius R☉ = 6.957e8 m (IAU 2015 B3); Julian year = 365.25 days; solar bolometric absolute magnitude 4.74.

## Star

New derived quantities:

- `luminosityWatts()` = 4πR²σT⁴ (Stefan–Boltzmann); the Sun gives 3.828e26 W;
- `luminositySolar()` = L / L☉;
- `absoluteBolometricMagnitude()` = 4.74 − 2.5·log₁₀(L/L☉);
- `mainSequenceLifetimeYears()` ≈ 10¹⁰ · (M/M☉)^−2.5 (the textbook L ∝ M^3.5 scaling; documented as a rough
  estimate, poor below 0.4 M☉ where real lifetimes are even longer);
- `getHabitableZone()`: a `HabitableZone` with inner edge √(L/1.1) AU and outer edge √(L/0.53) AU (Kasting /
  Kopparapu conservative fluxes, L in solar units);
- `getFate()`: `StellarFate` from the mass, treated as the initial mass (mass loss isn't modeled):
  WHITE_DWARF below 8 M☉, NEUTRON_STAR from 8 to 20 M☉, BLACK_HOLE from 20 M☉;
- `stageAt(double ageYears)`: `StellarStage` from a three-phase textbook model:
  MAIN_SEQUENCE until the lifetime, then a giant phase lasting 10 % of the lifetime (RED_GIANT below 8 M☉,
  RED_SUPERGIANT above), then the remnant matching the fate (WHITE_DWARF, NEUTRON_STAR, BLACK_HOLE). Negative ages
  throw; PROTOSTAR is kept in the enum for completeness but not produced (no pre-main-sequence model).

`HabitableZone`: `innerEdgeAu < outerEdgeAu`, `contains(Planet planet)` on its semi-major axis,
`contains(double distanceAu)`.

`StellarStage` knows whether a stage is a remnant (`isRemnant()`).

## Stellar remnants

`StellarRemnant extends CelestialBody` (abstract): name, mass, radius. Remnants have no planets in this model
(pulsar planets are too rare to justify it).

`WhiteDwarf`: mass, radius, `surfaceTemperatureKelvin`.

- Rejects a mass above the Chandrasekhar limit, `CHANDRASEKHAR_LIMIT_SOLAR_MASSES = 1.44`.
- `luminosityWatts()` by Stefan–Boltzmann; `densityKgPerCubicMeter()`.

`NeutronStar`: mass, radius, `rotationPeriodSeconds > 0`.

- Rejects a mass above the Tolman–Oppenheimer–Volkoff limit, taken as `TOV_LIMIT_SOLAR_MASSES = 2.3`.
- `densityKgPerCubicMeter()`; `surfaceEscapeVelocityFractionOfLight()` = √(2GM/R) / c;
  `isMillisecondPulsar()` (period < 10 ms).

`BlackHole`: name and mass only; its radius is the Schwarzschild radius, so `CelestialBody`'s positive-radius check
still holds. Methods:

- `schwarzschildRadiusMeters()` = 2GM/c²;
- `photonSphereRadiusMeters()` = 1.5 r_s; `innermostStableOrbitRadiusMeters()` = 3 r_s;
- `timeDilationFactor(double distanceMeters)` = √(1 − r_s/r), the rate of a static clock at r relative to a distant
  one; throws inside the horizon;
- `tidalAccelerationDifference(double distanceMeters, double lengthMeters)` = 2GM·L / r³ (head-to-toe stretch,
  m/s²);
- `hawkingTemperatureKelvin()` = ħc³ / (8πGMk);
- `evaporationTimeYears()` = 5120πG²M³ / (ħc⁴), in years;
- `isSupermassive()`: mass ≥ 10⁵ M☉.

`surfaceGravity()` inherited from `CelestialBody` would be meaningless at the horizon, so `BlackHole` overrides it to
throw `UnsupportedOperationException`.

## Galaxies

`Galaxy` gains a list of `StellarRemnant`s and an optional central black hole (nullable field, `Optional` getter):

- a new constructor `Galaxy(name, type, stars, remnants, centralBlackHole)`; the existing one delegates with an empty
  list and no black hole;
- `getRemnants()`, `getBlackHoles()` (the remnants that are black holes, plus the central one).

`Universe.getBlackHoles()` flattens them like `getStars()`.

`MilkyWay(stars)` keeps working and gets Sagittarius A*; a new `MilkyWay(stars, remnants)` adds remnants.
`Andromeda` gets M31*.

## Real objects (`world`)

| Class | Type | Values |
|---|---|---|
| `SiriusB` | `WhiteDwarf` | 1.018 M☉, 0.008 4 R☉, 25 000 K |
| `CrabPulsar` | `NeutronStar` | 1.4 M☉ (canonical, not measured), 10 km, 33.5 ms |
| `CygnusX1` | `BlackHole` | 21.2 M☉ (Miller-Jones et al. 2021) |
| `SagittariusAStar` | `BlackHole` | 4.297e6 M☉ (GRAVITY 2022) |
| `M31Star` | `BlackHole` | 1.4e8 M☉ |

`Main` assembles the Milky Way with Sirius B, the Crab pulsar and Cygnus X-1 among its remnants.

## Errors

Invalid input throws `IllegalArgumentException` in constructors (mass beyond a limit, non-positive period, negative
age); physically meaningless queries throw `IllegalStateException` or `UnsupportedOperationException`.

## Testing

- `StarTest` (extended): the Sun's luminosity 3.828e26 W (±0.5 %) and magnitude 4.74 (±0.01); Sirius ≈ 25 L☉ (±5 %);
  the Sun lives 10 Gyr, Sirius ≈ 1.6 Gyr; Earth is in the Sun's habitable zone, Venus and Mars are not; the Sun ends
  as a white dwarf, a 10 M☉ star as a neutron star, a 25 M☉ star as a black hole; the Sun is on the main sequence at
  4.6 Gyr, a red giant at 10.5 Gyr, a white dwarf at 12 Gyr.
- `WhiteDwarfTest`: Sirius B density ≈ 2.4e9 kg/m³ (±10 %); 1.5 M☉ rejected.
- `NeutronStarTest`: Crab density ≈ 6.7e17 kg/m³ (±5 %); escape velocity ≈ 0.64 c (±0.02); not a millisecond
  pulsar; 3 M☉ rejected.
- `BlackHoleTest`: a solar-mass black hole has r_s ≈ 2 953 m (±1) and a Hawking temperature ≈ 6.17e-8 K (±1 %), and
  evaporates in ≈ 2.1e67 years (±5 %); Sagittarius A* has r_s ≈ 1.27e10 m (±1 %) and is supermassive; Cygnus X-1
  isn't; time dilation at 3 r_s = √(2/3); inside the horizon throws; surface gravity throws.
- `GalaxyTest` (extended): the Milky Way's black holes include Sagittarius A* and Cygnus X-1; a galaxy built the old
  way has none.

## Integration

- `Main`: the Sun's luminosity, lifetime and fate; Earth in its habitable zone; Sagittarius A*'s event horizon.
- `CLAUDE.md`: describe stellar physics on `Star`, remnants, and galaxies' black holes.
