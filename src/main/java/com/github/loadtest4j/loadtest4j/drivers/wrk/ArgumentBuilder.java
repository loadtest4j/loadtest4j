package com.github.loadtest4j.loadtest4j.drivers.wrk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ArgumentBuilder {
    private final List<String> arguments = new ArrayList<>();

    protected ArgumentBuilder addArgument(String argument) {
        this.arguments.add(argument);
        return this;
    }

    protected ArgumentBuilder addNamedArgument(String name, String value) {
        this.arguments.add(name);
        this.arguments.add(value);
        return this;
    }

    protected List<String> build() {
        return Collections.unmodifiableList(this.arguments);
    }
}
