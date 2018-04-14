package com.github.themasterchef.loadtest4j.drivers;

import com.github.themasterchef.loadtest4j.LoadTester;

import java.util.Map;

public interface DriverFactory {
    String getType();

    LoadTester create(Map<String, String> properties);
}
