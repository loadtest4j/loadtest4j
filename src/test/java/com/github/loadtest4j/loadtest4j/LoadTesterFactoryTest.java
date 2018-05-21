package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver.NopDriver;
import com.github.loadtest4j.loadtest4j.driver.NopDriverFactory;
import com.github.loadtest4j.loadtest4j.junit.IntegrationTest;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Category(UnitTest.class)
public class LoadTesterFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPublicApi() {
        // The static factory method will reference loadtest4j.properties in the test resources
        final LoadTester loadTester = LoadTesterFactory.getLoadTester();

        assertNotNull(loadTester);
    }

    @Test
    public void testCreateDriverWithNoDriverFactories() {
        final LoadTesterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner();
        final Properties driverProperties = singletonProperties("loadtest4j.driver", "foo");
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Invalid load test driver type.");

        factory.createLoadTester();
    }

    @Test
    public void testCreateDriverWithNoConfig() {
        final LoadTesterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner()
                .add(new NopDriverFactory());
        final Properties driverProperties = emptyProperties();
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following load test driver properties were not found: [loadtest4j.driver]. Please specify them either as JVM properties or in loadtest4j.properties.");

        factory.createLoadTester();
    }

    @Test
    public void testCreateDriverWithMissingProperties() {
        final DriverFactory stubFactory = new StubDriverFactory();
        final LoadTesterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner()
                .add(stubFactory);
        final Properties driverProperties = singletonProperties("loadtest4j.driver", stubFactory.getClass().getName());
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following load test driver properties were not found: [bar, foo]. Please specify them either as JVM properties or in loadtest4j.properties.");

        factory.createLoadTester();
    }

    @Test
    public void testCreateDriver() {
        final DriverFactory nopFactory = new NopDriverFactory();
        final LoadTesterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner()
                .add(nopFactory);
        final Properties driverProperties = singletonProperties("loadtest4j.driver", nopFactory.getClass().getName());
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        final LoadTester loadTester = factory.createLoadTester();

        assertNotNull(loadTester);
    }

    @Category(IntegrationTest.class)
    public static class PropertiesResourceTest {
        @Test
        public void getProperties() {
            final LoadTesterFactory.PropertiesResource sut = new LoadTesterFactory.PropertiesResource("/props/example.properties");

            final Properties properties = sut.getProperties();

            final Properties expectedProperties = new Properties();
            expectedProperties.put("foo", "bar");
            assertEquals(expectedProperties, properties);
        }

        @Test
        public void getEmptyProperties() {
            final LoadTesterFactory.PropertiesResource sut = new LoadTesterFactory.PropertiesResource("/props/fake.properties");

            final Properties properties = sut.getProperties();

            assertEquals(new Properties(), properties);
        }
    }

    @Category(UnitTest.class)
    public static class PropertiesSubsetTest {
        @Test
        public void testGetSubsetAndStripPrefix() {
            final Properties props = new Properties();
            props.put("foo", "1");
            props.put("foo.bar", "2");
            props.put("foo.bar.baz", "3");

            assertEquals(Collections.singletonMap("baz", "3"), LoadTesterFactory.PropertiesSubset.getSubsetAndStripPrefix(props, "foo.bar"));
        }
    }

    private static Properties singletonProperties(String key, String value) {
        final Properties p = new Properties();
        p.setProperty(key, value);
        return p;
    }

    private static Properties emptyProperties() {
        return new Properties();
    }

    private static class MockDriverFactoryScanner implements LoadTesterFactory.DriverFactoryScanner {
        private final Collection<DriverFactory> factories = new ArrayList<>();

        private MockDriverFactoryScanner add(DriverFactory factory) {
            factories.add(factory);
            return this;
        }

        @Override
        public Optional<DriverFactory> findFirst(String className) {
            return factories.stream()
                    .filter(factory -> factory.getClass().getName().equals(className))
                    .findFirst();
        }
    }

    public static class StubDriverFactory implements DriverFactory {
        @Override
        public Set<String> getMandatoryProperties() {
            return new HashSet<>(Arrays.asList("bar", "foo"));
        }

        @Override
        public Driver create(Map<String, String> properties) {
            return new NopDriver();
        }
    }

}
