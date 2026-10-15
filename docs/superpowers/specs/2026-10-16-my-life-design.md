# My life — design

Date: 2026-10-16

## Goal

Let JUniverse say this sentence about a person:

> I do judo, ski and surf, I like mangas and drawing. I work in a company, I have friends, and we party on our
> birthdays.

Today only judo and drawing can be said. This adds the missing activities, a birthday on humans, and a new
`society` layer for what links humans together: their work in a company, their friendships and the birthday parties
that follow from both. A runnable `simulation.MyLife` tells the story.

## Non-goals

- Children, birth dates and ages that move with time: `Body` keeps its fixed `ageYears`. A birthday is a day of the
  year, not a birth date.
- Several jobs at once, careers, salaries, contracts, unemployment.
- Friendship strength, falling out, or any relationship other than friendship and colleagues (couples, enemies...).
- Party contents (food, music, drinks); a party is who, whose and when.
- Companies as legal entities: no shareholders, revenue, country or registration.

## Layout

- `com.enlil.universe.life.human` — existing; `Human` gains an optional birthday.
- `com.enlil.universe.world.activity` — existing; `Activities` gains `SKIING`, `SURFING`, `MANGA`.
- `com.enlil.universe.society` — new: `Company`, `Employment`, `Friendship`, `SocialCircle`, `BirthdayParty`.
- `com.enlil.universe.simulation` — new runnable `MyLife`.

`life` and `civilization` do not depend on each other today, and stay so: an employment joins a `Human` (`life`) to
an `Occupation` (`civilization`), so it lives in the new `society` layer, which depends on both. `Human` knows
nothing of its friends or job; like parents-to-children, those links would need cycles between immutable objects.

## Activities

New constants in `world.activity.Activities`, METs from the Compendium of Physical Activities (Ainsworth et al.,
2011):

| Constant  | Type      | Category            | MET | Compendium entry                                       |
|-----------|-----------|---------------------|-----|--------------------------------------------------------|
| `SKIING`  | `Sport`   | sport, 1 per side   | 5.3 | 19150 skiing, downhill, alpine, moderate effort       |
| `SURFING` | `Sport`   | sport, 1 per side   | 3.0 | 18260 surfing, body or board, general                  |
| `MANGA`   | `Pastime` | `INTELLECTUAL`      | 1.3 | 09030 sitting, reading (no entry for comics)          |

"I like mangas" is modeled as reading manga being a hobby: `Mind.hobbies` already means "what this human enjoys in
their free time", so no separate notion of taste is added. Intensities follow: skiing and surfing moderate (3 MET is where moderate starts), manga
sedentary.

## Birthday

`Human` gets a nullable `MonthDay birthday`, `@Getter(AccessLevel.NONE)`, set by the builder's `birthday(...)`,
exposed as `Optional<MonthDay> getBirthday()`, empty when unknown. `learn(...)` carries it over.

- `birthdayIn(int year)`: the `LocalDate` it falls on that year; a 29 February birthday is celebrated on
  28 February in common years (`MonthDay.atYear`). Empty when the birthday is unknown. Years are calendar years (no
  BCE here; `java.time` handles them).

## Society

Humans have no `equals`: two humans are the same only if they are the same object, everywhere below.

### Company

Immutable, `@Getter`.

- `name` (non-blank), `foundedYear` (no year 0).
- `toString()` is the name.

A company does not list its employees; employments point to it.

### Employment

Immutable, `@Getter`. A human working in a company at a given occupation since a year.

- `employee` (`Human`), `employer` (`Company`), `occupation` (`Occupation`), `sinceYear`.
- Validation (`IllegalArgumentException`): `sinceYear` not before the company was founded; the occupation existed
  in `sinceYear` (`Occupation.existedIn`); no year 0.

### Friendship

Immutable, two-way. `Friendship.between(a, b, sinceYear)`.

- `getFriends()`: the two humans; `involves(human)`; `friendOf(human)`: the other one
  (`IllegalArgumentException` if the human is not part of it).
- `sinceYear`, no year 0.
- Validation: the two humans are distinct objects.
- `equals` / `hashCode` on the unordered pair (by identity), so `between(a, b)` equals `between(b, a)`.

### SocialCircle

Immutable: a group of humans, their friendships and their employments. Built with a Lombok `@Builder` and
`@Singular` `friendships` and `employments`, like `Universe`.

- `getMembers()`: every human appearing in a friendship or an employment, in insertion order.
- `friendsOf(human)`, `areFriends(a, b)`, `mutualFriends(a, b)`.
- `employmentOf(human)`: `Optional<Employment>`.
- `colleaguesOf(human)`: the other members employed by the same company (by identity), empty if unemployed.
- `DUNBAR_NUMBER = 150` and `exceedsDunbarNumber(human)`: Dunbar's (1992) cognitive limit on stable relationships,
  counting friends and colleagues together.
- `birthdayPartiesIn(int year)`: one `BirthdayParty` per member who has a known birthday and at least one friend,
  hosted on their birthday that year with all their friends as guests, sorted by date. A member without friends
  celebrates alone, which is not a party.
- Validation: no duplicate friendship (same pair twice); at most one employment per human.

### BirthdayParty

Immutable, `@Getter`.

- `host` (`Human`), `date` (`LocalDate`), `guests` (`Set<Human>`, identity-based, unmodifiable).
- Validation: the host has a birthday and `date` is `host.birthdayIn(date.getYear())`; at least one guest; the host
  is not their own guest.
- `headcount()`: guests plus the host.

## Simulation

`simulation.MyLife`, a `main` in the spirit of `FranceHistory`, prints the sentence back from the model:

1. Me: a `Human` whose hobbies are judo, skiing, surfing, manga and drawing; for each hobby, its intensity and the
   kcal I burn in an hour (`human.kcalBurned`).
2. Work: my `Employment` (occupation, company, since when) and my colleagues.
3. Friends: my friends, and for one of them our mutual friends.
4. Parties: every birthday party in the current year, date, host and headcount.

The people, company and occupation in it are fictitious placeholders (a company named "Acme", the existing
`Engineer` occupation, friends with made-up names and birthdays), to be replaced with real ones by whoever runs it.

## Tests

One class per new type, JUnit 5 `Assertions`, snake_case names with `@DisplayName`, as elsewhere:

- `ActivitiesTest` (or the existing activity tests): the three new METs and intensities.
- `HumanTest`: birthday absent by default; `birthdayIn` for a normal date and for 29 February in leap and common
  years; `learn` keeps the birthday.
- `CompanyTest`, `EmploymentTest`: validation of names and years, occupation not yet existing.
- `FriendshipTest`: symmetry of `equals`, `friendOf`, refusing a friendship with oneself.
- `SocialCircleTest`: friends, mutual friends, colleagues, duplicate friendship and double employment rejected,
  Dunbar's number, parties sorted by date, friendless and birthday-less members skipped.
- `BirthdayPartyTest`: wrong date, host as guest, no guest, headcount.

## Docs

`CLAUDE.md`: describe the `society` layer in the architecture list, the birthday in the `life` paragraph, and
`MyLife` in the commands and `simulation` paragraph.
