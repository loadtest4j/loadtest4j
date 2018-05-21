package com.github.loadtest4j.loadtest4j.drivers.nop;

import com.github.loadtest4j.loadtest4j.DriverFactory;
import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class NopFactoryTest {

    private DriverFactory sut() {
        return new NopFactory();
    }

    @Test
    public void testGetMandatoryProperties() {
        final DriverFactory sut = sut();

        assertEquals(Collections.emptySet(), sut.getMandatoryProperties());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMandatoryPropertiesIsImmutable() {
        final DriverFactory sut = sut();

        sut.getMandatoryProperties().add("foobarbaz123");
    }

    @Test
    public void testCreate() {
        final DriverFactory sut = sut();

        final Driver driver = sut.create(Collections.emptyMap());

        assertEquals(Nop.class, driver.getClass());
    }
}
