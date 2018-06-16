# loadtest4j

[![Build Status](https://travis-ci.com/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.com/loadtest4j/loadtest4j)
[![Codecov](https://codecov.io/gh/loadtest4j/loadtest4j/branch/master/graph/badge.svg)](https://codecov.io/gh/loadtest4j/loadtest4j)
[![JitPack Release](https://jitpack.io/v/com.github.loadtest4j/loadtest4j.svg)](https://jitpack.io/#com.github.loadtest4j/loadtest4j)

A simple load test facade for Java.

## Regular usage

Install a load test driver (in this example we use the [Wrk driver](https://github.com/loadtest4j/loadtest4j-wrk)):

```xml
<dependency>
    <groupId>com.github.loadtest4j</groupId>
    <artifactId>loadtest4j-wrk</artifactId>
</dependency>
```

Add the file `loadtest4j.properties` to your `src/test/resources` directory and configure the load test driver:

```
loadtest4j.driver = com.github.loadtest4j.drivers.wrk.WrkFactory
loadtest4j.driver.url = https://localhost:3000
```

Then write a load test:

```java
@Category(LoadTest.class)
public class PetStoreLoadTest {

    private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();

    @Test
    public void testSimple() {
        List<Request> requests = List.of(Request.get("/"));

        Result result = loadTester.run(requests);

        assertEquals(0, result.getKo());
    }

    @Test
    public void testCustomised() {
        List<Request> requests = List.of(Request.post("/pets")
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{}"));

        Result result = loadTester.run(requests);

        assertEquals(0, result.getKo());
    }

    @Test
    public void testLotsOfHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Referer", "https://example.com");

        List<Request> requests = List.of(Request.get("/pets")
                                                .withHeaders(headers));

        Result result = loadTester.run(requests);

        assertEquals(0, result.getKo());
    }
}
```

## To write a driver

Add the [JitPack](https://jitpack.io) repository to your pom.xml:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then add this library:

```xml
<dependency>
    <groupId>com.github.loadtest4j</groupId>
    <artifactId>loadtest4j</artifactId>
    <version>[git tag]</version>
</dependency>
```

Then write your driver and its counterpart factory:

```java
class MyDriver implements Driver {
    public DriverResult run(Collection<DriverRequest> requests) {
        // ...
    }
}
```

