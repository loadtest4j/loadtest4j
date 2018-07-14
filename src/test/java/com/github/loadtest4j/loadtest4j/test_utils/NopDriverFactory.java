package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverFactory;

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
