package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.Driver;
import com.github.themasterchef.loadtest4j.DriverFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class NopFactory implements DriverFactory {

    @Override
    public Set<String> getMandatoryProperties() {
        return Collections.emptySet();
    }

    /**
     * Creates a Nop driver.
     */
    @Override
    public Driver create(Map<String, String> properties) {
        return new Nop();
    }
}
