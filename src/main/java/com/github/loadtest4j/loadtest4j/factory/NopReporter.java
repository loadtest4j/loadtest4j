package com.github.loadtest4j.loadtest4j.factory;

class NopReporter implements Reporter {
    @Override
    public void show(String reportUrl) {
        // No-op
    }
}
