package com.github.loadtest4j.loadtest4j.driver_reporter;

public class LoggingDriverReporter implements DriverReporter {
    @Override
    public void show(String reportUrl) {
        final String msg = String.format("The driver has generated a custom report. This is available at the following URL: %s", reportUrl);
        print(msg);
    }

    protected void print(String msg) {
        System.out.println(msg);
    }
}
