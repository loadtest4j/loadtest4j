package org.loadtest4j.test_utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpyLogger implements Consumer<String> {

    private final List<String> lines = new ArrayList<>();

    @Override
    public void accept(String msg) {
        this.lines.add(msg);
    }

    public String getMsg() {
        return String.join("\n", lines);
    }
}
