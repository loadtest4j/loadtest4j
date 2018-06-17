package com.github.loadtest4j.loadtest4j.driver_reporter;

import java.io.PrintStream;

public class LoggingDriverReporter implements DriverReporter {

    private final PrintStream printStream;

    LoggingDriverReporter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public static LoggingDriverReporter stdout() {
        return new LoggingDriverReporter(System.out);
    }

    @Override
    public void show(String reportUrl) {
        final String msg = String.format("Load test driver report URL: %s", reportUrl);
        printStream.println(msg);
    }
}
