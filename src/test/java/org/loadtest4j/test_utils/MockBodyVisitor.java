package org.loadtest4j.test_utils;

import org.loadtest4j.Body;

import java.nio.file.Path;

public class MockBodyVisitor implements Body.Visitor<String> {

    @Override
    public String string(String body) {
        return body;
    }

    @Override
    public String file(Path path) {
        return path.toString();
    }
}
