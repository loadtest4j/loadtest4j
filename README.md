# loadtest4j

[![Build Status](https://travis-ci.com/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.com/loadtest4j/loadtest4j)
[![Codecov](https://codecov.io/gh/loadtest4j/loadtest4j/branch/master/graph/badge.svg)](https://codecov.io/gh/loadtest4j/loadtest4j)
[![JitPack Release](https://jitpack.io/v/com.github.loadtest4j/loadtest4j.svg)](https://jitpack.io/#com.github.loadtest4j/loadtest4j)

A simple load test facade for Java.

## Regular usage

Add a load test driver to your `pom.xml` (in this example we use the [Wrk driver](https://github.com/loadtest4j/loadtest4j-wrk)):

```xml
<project>
    <dependencies>
        <dependency>
            <groupId>com.github.loadtest4j</groupId>
            <artifactId>loadtest4j-wrk</artifactId>
            <version>[git tag]</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
</project>
```

Configure the load test driver in `src/test/resources/loadtest4j.properties`:

```
loadtest4j.driver = com.github.loadtest4j.drivers.wrk.WrkFactory
loadtest4j.driver.url = https://localhost:3000

loadtest4j.reporter.enabled = true
```

Then write a load test:

```java
@Category(LoadTest.class)
public class PetStoreLoadTest {

    private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();

    @Test
    public void testFindPets() {
        List<Request> requests = List.of(Request.get("/pet/findByStatus")
                                                .withHeader("Accept", "application/json")
                                                .withQueryParam("status", "available"));

        Result result = loadTester.run(requests);

        assertEquals(0, result.getKo());
    }

    @Test
    public void testAddPet() {
        Map<String, String> headers = Map.of(
                "Accept", "application/json",
                "Content-Type", "application/json");

        List<Request> requests = List.of(Request.post("/pet")
                                                .withHeaders(headers)
                                                .withBody("{}"));

        Result result = loadTester.run(requests);

        assertEquals(0, result.getKo());
    }
}
```

## To write a driver

Add this library to your `pom.xml`:

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

public class MyDriverFactory implements DriverFactory {
    public Driver create(Map<String, String> properties) {
        // ...
    }
}
```

