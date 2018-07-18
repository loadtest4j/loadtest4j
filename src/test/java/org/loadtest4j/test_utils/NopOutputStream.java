package org.loadtest4j.test_utils;

import java.io.OutputStream;

public class NopOutputStream extends OutputStream {
    public NopOutputStream() {
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
