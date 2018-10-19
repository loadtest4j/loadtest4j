package org.loadtest4j.test_utils;

import org.loadtest4j.BodyPart;

import java.nio.file.Path;

public class MockBodyPartVisitor implements BodyPart.Visitor<String> {
    @Override
    public String stringPart(String part) {
        return part;
    }

    @Override
    public String filePart(Path part) {
        return part.toString();
    }
}
