package com.github.themasterchef.loadtest4j.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArgumentBuilder {
    private final List<String> arguments = new ArrayList<>();

    public ArgumentBuilder addArgument(String argument) {
        this.arguments.add(argument);
        return this;
    }

    public ArgumentBuilder addNamedArgument(String name, String value) {
        this.arguments.add(name);
        this.arguments.add(value);
        return this;
    }

    public List<String> build() {
        return Collections.unmodifiableList(this.arguments);
    }
}
