package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.DriverFactory;
import com.github.themasterchef.loadtest4j.drivers.DriverFactoryTest;
import com.github.themasterchef.loadtest4j.Driver;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class NopFactoryTest implements DriverFactoryTest {

    @Override
    public void testGetMandatoryProperties() {
        final DriverFactory sut = new NopFactory();

        assertEquals(Collections.emptyList(), sut.getMandatoryProperties());
    }

    @Test
    public void testCreate() {
        final DriverFactory sut = new NopFactory();

        final Driver driver = sut.create(Collections.emptyMap());

        assertEquals(Nop.class, driver.getClass());
    }
}
