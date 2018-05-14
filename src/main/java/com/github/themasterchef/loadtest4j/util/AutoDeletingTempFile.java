package com.github.themasterchef.loadtest4j.util;

import com.github.themasterchef.loadtest4j.LoadTesterException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AutoDeletingTempFile implements AutoCloseable {

    private final Path path;

    private AutoDeletingTempFile(Path path) {
        this.path = path;
    }

    @Override
    public void close() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new LoadTesterException(e);
        }
    }

    protected boolean exists() {
        return Files.exists(path);
    }

    public String getAbsolutePath() {
        return path.toAbsolutePath().toString();
    }

    public static AutoDeletingTempFile create(String contents) {
        try {
            final Path path = Files.createTempFile(null, null);
            writeStringToFile(path.toFile(), contents);
            return new AutoDeletingTempFile(path);
        } catch (IOException e) {
            throw new LoadTesterException(e);
        }
    }

    private static void writeStringToFile(File file, String contents) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(contents);
        }
    }
}
