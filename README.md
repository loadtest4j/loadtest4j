# loadtest4j

[![Build Status](https://travis-ci.org/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.org/loadtest4j/loadtest4j)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0dedacd1b10941df95f08ebe0774e2be)](https://www.codacy.com/app/loadtest4j/loadtest4j)
[![JitPack Release](https://jitpack.io/v/com.github.loadtest4j/loadtest4j.svg)](https://jitpack.io/#com.github.loadtest4j/loadtest4j)

A simple load test facade for Java.

## Drivers

* wrk

## Setup

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

## Usage

Add the file `loadtest4j.properties` to your `src/test/resources` directory and configure the load test driver:

```
loadtest4j.driver = com.github.loadtest4j.loadtest4j.drivers.wrk.WrkFactory
loadtest4j.driver.duration = 30
loadtest4j.driver.url = https://example.com
```

Then write a load test:

```java
public class FooTest {

    private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();

    @Before
    private void setup() {
        // Set up your service somewhere before load testing
    }

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