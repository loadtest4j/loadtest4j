package org.loadtest4j;

import java.nio.file.Path;

/**
 * An Algebraic Data Type representing an HTTP request body part.
 */
public abstract class BodyPart {
    private BodyPart() {

    }

    /**
     * Create a body part from a simple string.
     *
     * @param name The name (identifier) for the body part
     * @param content the string content of the body part
     * @return a string body part
     */
    public static BodyPart string(String name, String content) {
        return new StringPart(name, content);
    }

    /**
     * Create a file body part.
     *
     * @param content the path to the file that will be a body part
     * @return a file body part
     */
    public static BodyPart file(Path content) {
        return new FilePart(content);
    }

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {
        R stringPart(String name, String content);

        R filePart(Path content);
    }

    private static class FilePart extends BodyPart {
        private final Path content;

        private FilePart(Path content) {
            this.content = content;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.filePart(content);
        }
    }

    private static class StringPart extends BodyPart {
        private final String name;
        private final String content;

        private StringPart(String name, String content) {
            this.name = name;
            this.content = content;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.stringPart(name, content);
        }
    }
}
