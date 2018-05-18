package com.github.themasterchef.loadtest4j.drivers;

import java.util.Map;

public interface DriverFactory {
    Driver create(Map<String, String> properties);
}
