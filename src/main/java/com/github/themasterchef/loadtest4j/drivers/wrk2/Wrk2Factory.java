package com.github.themasterchef.loadtest4j.drivers.wrk2;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.drivers.DriverFactory;
import com.github.themasterchef.loadtest4j.util.Validator;

import java.time.Duration;
import java.util.Map;

public class Wrk2Factory implements DriverFactory {

    @Override
    public String getType() {
        return "wrk2";
    }

    /**
     * Creates a Wrk2 driver using the following properties.
     *
     * Mandatory properties:
     *
     * - `duration`
     * - `rate`
     *
     * Optional properties:
     *
     * - `connections` (defaults to 1)
     * - `executable` (defaults to `wrk2`, and is located using the PATH)
     * - `threads` (defaults to 1)
     */
    @Override
    public LoadTester create(Map<String, String> properties) {
        Validator.validatePresenceOf(properties, "duration", "rate");

        final Duration duration = Duration.ofSeconds(Long.valueOf(properties.get("duration")));
        final int requestsPerSecond = Integer.valueOf(properties.get("rate"));
        final int connections = Integer.valueOf(properties.getOrDefault("connections", "1"));
        final String executable = properties.getOrDefault("executable", "wrk2");
        final int threads = Integer.valueOf(properties.getOrDefault("threads", "1"));

        return new Wrk2(connections, duration, executable, requestsPerSecond, threads);
    }
}
