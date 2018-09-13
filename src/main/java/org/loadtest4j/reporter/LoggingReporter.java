package org.loadtest4j.reporter;

import org.loadtest4j.driver.DriverResponseTime;
import org.loadtest4j.driver.DriverResult;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Consumer;

public class LoggingReporter implements Reporter {

    private final Consumer<String> logger;

    public LoggingReporter(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public void show(DriverResult driverResult) {
        final long duration = driverResult.getActualDuration().toMillis();
        final long ok = driverResult.getOk();
        final long ko = driverResult.getKo();
        final long total = ok + ko;
        final DriverResponseTime responseTime = driverResult.getResponseTime();
        final long min = responseTime.getPercentile(0).toMillis();
        final long p50 = responseTime.getPercentile(50).toMillis();
        final long p75 = responseTime.getPercentile(75).toMillis();
        final long p95 = responseTime.getPercentile(95).toMillis();
        final long p99 = responseTime.getPercentile(99).toMillis();
        final long max = responseTime.getPercentile(100).toMillis();
        final double rps = getRequestsPerSecond(total, driverResult.getActualDuration());
        final Optional<String> reportUrl = driverResult.getReportUrl();

        println("Summary");
        println("================================================================================");
        println("");
        println("Global Information");
        println("--------------------------------------------------------------------------------");
        println("");
        println("    duration (ms)        %55d", duration);
        println("    mean requests/sec    %55f", rps);
        println("");
        println("Request Count");
        println("--------------------------------------------------------------------------------");
        println("");
        println("    ok                   %55d", ok);
        println("    ko                   %55d", ko);
        println("    total                %55d", total);
        println("");
        println("Response Time");
        println("--------------------------------------------------------------------------------");
        println("");
        println("    min (ms)             %55d", min);
        println("    p50 (ms)             %55d", p50);
        println("    p75 (ms)             %55d", p75);
        println("    p95 (ms)             %55d", p95);
        println("    p99 (ms)             %55d", p99);
        println("    max (ms)             %55d", max);
        println("");
        reportUrl.ifPresent(url -> {
            println("Driver Report URL");
            println("--------------------------------------------------------------------------------");
            println("");
            println(url);
            println("");
        });
    }

    static double getRequestsPerSecond(long requests, Duration actualDuration) {
        final double durationSeconds = ((double) actualDuration.toMillis()) / 1000;

        // Do not divide by zero
        if (durationSeconds == 0) {
            return 0;
        } else {
            return requests / durationSeconds;
        }
    }

    private void println(String format, Object... args) {
        final String str = String.format(format, args);
        println(str);
    }

    private void println(String s) {
        this.logger.accept(s);
    }
}
