package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverFactory;

import java.time.Duration;
import java.util.*;

public class WrkFactory implements DriverFactory {

    @Override
    public Set<String> getMandatoryProperties() {
        return setOf("duration", "url");
    }

    /**
     * Creates a Wrk driver using the following properties.
     *
     * Mandatory properties:
     *
     * - `duration`
     * - `url`
     *
     * Optional properties:
     *
     * - `connections` (defaults to 1)
     * - `executable` (defaults to `wrk`, and is located using the PATH)
     * - `threads` (defaults to 1)
     */
    @Override
    public Driver create(Map<String, String> properties) {
        final Duration duration = Duration.ofSeconds(Long.valueOf(properties.get("duration")));
        final int connections = Integer.valueOf(properties.getOrDefault("connections", "1"));
        final String executable = properties.getOrDefault("executable", "wrk");
        final int threads = Integer.valueOf(properties.getOrDefault("threads", "1"));
        final String url = properties.get("url");

        return new Wrk(connections, duration, executable, threads, url);
    }

    private static Set<String> setOf(String... values) {
        // This utility method can be replaced when Java 9+ is more widely adopted
        final Set<String> internalSet = new HashSet<>(Arrays.asList(values));
        return Collections.unmodifiableSet(internalSet);
    }
}
