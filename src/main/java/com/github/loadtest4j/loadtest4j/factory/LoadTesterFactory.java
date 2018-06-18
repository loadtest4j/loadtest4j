package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTester;
import com.github.loadtest4j.loadtest4j.utils.PropertiesResource;

import java.util.*;

public final class LoadTesterFactory {

    /**
     * The primary entry point to the library. Clients should request {@link LoadTester} instances from here.
     *
     * Driver configuration uses the following order of precedence (1 = highest priority):
     *
     * 1. The system properties.
     * 2. The loadtest4j.properties file in the jar resources folder.
     *
     * @return A configured {@link LoadTester}
     */
    public static LoadTester getLoadTester() {
        final Properties properties = new Properties();
        properties.putAll(new PropertiesResource("/loadtest4j.properties").getProperties());
        properties.putAll(System.getProperties());
        final Map<String, String> props = (Map) properties;

        final DriverAdapterFactory driverFactory = DriverAdapterFactory.defaultFactory();
        return driverFactory.create(props);
    }

    private LoadTesterFactory() {
    }


}
