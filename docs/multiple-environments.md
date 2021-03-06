# Multiple Environments

Multiple environment support (e.g. development, staging, production).

## Setup

### loadtest4j.properties

Parameterise the driver configuration file:

```properties
# Constant properties
loadtest4j.driver.duration = 300

# Environment-specific properties
loadtest4j.driver.url = ${loadtest4j.driver.url}
```

### pom.xml

Inject those parameters from Maven properties:

```xml
<project>
    <!-- Define each loadtest4j environment in a Maven profile. -->
    <profiles>
        <profile>
            <id>development</id>
            <!-- Make your development profile active by default, so that your load tests work on your laptop. -->
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <loadtest4j.driver.url>http://localhost:3000</loadtest4j.driver.url>
            </properties>
        </profile>
        <profile>
            <id>staging</id>
            <properties>
                <loadtest4j.driver.url>https://staging.example.com</loadtest4j.driver.url>
            </properties>
        </profile>
        <profile>
            <id>production</id>
            <properties>
                <loadtest4j.driver.url>https://example.com</loadtest4j.driver.url>
            </properties>
        </profile>
    </profiles>

    <build>
        <!-- Enable resource filtering to inject Maven properties into loadtest4j.properties -->
        <testResources>
            <testResource>
                <filtering>true</filtering>
                <directory>src/test/resources</directory>
            </testResource>
        </testResources>
    </build>
</project>
```

## Run

Run the normal Maven goal to test against development. Then invoke Maven again with each environment profile that you want to test.

**Note:** If you are using custom executions of the Maven Surefire Plugin to run your load test group separately (e.g. `mvn test-compile surefire-test@load`), then you need Maven 3.3.1 or higher.

```bash
#!/bin/sh

# Your main CI build
mvn test

# Load test after deployment to your staging environment
mvn test -P staging

# Load test after deployment to your production environment
mvn test -P production
```
