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
  - [Java](https://github.com/loadtest4j/loadtest4j-example-java)
  - [Scala](https://github.com/loadtest4j/loadtest4j-example-scala)
- [Manifesto](manifesto.md)
- [Multiple Drivers](multiple-drivers.md)
- [Multiple Environments](multiple-environments.md)
- [Source Code (Git)](https://github.com/loadtest4j/loadtest4j)
- Style Guides
  - [JUnit](guides/junit.md)
  - [ScalaTest](guides/scalatest.md)

## What it does

Loadtest4j is a Java library that lets you write load tests as plain old xUnit tests.

The benefits include...

- **Executable [SLOs](concepts/slo.md)** - fully automated and verifiable. No longer an inert piece of paper.
- **Portable load tests** - which will run anywhere that a unit test can run.
- **Full integration with existing xUnit tools** - coverage reports, trend trackers, test grouping, IDE support, and more.
- **Quicker detection of performance bugs** in your release pipeline - catch problems before your code hits production.

## Usage

With a new or existing Maven project open in your favorite editor...

### 1. Add the library

Add a load test driver library from the [registry](registry.md) to your Maven project POM:

```xml
<!-- Example: https://github.com/loadtest4j/loadtest4j-gatling -->
<dependency>
    <groupId>org.loadtest4j.drivers</groupId>
    <artifactId>loadtest4j-gatling</artifactId>
    <scope>test</scope>
</dependency>
```

### 2. Create the load tester

Use **either** the Factory **or** the Builder. (Note: The options available, and builder class name, depend on the driver used.)

#### Factory

```java
LoadTester loadTester = LoadTesterFactory.getLoadTester();
```

```properties
# src/test/resources/loadtest4j.properties

loadtest4j.driver.duration = 60
loadtest4j.driver.url = https://example.com
loadtest4j.driver.usersPerSecond = 1
```

#### Builder

```java
LoadTester loadTester = GatlingBuilder.withUrl("https://example.com")
                                      .withDuration(Duration.ofSeconds(60))
                                      .withUsersPerSecond(1)
                                      .build();
``` 

### 3. Write load tests
 
Write load tests with your favorite language, test framework, and assertions:
    
```java
public class PetStoreLT {

    private static final LoadTester loadTester = /* see step 2 */ ;

    @Test
    public void shouldFindPets() {
        List<Request> requests = List.of(Request.get("/pet/findByStatus")
                                                .withHeader("Accept", "application/json")
                                                .withQueryParam("status", "available"));

        Result result = loadTester.run(requests);

        assertThat(result.getResponseTime().getPercentile(90))
            .isLessThanOrEqualTo(Duration.ofMillis(500));
    }
}
```

### 4. Declare your load tests

Tell Maven how to discover and run your load tests:

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

### 5. Run the tests
 
Run the tests with Maven or your IDE:

```bash
# Run all tests
mvn verify

# Only run load tests
mvn test-compile surefire:test@load
```

## Advanced usage

### Multipart requests

Attach an arbitrary number of string parts or file parts to the multipart request body.

```java
public class PetStoreLT {
    @Test
    public void shouldAddPet() {
        BodyPart stringPart = BodyPart.string("name", "content");
        BodyPart filePart = BodyPart.file(Paths.get("foo.txt"));
        
        Request request = Request.post("/pets").withBody(stringPart, filePart);
    }
}
```

### Decorator

Attach custom behaviors to a `LoadTester` using the decorator. The behaviors will execute after each invocation of that 
`LoadTester`. This feature can be used for a wide variety of tasks, such as logging or visualising a `Result`.

Note: Custom behaviors are not included with the core library.

```java
public class PetStoreLT {
    private static final LoadTester loadTester = new LoadTesterDecorator()
        .add(new Slf4jReporter())
        .add(new HtmlReporter())
        .decorate(LoadTesterFactory.getLoadTester());
}
```
