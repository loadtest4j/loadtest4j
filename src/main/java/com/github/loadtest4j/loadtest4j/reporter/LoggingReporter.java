package com.github.loadtest4j.loadtest4j.reporter;

import java.io.PrintStream;

public class LoggingReporter implements Reporter {

    private final PrintStream printStream;

    LoggingReporter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public static LoggingReporter stdout() {
        return new LoggingReporter(System.out);
    }

    @Override
    public void show(String reportUrl) {
        final String msg = String.format("Load test driver report URL: %s", reportUrl);
        printStream.println(msg);
    }
}
