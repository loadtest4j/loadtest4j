package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.drivers.DriverFactory;
import com.github.themasterchef.loadtest4j.drivers.nop.NopFactory;
import com.github.themasterchef.loadtest4j.drivers.wrk.WrkFactory;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

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
    public void testCreateDriver() {
        final Collection<DriverFactory> driverFactories = Collections.singletonList(new WrkFactory());
        final Properties driverProperties = doubleProperties("loadtest4j.driver", "wrk",
                                                             "loadtest4j.driver.duration", "2");
        final LoadTesterFactory factory = new LoadTesterFactory(driverFactories, driverProperties);

        final LoadTester loadTester = factory.createLoadTester();

        assertNotNull(loadTester);
    }

    private static Properties doubleProperties(String key1, String value1, String key2, String value2) {
        return new Properties() {{
            setProperty(key1, value1);
            setProperty(key2, value2);
        }};
    }

    private static Properties singletonProperties(String key, String value) {
        return new Properties() {{
            setProperty(key, value);
        }};
    }

    private static Properties emptyProperties() {
        return new Properties();
    }
}
