# AssertJ example

Use one of the following AssertJ styles.

## Soft assertions

AssertJ assertions are 'hard' by default - the first failing assertion stops the test. This is unhelpful when asserting on a POJO, so use [`SoftAssertions`](https://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#soft-assertions) instead.

```java

final Result result = loadTester.run(requests);

assertSoftly(s -> {
    s.assertThat(result.getRequestsPerSecond()).as("Requests Per Second").isGreaterThanOrEqualTo(1);
    s.assertThat(result.getResponseTime().getPercentile(75)).as("p75 Response Time").isLessThanOrEqualTo(Duration.ofMillis(500));
    // and so on...
})
```

## Custom assertions

Follow the AssertJ [custom assertions guide](https://joel-costigliola.github.io/assertj/assertj-core-custom-assertions.html) to write assertions on `Result` which are relevant to your [SLA](../concepts/sla.md).

Note: The custom assertions class must be proxied through `SoftAssertions` for the same reason as normal assertions.

## Advanced: One assertion per test

This style might be more readable if your SLA has many requirements. 

To replicate the style in the [ScalaTest example](scalatest.md) you need to make up for the lack of lazy evaluation in Java.

First encapsulate the `lazy val` emulation in a utility class:

```java
public class LazyLoadTester {
    public static Supplier<Result> run(List<Request> requests) {
        return memoize(new Loadtest4jSupplier(requests));
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
    
    // The memoize step is critical. It ensures that a given LoadTest4jSupplier is only run ONCE.
    private static <T> Supplier<T> memoize(Supplier<T> delegate) {
        return new MemoizingSupplier<>(delegate);
    }
    
    // Either roll your own, or use your favorite library for this.
    private static class MemoizingSupplier<T> implements Supplier<T> {
        // ...
    }
}
```

Then write the test:

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
}
``` 