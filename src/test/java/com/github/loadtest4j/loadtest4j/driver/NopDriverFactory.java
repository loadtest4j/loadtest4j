package com.github.loadtest4j.loadtest4j.driver;

import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverFactory;

import java.util.*;

public class NopDriverFactory implements DriverFactory {
    @Override
    public Set<String> getMandatoryProperties() {
        return Collections.emptySet();
    }

    @Override
    public Driver create(Map<String, String> properties) {
        return new NopDriver();
    }
}
