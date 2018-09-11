package org.loadtest4j.reporter;

import org.loadtest4j.driver.DriverResult;

import java.io.PrintStream;
import java.time.Duration;

public class VerboseReporter implements Reporter {

    private final PrintStream printStream;

    VerboseReporter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public static VerboseReporter stdout() {
        return new VerboseReporter(System.out);
    }

    @Override
    public void show(DriverResult driverResult) {
        final long duration = driverResult.getActualDuration().toMillis();
        final long ok = driverResult.getOk();
        final long ko = driverResult.getKo();
        final long count = ok + ko;
        final long min = driverResult.getResponseTime().getPercentile(0).toMillis();
        final long p50 = driverResult.getResponseTime().getPercentile(50).toMillis();
        final long p75 = driverResult.getResponseTime().getPercentile(75).toMillis();
        final long p95 = driverResult.getResponseTime().getPercentile(95).toMillis();
        final long p99 = driverResult.getResponseTime().getPercentile(99).toMillis();
        final long max = driverResult.getResponseTime().getPercentile(100).toMillis();
        final double rps = getRequestsPerSecond(count, driverResult.getActualDuration());

        println("================================================================================");
        println("---- Global Information --------------------------------------------------------");
        println("> duration              %56d", duration);
        println("> mean requests/sec     %56f", rps);
        println("---- Request Count -------------------------------------------------------------");
        println("> total                 %56d", count);
        println("> ok                    %56d", ok);
        println("> ko                    %56d", ko);
        println("---- Response Time Distribution ------------------------------------------------");
        println("> min                   %56d", min);
        println("> 50th percentile       %56d", p50);
        println("> 75th percentile       %56d", p75);
        println("> 95th percentile       %56d", p95);
        println("> 99th percentile       %56d", p99);
        println("> max                   %56d", max);
        println("================================================================================");
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
        this.printStream.println(s);
    }
}
