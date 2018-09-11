package org.loadtest4j.test_utils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class PrintStreamSpy extends PrintStream {
    private final List<String> lines = new ArrayList<>();

    public PrintStreamSpy() {
        super(new NopOutputStream());
    }

    @Override
    public void println(String msg) {
        this.lines.add(msg);
    }

    public String getMsg() {
        return String.join("\n", lines);
    }

    public List<String> getLines() {
        return lines;
    }
}
