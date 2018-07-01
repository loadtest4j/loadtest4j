# loadtest4j

[![Build Status](https://travis-ci.com/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.com/loadtest4j/loadtest4j)
[![Codecov](https://codecov.io/gh/loadtest4j/loadtest4j/branch/master/graph/badge.svg)](https://codecov.io/gh/loadtest4j/loadtest4j)
[![JitPack Release](https://jitpack.io/v/com.github.loadtest4j/loadtest4j.svg)](https://jitpack.io/#com.github.loadtest4j/loadtest4j)

A simple load test facade for Java.

## Regular usage

1. **Add a [load test driver](drivers.md)** to your `pom.xml`:
    
    ```xml
    <project>
        <dependencies>
            <dependency>
                <!-- Example: https://github.com/loadtest4j/loadtest4j-wrk -->
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
    
2. **Install external driver dependencies** if the driver needs them.

3. **Configure the driver** in `src/test/resources/loadtest4j.properties`:
    
    ```
    loadtest4j.driver = com.github.loadtest4j.drivers.wrk.WrkFactory
    loadtest4j.driver.duration = 60
    loadtest4j.driver.url = https://localhost:3000
    
    loadtest4j.reporter.enabled = true
    ```
    
4. **Write a load test** in your favorite test framework, with your favorite assertions:
    
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
    
            assertThat(result.getPercentKo()).isEqualTo(0);
        }
    }
    ```

## More examples

- [loadtest4j-example](https://github.com/loadtest4j/loadtest4j-example): A minimum working example.
- [loadtest4j-advanced-example](https://github.com/loadtest4j/loadtest4j-advanced-example): An advanced example.
