package org.loadtest4j.test_utils;

import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverFactory;

import java.util.*;

public class NopDriverFactory implements DriverFactory {
    @Override
    public Set<String> getMandatoryProperties() {
        return new HashSet<>(Arrays.asList("bar", "foo"));
    }

    @Override
    public Driver create(Map<String, String> properties) {
        return new NopDriver();
    }
}
