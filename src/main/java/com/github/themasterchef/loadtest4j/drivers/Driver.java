package com.github.themasterchef.loadtest4j.drivers;

import com.github.themasterchef.loadtest4j.Request;

import java.util.concurrent.CompletableFuture;

/**
 * The driver that runs a load test under the covers.
 */
public interface Driver {
    CompletableFuture<DriverResult> run(Request... requests);
}
