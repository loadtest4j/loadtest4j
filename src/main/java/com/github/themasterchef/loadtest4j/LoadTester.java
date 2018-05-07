package com.github.themasterchef.loadtest4j;

import java.util.concurrent.CompletableFuture;

/**
 * A facade for running a load test.
 */
public interface LoadTester {

    CompletableFuture<Result> run(Request... requests);

}
