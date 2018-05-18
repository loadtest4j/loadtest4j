package com.github.themasterchef.loadtest4j;

import java.util.Map;

public interface DriverFactory {
    LoadTester create(Map<String, String> properties);
}
