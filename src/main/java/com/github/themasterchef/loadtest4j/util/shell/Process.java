package com.github.themasterchef.loadtest4j.util.shell;

import com.github.themasterchef.loadtest4j.LoadTesterException;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Process {

    private final java.lang.Process process;

    Process(java.lang.Process process) {
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
