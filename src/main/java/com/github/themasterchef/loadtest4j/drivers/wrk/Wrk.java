package com.github.themasterchef.loadtest4j.drivers.wrk;

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
 * Runs a load test using the classic 'wrk' program by Will Glozer (https://github.com/wg/wrk).
 */
class Wrk implements LoadTester {

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

    private static Result parseStdout(String stdout) {
        final int numErrors = Regex.compile("Non-2xx or 3xx responses: (\\d+)")
                .firstMatch(stdout)
                .map(Integer::parseInt)
                .orElse(0);

        final int numRequests = Regex.compile("(\\d+) requests in ").firstMatch(stdout)
                .map(Integer::parseInt)
                .orElseThrow(() -> new LoadTesterException("The output from wrk was malformatted."));

        return new Result(numErrors, numRequests);
    }

    @Override
    public CompletableFuture<Result> run(Request... requests) {
        final WrkLuaScript script = new WrkLuaScript(requests);

        try (final AutoDeletingTempFile scriptPath = AutoDeletingTempFile.create(script.toString())) {
            final List<String> arguments = new ArgumentBuilder()
                    .addNamedArgument("--connections", valueOf(connections))
                    .addNamedArgument("--duration", String.format("%ds", duration.getSeconds()))
                    .addNamedArgument("--script", scriptPath.getAbsolutePath())
                    .addNamedArgument("--threads", valueOf(threads))
                    .addArgument(url)
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
