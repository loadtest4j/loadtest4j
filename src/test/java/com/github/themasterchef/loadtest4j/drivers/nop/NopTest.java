package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.drivers.DriverTest;
import com.github.themasterchef.loadtest4j.drivers.Driver;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class NopTest extends DriverTest {
    @Override
    public Driver sut(String serviceUrl) {
        return new Nop();
    }
}
