package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.drivers.nop.NopFactory;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.*;

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
        final Collection<DriverFactory> driverFactories = Collections.emptyList();
        final Properties driverProperties = singletonProperties("loadtest4j.driver", "foo");
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Invalid load test driver type.");

        factory.createLoadTester();
    }

    @Test
    public void testCreateDriverWithNoConfig() {
        final Collection<DriverFactory> driverFactories = Collections.singletonList(new NopFactory());
        final Properties driverProperties = emptyProperties();
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following load test driver properties were not found: [loadtest4j.driver]. Please specify them either as JVM properties or in loadtest4j.properties.");

        factory.createLoadTester();
    }

    @Test
    public void testCreateDriverWithMissingProperties() {
        final DriverFactory stubFactory = new StubDriverFactory();
        final Collection<DriverFactory> driverFactories = Collections.singletonList(stubFactory);
        final Properties driverProperties = singletonProperties("loadtest4j.driver", stubFactory.getClass().getName());
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following load test driver properties were not found: [bar, foo]. Please specify them either as JVM properties or in loadtest4j.properties.");

        factory.createLoadTester();
    }

    @Test
    public void testCreateDriver() {
        final DriverFactory nopFactory = new NopFactory();
        final Collection<DriverFactory> driverFactories = Collections.singletonList(nopFactory);
        final Properties driverProperties = singletonProperties("loadtest4j.driver", nopFactory.getClass().getName());
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        final LoadTester loadTester = factory.createLoadTester();

        assertNotNull(loadTester);
    }

    private static Properties singletonProperties(String key, String value) {
        final Properties p = new Properties();
        p.setProperty(key, value);
        return p;
    }

    private static Properties emptyProperties() {
        return new Properties();
    }

    private static class StubDriverFactory implements DriverFactory {

        @Override
        public Set<String> getMandatoryProperties() {
            return new HashSet<>(Arrays.asList("bar", "foo"));
        }

        @Override
        public Driver create(Map<String, String> properties) {
            return null;
        }
    }
}
