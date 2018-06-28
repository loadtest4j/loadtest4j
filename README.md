# loadtest4j

[![Build Status](https://travis-ci.com/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.com/loadtest4j/loadtest4j)
[![Codecov](https://codecov.io/gh/loadtest4j/loadtest4j/branch/master/graph/badge.svg)](https://codecov.io/gh/loadtest4j/loadtest4j)
[![JitPack Release](https://jitpack.io/v/com.github.loadtest4j/loadtest4j.svg)](https://jitpack.io/#com.github.loadtest4j/loadtest4j)

A simple load test facade for Java.

## Regular usage

1. Add a load test driver to your `pom.xml` (in this example we use the [Wrk driver](https://github.com/loadtest4j/loadtest4j-wrk)):
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
2. If the driver has any external dependencies, install them now.
3. Configure the driver in `src/test/resources/loadtest4j.properties`:
    ```
    loadtest4j.driver = com.github.loadtest4j.drivers.wrk.WrkFactory
    loadtest4j.driver.duration = 60
    loadtest4j.driver.url = https://localhost:3000
    
    loadtest4j.reporter.enabled = true
    ```
4. Write a load test:
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

## More examples

- [loadtest4j-example](https://github.com/loadtest4j/loadtest4j-example): A minimum working example.
- [loadtest4j-advanced-example](https://github.com/loadtest4j/loadtest4j-advanced-example): An advanced example.
