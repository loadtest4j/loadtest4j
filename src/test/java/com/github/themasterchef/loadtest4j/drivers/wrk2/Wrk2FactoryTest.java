package com.github.themasterchef.loadtest4j.drivers.wrk2;

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

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Category(UnitTest.class)
public class Wrk2FactoryTest implements DriverFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetType() {
        assertEquals("wrk2", new Wrk2Factory().getType());
    }

    @Test
    public void testCreate() {
        final DriverFactory sut = new Wrk2Factory();

        final LoadTester loadTester = sut.create(new HashMap<String, String>() {{
            put("duration", "2");
            put("rate", "2");
        }});

        assertNotNull(loadTester);
    }

    @Test
    public void testCreateInvalid() {
        final DriverFactory sut = new Wrk2Factory();

        thrown.expect(Validator.MissingPropertiesException.class);
        thrown.expect(hasProperty("missingProperties", contains("duration", "rate")));

        sut.create(Collections.emptyMap());
    }
}
