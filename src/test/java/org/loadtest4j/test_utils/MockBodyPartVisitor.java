package org.loadtest4j.test_utils;

import org.loadtest4j.BodyPart;

import java.nio.file.Path;

public class MockBodyPartVisitor implements BodyPart.Visitor<String> {
    @Override
    public String stringPart(String name, String content) {
        return name + "," + content;
    }

    @Override
    public String filePart(Path content) {
        return content.toString();
    }
}
