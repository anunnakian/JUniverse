# JUniverse

Let's code the universe in Java. Yes, for real ! ...all the things around and inside us.

Atoms, humans, moons, stars, galaxies. If it exists and it has a mass, it will end up as a `final` field sooner or later.

## Why ?

Because God created the universe but didn't write unit tests, and look where things went. (Just kidding, we respect all religions and beliefs.)

Every number in here is real: the gravitational constant, the mass of Jupiter, the weight of a bee, the size of your heart. No magic numbers, only physics numbers (with underscores, because our eyes deserve it: one AU is `149_597_870_700` meters).

## What's inside

The universe is built bottom up, like any respectable creation story:

1. **`matter`**: atoms with a nucleus, protons, neutrons and electrons. Ionize one and you get a brand new atom, because in this universe nothing mutates. Not even carbon.
2. **`life`**: creatures from Escherichia coli to the blue whale, and humans with a body, blood, organs, a face, a mind, hobbies and parents. Your blood type is inherited by Mendel's laws, your skin knows exactly how many minutes it can spend at UV index 8 before it burns. Whatever your skin type, the universe says the same thing: wear sunscreen.
3. **`celestial`**: stars, planets and moons moving along real Keplerian orbits. Kepler solved the equation in 1609, we solve it every time you call `positionOf`.
4. **`civilization`**: an abstract `Civilization` measured on the Kardashev scale by the power it harnesses. Extend it with your own species and your own era. Aliens welcome. Each civilization knows its discoveries, from Euclid to the double helix, across the sciences: physics, biology, psychology, economics... Civilizations organize themselves into polities: kingdoms, empires, principalities, sultanates, republics and theocracies, each with its capital, its head of state and its way of choosing one, and its money: the euro, the dollar, the yen, or the silver denarius of Rome, worth 16 bronze asses. Money is counted in exact cents, because 0.1 + 0.2 is 0.30000000000000004 in a `double`, and nobody wants to explain that to their bank. And people work: doctors, engineers, teachers, firefighters, soldiers, police officers, FBI agents, secret agents, and, back in the day, knights and samurai.
5. **`world`**: the real thing. The Sun, the eight planets and their moons, Sirius, Proxima Centauri, the Milky Way and its Local Group neighbours, humanity and ancient Rome, France, the United States, Japan (the last empire), Monaco, Vatican City and more. Pluto is not a planet here either. We checked.

And at the root, the `PrimitiveAtom` of Georges Lemaître, the Belgian physicist who figured out in 1927 that the universe is expanding (two years before Hubble, who still got the law named after him until 2018) and who imagined in 1931 that everything started as a single "primeval atom". The name "Big Bang" came later, from a critic who meant it as a joke. Its method is called `bigBand()`. Yes, the universe started with a drum solo. It's not a typo, it's a creation myth.

## Things you can actually ask the universe

* How much would you weigh on Jupiter ? (More than on Earth. Blame the planet, not the snacks.)
* When will Mars come closest to the Earth in the next two years ?
* How lit is the Moon tonight ?
* Can Alice give blood to Bob ? Can Bob give blood back ? (Relationships are complicated.)
* What blood types can their children have ?
* How far is humanity from a type I civilization ? (About 0.73 on the Kardashev scale, and some 300 years away at 2 % growth. Don't wait up.)
* How long did the Roman Empire last ? (502 years, from 27 BCE to 476. There is no year 0, and the universe knows it.)
* Could you meet a samurai and a knight in 1600 ? (Yes, on opposite sides of the planet. Both were gone by 1876.)
* Who elects a king ? (Malaysia's nine sultans, every five years.)
* How many calories does an hour of guitar burn ? (About 140 for 70 kg. An hour of running burns nearly five times more, but the guitar sounds better.)
* How long until you reach B2 in English ? How many dreams did you have last night, and why do you only remember one ?
* Where could Clara marry a woman, and where would her love still be a crime ? (The universe is not done improving.)
* What is your BMR, your max heart rate and your vital capacity ? (Population averages, not medical advice. Your doctor knows you better than a `double` does.)

## Let there be light

You need Java 17 and Maven. Then:

```sh
mvn test
```

Every test checks the code against reality, with a tolerance, because reality is never exactly what the textbook says.

To watch the whole universe print itself in your terminal:

```sh
mvn compile && java -cp target/classes com.enlil.universe.Main
```

13.8 billion years for the real one, a few seconds for ours. Not bad.

To live through fifteen centuries of the history of France, from Clovis to the euro, kings, emperors, republics, knights and musketeers included:

```sh
mvn compile && java -cp target/classes com.enlil.universe.simulation.FranceHistory
```

## Rules of this universe

* Units live in the names: `massKg`, `radiusMeters`, `semiMajorAxisAu`. NASA lost a Mars orbiter over this, we won't lose a test.
* Everything is immutable. You can't change the past, and you can't change a `Planet` either.
* Invalid physics throws an `IllegalArgumentException`. A star with a negative mass is not a discovery, it's a bug.
* Commit messages read like a creation story: *"Give humans a face: eyes, hair and skin that burns in the sun"*. Keep the tradition alive.

## Known limitations

* Only the first elements of the periodic table exist. The rest of them are still waiting for their turn in the queue.
* Chromium and copper break the aufbau rule in real life. Here they behave. Rules are rules.
* Only the Earth's Moon has a real phase. The other moons are just pretty.
* The stars of Andromeda are not modeled yet. They are very far, and we are very busy.
* No dark matter. We looked everywhere, couldn't find it.

## Contributing

Found a mistake in the universe ? Open a pull request. Reviews are friendly, and the universe is big enough for everyone. Please read our [Code of Conduct](CODE_OF_CONDUCT.md) first.
