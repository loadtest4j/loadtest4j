package org.loadtest4j.reporter;

import java.util.function.Consumer;

public class LoggingReporter implements Reporter {

    private final Consumer<String> logger;

    public LoggingReporter(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public void show(String reportUrl) {
        final String msg = String.format("Driver report URL: %s", reportUrl);
        logger.accept(msg);
    }
}
