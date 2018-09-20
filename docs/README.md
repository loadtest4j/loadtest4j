# loadtest4j

[![Build Status](https://travis-ci.com/loadtest4j/loadtest4j.svg?branch=master)](https://travis-ci.com/loadtest4j/loadtest4j)
[![Codecov](https://codecov.io/gh/loadtest4j/loadtest4j/branch/master/graph/badge.svg)](https://codecov.io/gh/loadtest4j/loadtest4j)
[![Maven Central](https://img.shields.io/maven-central/v/org.loadtest4j/loadtest4j.svg)](http://repo2.maven.org/maven2/org/loadtest4j/loadtest4j/)

A simple load test facade for Java.

## Contents

- Concepts
  - [Driver](concepts/driver.md)
  - [Scenario](concepts/scenario.md)
  - [Service Level Agreement (SLA)](concepts/sla.md)
  - [Service Level Indicator (SLI)](concepts/sli.md)
  - [Service Level Objective (SLO)](concepts/slo.md)
  - [Service Under Test (SUT)](concepts/sut.md)
- [Driver Registry](registry.md)
- Example Projects
  - [Java](https://github.com/loadtest4j/loadtest4j-example)
  - [Scala](https://github.com/loadtest4j/loadtest4j-example-scala)
- [Manifesto](manifesto.md)
- [Multiple Drivers](multiple-drivers.md)
- [Multiple Environments](multiple-environments.md)
- [Source Code (Git)](https://github.com/loadtest4j/loadtest4j)
- Style Guides
  - [JUnit](guides/junit.md)
  - [ScalaTest](guides/scalatest.md)

## What it does

Loadtest4j lets you write load tests as plain old xUnit tests.

The benefits include...

- **Executable [SLOs](concepts/slo.md)** - fully automated and verifiable. No longer an inert piece of paper.
- **Portable load tests** - which will run anywhere that a unit test can run.
- **Full integration with existing xUnit tools** - coverage reports, trend trackers, test grouping, IDE support, and more.
- **Quicker detection of performance bugs** in your release pipeline - catch problems before your code hits production.

## Usage

1. **Add a load test driver** from the [registry](registry.md) to the POM:
    
    ```xml
    <!-- Example: https://github.com/loadtest4j/loadtest4j-gatling -->
    <dependency>
        <groupId>org.loadtest4j.drivers</groupId>
        <artifactId>loadtest4j-gatling</artifactId>
        <scope>test</scope>
    </dependency>
    ```

2. **Configure the library** in `src/test/resources/loadtest4j.properties`:
    
    ```properties
    loadtest4j.driver.duration = 60
    loadtest4j.driver.url = https://example.com
    loadtest4j.driver.usersPerSecond = 1
    ```
    
3. **Write a load test** with your favorite language, test framework, and assertions:
    
    ```java
    public class PetStoreLT {
    
        private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();
    
        @Test
        public void testFindPets() {
            List<Request> requests = List.of(Request.get("/pet/findByStatus")
                                                    .withHeader("Accept", "application/json")
                                                    .withQueryParam("status", "available"));
    
            Result result = loadTester.run(requests);
    
            assertThat(result.getResponseTime().getPercentile(90))
                .isLessThanOrEqualTo(Duration.ofMillis(500));
        }
    }
    ```

4. **Declare your load test** in the POM:

    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <executions>
            <execution>
                <id>integration</id>
                <phase>integration-test</phase>
                <goals>
                    <goal>test</goal>
                </goals>
                <configuration>
                    <includes>
                        <include>**/*IT.java</include>
                    </includes>
                </configuration>
            </execution>
            <execution>
                <id>load</id>
                <phase>integration-test</phase>
                <goals>
                    <goal>test</goal>
                </goals>
                <configuration>
                    <includes>
                        <include>**/*LT.java</include>
                    </includes>
                </configuration>
            </execution>
        </executions>
    </plugin>
    ```

5. **Run the test** with Maven or your IDE:

    ```bash
    # Run all tests
    mvn verify
    
    # Run load tests
    mvn test-compile surefire:test@load
    ```
