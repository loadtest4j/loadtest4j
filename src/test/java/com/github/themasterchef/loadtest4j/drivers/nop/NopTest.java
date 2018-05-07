package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.LoadTesterTest;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class NopTest extends LoadTesterTest {
    @Override
    public LoadTester sut(String serviceUrl) {
        return new Nop();
    }
}
