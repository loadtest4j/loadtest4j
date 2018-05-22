# loadtest4j

[![Build Status](https://travis-ci.com/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.com/loadtest4j/loadtest4j)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/29e3d0f7658c499eaee2023cebe3dd2c)](https://www.codacy.com/app/loadtest4j/loadtest4j)
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
public class FooTest {

    private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();

    @Test
    public void testSimple() {
        Result result = loadTester.run(Request.get("/"));

        assertEquals(0, result.getErrors());
    }

    @Test
    public void testCustomised() {
        Result result = loadTester.run(Request.post("/pets")
                                              .withHeader("Content-Type", "application/json")
                                              .withBody("{}"));

        assertEquals(0, result.getErrors());
    }

    @Test
    public void testLotsOfHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Referer", "https://example.com");

        Result result = loadTester.run(Request.get("/pets")
                                              .withHeaders(headers));

        assertEquals(0, result.getErrors());
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
    <version>[a git tag]</version>
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

