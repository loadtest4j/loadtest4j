package com.github.themasterchef.loadtest4j.util.shell;

import com.github.themasterchef.loadtest4j.LoadTesterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

}
