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
- [Manifesto](manifesto.md)
- [Multiple Drivers support](multiple-drivers.md)
- [Multiple Environments support](multiple-environments.md)
- [Scala support](scala.md)

## Usage

1. **Add a [load test driver](drivers.md)** to your `pom.xml`:
    
    ```xml
    <!-- Example: https://github.com/loadtest4j/loadtest4j-gatling -->
    <dependency>
        <groupId>com.github.loadtest4j</groupId>
        <artifactId>loadtest4j-gatling</artifactId>
        <version>[version]</version>
        <scope>test</scope>
    </dependency>
    
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    ```
    
2. **Install external driver dependencies** if the driver needs them.

3. **Configure the driver** in `src/test/resources/loadtest4j.properties`:
    
    ```properties
    loadtest4j.driver = com.github.loadtest4j.drivers.gatling.GatlingFactory
    loadtest4j.driver.duration = 60
    loadtest4j.driver.url = https://example.com
    
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

## Example Project

[loadtest4j-example](https://github.com/loadtest4j/loadtest4j-example): A full working example.
