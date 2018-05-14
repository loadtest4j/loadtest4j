package com.github.themasterchef.loadtest4j.util.shell;

import java.util.Collections;
import java.util.List;

public class Command {

    private final List<String> arguments;
    private final String launchPath;

    public Command(List<String> arguments, String launchPath) {
        this.arguments = arguments;
        this.launchPath = launchPath;
    }

    protected List<String> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    protected String getLaunchPath() {
        return launchPath;
    }
}
