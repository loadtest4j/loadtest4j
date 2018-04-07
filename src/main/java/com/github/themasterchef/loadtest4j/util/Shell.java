package com.github.themasterchef.loadtest4j.util;

import com.github.themasterchef.loadtest4j.LoadTesterException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Shell {

    public Process start(Command command) {
        final List<String> cmd = new ArrayList<>();
        cmd.add(command.getLaunchPath());
        cmd.addAll(command.getArguments());

        try {
            return new Process(new ProcessBuilder(cmd).redirectInput(ProcessBuilder.Redirect.PIPE).start());
        } catch (IOException e) {
            throw new LoadTesterException(e);
        }
    }

    public static class Process {

        private final java.lang.Process process;

        private Process(java.lang.Process process) {
            this.process = process;
        }

        public String readStdout() {
            // From https://stackoverflow.com/a/5445161
            final InputStream istream = process.getInputStream();
            final Scanner scanner = new Scanner(istream).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }

        public CompletableFuture<Integer> run() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return process.waitFor();
                } catch (InterruptedException e) {
                    throw new LoadTesterException(e);
                }
            });
        }
    }

    public static class Command {

        private final List<String> arguments;
        private final String launchPath;

        Command(String launchPath) {
            this(Collections.emptyList(), launchPath);
        }

        public Command(List<String> arguments, String launchPath) {
            this.arguments = arguments;
            this.launchPath = launchPath;
        }

        List<String> getArguments() {
            return Collections.unmodifiableList(arguments);
        }

        String getLaunchPath() {
            return launchPath;
        }
    }
}
