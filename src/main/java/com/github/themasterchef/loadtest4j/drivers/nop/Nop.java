package com.github.themasterchef.loadtest4j.drivers.nop;

import com.github.themasterchef.loadtest4j.*;
import com.github.themasterchef.loadtest4j.drivers.Driver;
import com.github.themasterchef.loadtest4j.drivers.DriverResult;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

class Nop implements Driver {
    @Override
    public CompletableFuture<DriverResult> run(Request... requests) {
        return completedFuture(new NopResult());
    }

    private static class NopResult implements DriverResult {
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
