package com.github.themasterchef.loadtest4j;

import java.util.Collection;
import java.util.Map;

public interface DriverFactory {
    Collection<String> getMandatoryProperties();

    Driver create(Map<String, String> properties);
}
