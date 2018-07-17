# Java guide

A load test style guide for Java (using JUnit and AssertJ).

## Soft assertions

AssertJ assertions are 'hard' by default - the first failing assertion stops the test. This is unhelpful when asserting on a POJO, so use [`SoftAssertions`](https://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#soft-assertions) instead.

```java
@Category(LoadTest.class)
public class PetStoreTest {
    
    private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();
    
    @Test
    public void testFindPets() {
        final Result result = loadTester.run(requests);
        
        assertSoftly(s -> {
            s.assertThat(result.getRequestsPerSecond()).as("Requests Per Second").isGreaterThanOrEqualTo(1);
            s.assertThat(result.getResponseTime().getPercentile(75)).as("p75 Response Time").isLessThanOrEqualTo(Duration.ofMillis(500));
            s.assertThat(result.getPercentOk()).as("Percent OK").isGreaterThanOrEqualTo(99.99);
            // and so on...
        });
    }
}
```

## Custom assertions

Follow the AssertJ [custom assertions guide](https://joel-costigliola.github.io/assertj/assertj-core-custom-assertions.html) to write assertions on `Result` which are relevant to your [SLA](concepts/sla.md).

Note: The custom assertions class must be proxied through `SoftAssertions` for the same reason as normal assertions.

## Advanced: One assertion per test

This style might be more readable if your SLA has many requirements. 

```java
@Category(LoadTest.class)
public class FindPetsTest {
    private static final List<Request> requests = Arrays.asList(Request.get("/"), Request.get("/pets"));
    
    private static final Supplier<Result> result = LazyLoadTester.run(requests);
    
    @Test
    public void testRequestsPerSecond() {
        assertThat(result.get().getRequestsPerSecond()).isGreaterThanOrEqualTo(1);
    }
   
    @Test
    public void testP75ResponseTime() {
        assertThat(result.get().getResponseTime().getPercentile(75)).isLessThanOrEqualTo(Duration.ofMillis(500));
    }
    
    @Test
    public void testPercentOk() {
        assertThat(result.get().getPercentOk()).isGreaterThanOrEqualTo(99.99);
    }
}
```

To use this style you need to defer load test execution until a test requires it, to avoid blocking test suite initialization while the LoadTester executes.

Java does not yet support lazy evaluation, so we must emulate this with a utility function that returns a `Supplier<Result>`. Here is an example:

```java
public class LazyLoadTester {
    public static Supplier<Result> run(List<Request> requests) {
        // The memoize step is critical: it ensures that a given LoadTest4jSupplier is only run ONCE.
        // Either write your own memoizer, or use your favorite library.
        return Suppliers.memoize(new Loadtest4jSupplier(requests));
    }
    
    private static class Loadtest4jSupplier implements Supplier<Result> {
        private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();
        
        private final List<Request> requests;
        
        private Loadtest4jSupplier(List<Request> requests) {
            this.requests = requests;
        }
        
        @Override
        public Result get() {
            return loadTester.run(requests);
        }
    }
}
```
