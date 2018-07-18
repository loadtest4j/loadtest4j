package org.loadtest4j.reporter;

public class NopReporter implements Reporter {
    @Override
    public void show(String reportUrl) {
        // No-op
    }
}
