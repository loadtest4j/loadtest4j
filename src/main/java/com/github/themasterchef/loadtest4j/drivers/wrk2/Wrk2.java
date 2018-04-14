package com.github.themasterchef.loadtest4j.drivers.wrk2;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.LoadTesterException;
import com.github.themasterchef.loadtest4j.Request;
import com.github.themasterchef.loadtest4j.Result;
import com.github.themasterchef.loadtest4j.util.ArgumentBuilder;
import com.github.themasterchef.loadtest4j.util.AutoDeletingTempFile;
import com.github.themasterchef.loadtest4j.util.Regex;
import com.github.themasterchef.loadtest4j.util.Shell;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.valueOf;

/**
 * Runs a load test using the 'wrk2' program by Gil Tene (https://github.com/giltene/wrk2).
 */
class Wrk2 implements LoadTester {

    private final int connections;
    private final Duration duration;
    private final String executable;
    private final int requestsPerSecond;
    private final int threads;

    Wrk2(int connections, Duration duration, String executable, int requestsPerSecond, int threads) {
        this.connections = connections;
        this.duration = duration;
        this.executable = executable;
        this.requestsPerSecond = requestsPerSecond;
        this.threads = threads;
    }

    private static Result parseStdout(String stdout) {
        final int numErrors = Regex.compile("Non-2xx or 3xx responses: (\\d+)")
                .firstMatch(stdout)
                .map(Integer::parseInt)
                .orElse(0);

        final int numRequests = Regex.compile("(\\d+) requests in ")
                .firstMatch(stdout)
                .map(Integer::parseInt)
                .orElseThrow(() -> new LoadTesterException("The output from wrk2 was malformatted."));

        return new Result(numErrors, numRequests);
    }

    @Override
    public CompletableFuture<Result> run(Request request) {
        final Wrk2LuaScript script = new Wrk2LuaScript(request.getBody(), request.getHeaders(), request.getMethod());

        try (final AutoDeletingTempFile scriptPath = AutoDeletingTempFile.create(script.toString())) {
            final List<String> arguments = new ArgumentBuilder()
                    .addNamedArgument("--connections", valueOf(connections))
                    .addNamedArgument("--duration", String.format("%ds", duration.getSeconds()))
                    .addNamedArgument("--rate", valueOf(requestsPerSecond))
                    .addNamedArgument("--script", scriptPath.getAbsolutePath())
                    .addNamedArgument("--threads", valueOf(threads))
                    .addArgument(request.getUrl())
                    .build();

            final Shell.Command command = new Shell.Command(arguments, executable);

            final Shell.Process process = new Shell().start(command);

            return process.run().thenApply(exitStatus -> {
                if (exitStatus != 0) throw new LoadTesterException("Command exited with an error");

                final String stdout = process.readStdout();
                return parseStdout(stdout);
            });
        }
    }
}
