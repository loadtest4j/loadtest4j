# Multiple Drivers

**Note: This is an advanced feature. Ensure that you are comfortable with Maven and loadtest4j before using this.**

Basic support is available for running your load test suite using a different driver in each environment.

Multiple Driver support will be improved in future, most likely in the form of a custom Maven plugin.

## Setup

### pom.xml

In a nutshell:

1. Bring in the loadtest4j API jar explicitly.
2. Define base configuration of the Maven Surefire plugin.
3. Define each environment as a Maven profile. Do the following in each environment.
    - Bring the driver dependency in, excluding the loadtest4j API jar. 
    - Pass driver config via the Maven Surefire plugin (not loadtest4j.properties).

```xml
<!-- Bring in the loadtest4j API jar first. -->
<dependency>
    <groupId>com.github.loadtest4j</groupId>
    <artifactId>loadtest4j</artifactId>
    <version>[version]</version>
</dependency>
```

```xml
<!-- Define each loadtest4j environment in a Maven profile. -->
<profiles>
    <profile>
        <id>development</id>
        <!-- Make your development profile active by default, so that your load tests work on your laptop. -->
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <!-- Pass the Foo driver config as JVM system properties, via the Maven Surefire plugin. --> 
                        <systemPropertyVariables>
                            <loadtest4j.driver.duration>4</loadtest4j.driver.duration>
                            <loadtest4j.driver.url>http://localhost:3000</loadtest4j.driver.url>                            
                        </systemPropertyVariables>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        <dependencies>
            <!-- Bring in loadtest4j driver Foo. Exclude the loadtest4j API. -->
            <dependency>
                <groupId>com.example</groupId>
                <artifactId>loadtest4j-foo</artifactId>
                <version>[version]</version>
                <exclusions>
                    <exclusion>
                        <groupId>com.github.loadtest4j</groupId>
                        <artifactId>loadtest4j</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
        </dependencies>
    </profile>
    <profile>
        <id>production</id>
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <!-- Pass the Bar driver config as JVM system properties, via the Maven Surefire plugin. --> 
                        <systemPropertyVariables>
                            <loadtest4j.driver.duration>1800</loadtest4j.driver.duration>
                            <loadtest4j.driver.url>https://example.com</loadtest4j.driver.url>
                        </systemPropertyVariables>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        <dependencies>
            <!-- Bring in loadtest4j driver Bar. Exclude the loadtest4j API. -->
            <dependency>
                <groupId>com.example</groupId>
                <artifactId>loadtest4j-bar</artifactId>
                <version>[version]</version>
                <exclusions>
                    <exclusion>
                        <groupId>com.github.loadtest4j</groupId>
                        <artifactId>loadtest4j</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```

```xml
<!-- Separate (slow) load tests from (fast) unit tests. -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.12.4</version>
    <configuration>
        <!-- Run unit tests by default. -->
        <groups>com.example.junit.UnitTest</groups>
    </configuration>
    <executions>
        <execution>
            <id>load</id>
            <phase>integration-test</phase>
            <goals>
                <goal>test</goal>
            </goals>
            <configuration>
                <!-- Run load tests in the verify phase. -->
                <groups>com.example.junit.LoadTest</groups>
            </configuration>
        </execution>
    </executions>
</plugin>
```
