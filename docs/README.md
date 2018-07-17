# loadtest4j

[![Build Status](https://travis-ci.com/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.com/loadtest4j/loadtest4j)
[![Codecov](https://codecov.io/gh/loadtest4j/loadtest4j/branch/master/graph/badge.svg)](https://codecov.io/gh/loadtest4j/loadtest4j)
[![JitPack Release](https://jitpack.io/v/com.github.loadtest4j/loadtest4j.svg)](https://jitpack.io/#com.github.loadtest4j/loadtest4j)

A simple load test facade for Java.

## Contents

- Concepts
  - [Scenario](concepts/scenario.md)
  - [Service Level Agreement (SLA)](concepts/sla.md)
  - [Service Under Test (SUT)](concepts/sut.md)
- [Drivers](drivers.md)
- [Java guide](java.md)
- [Manifesto](manifesto.md)
- [Multiple Drivers](multiple-drivers.md)
- [Multiple Environments](multiple-environments.md)
- [Scala guide](scala.md)

## Usage

1. **Add a [load test driver](drivers.md)** to your `pom.xml`:
    
    ```xml
    <!-- Loadtest4j scans the classpath to find your driver. -->
    <!-- Example: https://github.com/loadtest4j/loadtest4j-gatling -->
    <dependency>
        <groupId>com.github.loadtest4j</groupId>
        <artifactId>loadtest4j-gatling</artifactId>
        <version>[version]</version>
        <scope>test</scope>
    </dependency>
    ```
    
    ```xml
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    ```
    
2. **Install external driver dependencies** if the driver needs them.

3. **Configure the library** in `src/test/resources/loadtest4j.properties`:
    
    ```properties
    loadtest4j.driver.duration = 60
    loadtest4j.driver.url = https://example.com
    loadtest4j.driver.usersPerSecond = 1
    
    loadtest4j.reporter.enabled = true
    ```
    
4. **Write a load test** with your favorite language, test framework, and assertions:
    
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
    
            assertThat(result.getResponseTime().getPercentile(90)).isLessThanOrEqualTo(Duration.ofMillis(500));
        }
    }
    ```

## Example Project

[loadtest4j-example](https://github.com/loadtest4j/loadtest4j-example): A full working example.
