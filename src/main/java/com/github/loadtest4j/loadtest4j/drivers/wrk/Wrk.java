package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.DriverRequest;
import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.DriverResult;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Runs a load test using the 'wrk' program by Will Glozer (https://github.com/wg/wrk).
 */
class Wrk implements Driver {

    private final int connections;
    private final Duration duration;
    private final String executable;
    private final int threads;
    private final String url;

    Wrk(int connections, Duration duration, String executable, int threads, String url) {
        this.connections = connections;
        this.duration = duration;
        this.executable = executable;
        this.threads = threads;
        this.url = url;
    }

    @Override
    public DriverResult run(Collection<DriverRequest> requests) {
        validateNotEmpty(requests);

        final WrkLuaScript script = new WrkLuaScript(requests);

        try (final AutoDeletingTempFile scriptPath = AutoDeletingTempFile.create(script.toString())) {
            final List<String> arguments = new ArgumentBuilder()
                    .addNamedArgument("--connections", valueOf(connections))
                    .addNamedArgument("--duration", String.format("%ds", duration.getSeconds()))
                    .addNamedArgument("--script", scriptPath.getAbsolutePath())
                    .addNamedArgument("--threads", valueOf(threads))
                    .addArgument(url)
                    .build();

            final Command command = new Command(arguments, executable);

            final Process process = new Shell().start(command);

            final int exitStatus = process.run();

            if (exitStatus != 0) throw new LoadTesterException("Command exited with an error");

            final String stdout = process.readStdout();
            return parseStdout(stdout);
        }
    }

    private static <T> void validateNotEmpty(Collection<T> requests) {
        if (requests.size() < 1) {
            throw new LoadTesterException("No requests were specified for the load test.");
        }
    }

    private static DriverResult parseStdout(String stdout) {
        final long numErrors = Regex.compile("Non-2xx or 3xx responses: (\\d+)")
                .firstMatch(stdout)
                .map(Long::parseLong)
                .orElse(0L);

        final long numRequests = Regex.compile("(\\d+) requests in ").firstMatch(stdout)
                .map(Long::parseLong)
                .orElseThrow(() -> new LoadTesterException("The output from wrk was malformatted."));

        return new DriverResult(numErrors, numRequests);
    }
}
