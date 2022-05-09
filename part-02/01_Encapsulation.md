# Encapsulation

This codebase (like any other) is far from perfect. It appears that at some point, someone had the _brilliant idea_ to
represent everything relating to lego parts as `Maps` with `String` key and `Int` value. Although that data structure
succeeds to capture what that the app needs to store about lego parts, it fails to encapsulate the behavior needed for
dealing with lego parts. That `behavior` is spread across the codebase as util methods acting on the `Map<String, Int>`
or on properties with that type. Look for them here:

- [LegoStock requireValidMissingLegoParts](src/main/kotlin/eu/luminis/workshop/smallsteps/logic/domainService/LegoStock.kt)
  line 68-75
- [LegoStockQueriesKt mergeAndSum](src/main/kotlin/eu/luminis/workshop/smallsteps/logic/appService/LegoStockQueries.kt)
  line 23
- [LegoStockQueriesKt mergePartsAndSumValues](src/main/kotlin/eu/luminis/workshop/smallsteps/logic/appService/LegoStockQueries.kt)
  line 27
- [LegoStockQueriesKt toSortedListOfPairs](src/main/kotlin/eu/luminis/workshop/smallsteps/logic/appService/LegoStockQueries.kt)
  line 33
- [LegoPartCatalog](src/main/kotlin/eu/luminis/workshop/smallsteps/logic/domainService/catalog/LegoPartCatalog.kt)
- [InMemoryLegoPartCatalog](src/main/kotlin/eu/luminis/workshop/smallsteps/persistence/InMemoryLegoPartCatalog.kt)
- [LegoBox](src/main/kotlin/eu/luminis/workshop/smallsteps/logic/domainService/state/LegoStockState.kt) line 17
- [IncompleteReturn](src/main/kotlin/eu/luminis/workshop/smallsteps/logic/domainService/state/LegoStockState.kt) line 22

---

## Exercise

It would be nice to **encapsulate all behavior around lego parts.**

- Introduce a new `LegoParts` class.
- Discuss with your pairing-partner what `behavior` this class should have.
- `Write` the class TDD-style. So first write a failing assertion, then the implementation, then your next assertion,
  and so on... until you cannot think of any more cases to test.

---

Now that we have a new `LegoParts` class, it's time to use it throughout the codebase.

- Look for all occurrences of `Map<String, Int>` and replace them with your `LegoParts` class.
- BUT you have to take **small steps**!
- Introduce `LegoParts` one file at a time.
- Keep everything working while you do so. **All code must compile, all tests must pass.**
- Commit your changes after every file where you have introduced your `LegoParts` class. Think of each commit as
  representing a small PR that can be integrated into your main branch.
- Hint: there are `at least 8 files` that you'll need to change.

---

With no more raw `Map<String, Int>` in place, `discuss with your pairing partner` how the encapsulation of LegoParts
behavior makes it easier change this codebase in the future.
