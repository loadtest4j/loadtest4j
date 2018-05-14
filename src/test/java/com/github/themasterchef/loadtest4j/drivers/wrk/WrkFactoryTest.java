package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.drivers.DriverFactory;
import com.github.themasterchef.loadtest4j.drivers.DriverFactoryTest;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import com.github.themasterchef.loadtest4j.util.Validator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;

@Category(UnitTest.class)
public class WrkFactoryTest implements DriverFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreate() {
        final DriverFactory sut = new WrkFactory();

        final Map<String, String> properties = new HashMap<>();
        properties.put("duration", "2");
        properties.put("url", "https://example.com");

        final LoadTester loadTester = sut.create(properties);

        assertNotNull(loadTester);
    }

    @Test
    public void testCreateInvalid() {
        final DriverFactory sut = new WrkFactory();

        thrown.expect(Validator.MissingPropertiesException.class);
        thrown.expect(hasProperty("missingProperties", contains("duration", "url")));

        sut.create(Collections.emptyMap());
    }
}
