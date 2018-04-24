package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.drivers.DriverFactory;
import com.github.themasterchef.loadtest4j.util.Validator;

import java.time.Duration;
import java.util.Map;

public class WrkFactory implements DriverFactory {

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
    public LoadTester create(Map<String, String> properties) {
        Validator.validatePresenceOf(properties, "duration", "url");

        final Duration duration = Duration.ofSeconds(Long.valueOf(properties.get("duration")));
        final int connections = Integer.valueOf(properties.getOrDefault("connections", "1"));
        final String executable = properties.getOrDefault("executable", "wrk");
        final int threads = Integer.valueOf(properties.getOrDefault("threads", "1"));
        final String url = properties.get("url");

        return new Wrk(connections, duration, executable, threads, url);
    }
}
