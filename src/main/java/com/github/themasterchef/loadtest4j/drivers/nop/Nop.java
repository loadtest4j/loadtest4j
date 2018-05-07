package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.LoadTesterException;
import com.github.themasterchef.loadtest4j.Request;
import com.github.themasterchef.loadtest4j.Result;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

class Nop implements LoadTester {
    @Override
    public CompletableFuture<Result> run(Request... requests) {
        validateNotEmpty(requests);

        return completedFuture(new NopResult());
    }

    private static <T> void validateNotEmpty(T[] requests) {
        if (requests.length < 1) {
            throw new LoadTesterException("No requests were specified for the load test.");
        }
    }

    private static class NopResult implements Result {
        @Override
        public long getErrors() {
            return 0;
        }

        @Override
        public long getRequests() {
            return 0;
        }
    }
}
