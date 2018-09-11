package org.loadtest4j.reporter;

import org.loadtest4j.driver.DriverResult;

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
    public void show(DriverResult driverResult) {
        driverResult.getReportUrl().ifPresent(this::show);
    }

    private void show(String reportUrl) {
        final String msg = String.format("Load test driver report URL: %s", reportUrl);
        printStream.println(msg);
    }
}
