package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.drivers.DriverFactory;

import java.util.Map;

public class NopFactory implements DriverFactory {

    /**
     * Creates a Nop driver.
     */
    @Override
    public LoadTester create(Map<String, String> properties) {
        return new Nop();
    }
}
