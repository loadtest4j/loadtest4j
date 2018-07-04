# Multiple Drivers example

Basic support is available for running your load test suite using a different driver in each environment.

Multiple Driver support will be improved in future, most likely in the form of a custom Maven plugin.

## Setup

### pom.xml

In a nutshell:

1. Bring in the loadtest4j API jar explicitly. Then bring in all drivers, excluding the API jar.
2. Define base configuration of the Maven Surefire plugin.
3. Define each environment as a Maven profile. Pass driver config to Maven Surefire plugin in each environment.

```xml
<dependencies>
    <!-- Bring in the loadtest4j API jar first. -->
    <dependency>
        <groupId>com.github.loadtest4j</groupId>
        <artifactId>loadtest4j</artifactId>
        <version>[version]</version>
    </dependency>
    <!-- Bring in loadtest4j driver Foo. Exclude the loadtest4j API. -->
    <dependency>
        <groupId>com.github.loadtest4j</groupId>
        <artifactId>loadtest4j-foo</artifactId>
        <version>[version]</version>
        <exclusions>
            <exclusion>
                <groupId>com.github.loadtest4j</groupId>
                <artifactId>loadtest4j</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <!-- Bring in loadtest4j driver Bar. Exclude the loadtest4j API. -->
    <dependency>
        <groupId>com.github.loadtest4j</groupId>
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

<profiles>
    <!-- Define each loadtest4j environment in a Maven profile. -->
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
                        <!-- Pass the Foo driver config as JVM system properties, via the Maven Surefire plugin. (Not via loadtest4j.properties.) --> 
                        <systemPropertyVariables>
                            <loadtest4j.driver>com.github.loadtest4j.drivers.foo.FooFactory</loadtest4j.driver>
                            <loadtest4j.driver.duration>4</loadtest4j.driver.duration>
                            <loadtest4j.driver.url>http://localhost:3000</loadtest4j.driver.url>
                            
                            <loadtest4j.reporter.enabled>true</loadtest4j.reporter.enabled>
                        </systemPropertyVariables>
                    </configuration>
                </plugin>
            </plugins>
        </build>
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
                            <loadtest4j.driver>com.github.loadtest4j.drivers.bar.BarFactory</loadtest4j.driver>
                            <loadtest4j.driver.duration>1800</loadtest4j.driver.duration>
                            <loadtest4j.driver.url>https://example.com</loadtest4j.driver.url>
                        </systemPropertyVariables>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.12.4</version>
            <configuration>
                <!-- Run unit tests by default. -->
                <groups>com.github.loadtest4j.example.junit.UnitTest</groups>
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
                        <groups>com.github.loadtest4j.example.junit.LoadTest</groups>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
