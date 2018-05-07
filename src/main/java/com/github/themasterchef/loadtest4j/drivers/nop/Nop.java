package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.Request;
import com.github.themasterchef.loadtest4j.Result;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

class Nop implements LoadTester {
    @Override
    public CompletableFuture<Result> run(Request... requests) {
        return completedFuture(new NopResult());
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
