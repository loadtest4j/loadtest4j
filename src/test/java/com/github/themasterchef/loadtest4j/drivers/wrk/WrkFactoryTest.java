package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.Driver;
import com.github.themasterchef.loadtest4j.DriverFactory;
import com.github.themasterchef.loadtest4j.drivers.DriverFactoryTest;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Category(UnitTest.class)
public class WrkFactoryTest implements DriverFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Override
    public void testGetMandatoryProperties() {
        final DriverFactory sut = new WrkFactory();

        assertEquals(Arrays.asList("duration", "url"), sut.getMandatoryProperties());
    }

    @Test
    public void testCreate() {
        final DriverFactory sut = new WrkFactory();

        final Map<String, String> properties = new HashMap<>();
        properties.put("duration", "2");
        properties.put("url", "https://example.com");

        final Driver driver = sut.create(properties);

        assertNotNull(driver);
    }
}
