package com.github.themasterchef.loadtest4j;

import org.junit.Test;

public abstract class DriverFactoryTest {
    protected abstract DriverFactory sut();

    @Test
    public abstract void testGetMandatoryProperties();

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMandatoryPropertiesIsImmutable() {
        final DriverFactory sut = sut();

        sut.getMandatoryProperties().add("foobarbaz123");
    }

    @Test
    public abstract void testCreate();
}
