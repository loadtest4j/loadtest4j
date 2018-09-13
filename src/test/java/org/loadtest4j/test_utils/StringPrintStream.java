package org.loadtest4j.test_utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class StringPrintStream extends PrintStream {

    private final List<String> lines = new ArrayList<>();

    public StringPrintStream() {
        super(new NopOutputStream());
    }

    @Override
    public void println(String msg) {
        this.lines.add(msg);
    }

    public String getMsg() {
        return String.join("\n", lines);
    }

    private static class NopOutputStream extends OutputStream {
        NopOutputStream() {
        }

        public void write(int b) {
        }

        public void close() {
        }

        public void flush() {
        }

        public void write(byte[] b, int off, int len) {
        }

        public void write(byte[] b) {
        }
    }

}
