package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.DriverFactory;
import com.github.themasterchef.loadtest4j.Driver;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class NopFactory implements DriverFactory {

    @Override
    public Collection<String> getMandatoryProperties() {
        return Collections.emptyList();
    }

    /**
     * Creates a Nop driver.
     */
    @Override
    public Driver create(Map<String, String> properties) {
        return new Nop();
    }
}
