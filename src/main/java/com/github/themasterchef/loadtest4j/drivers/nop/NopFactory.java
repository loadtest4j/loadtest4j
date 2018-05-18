package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.drivers.DriverFactory;
import com.github.themasterchef.loadtest4j.drivers.Driver;

import java.util.Map;

public class NopFactory implements DriverFactory {

    /**
     * Creates a Nop driver.
     */
    @Override
    public Driver create(Map<String, String> properties) {
        return new Nop();
    }
}
