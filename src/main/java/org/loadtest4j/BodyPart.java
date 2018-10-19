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
     * @param part the string content of the body part
     * @return a string body part
     */
    public static BodyPart string(String part) {
        return new StringPart(part);
    }

    /**
     * Create a file body part.
     *
     * @param part the path to the file that will be a body part
     * @return a file body part
     */
    public static BodyPart file(Path part) {
        return new FilePart(part);
    }

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {
        R stringPart(String part);

        R filePart(Path part);
    }

    private static class FilePart extends BodyPart {
        private final Path part;

        private FilePart(Path part) {
            this.part = part;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.filePart(part);
        }
    }

    private static class StringPart extends BodyPart {
        private final String part;

        private StringPart(String part) {
            this.part = part;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.stringPart(part);
        }
    }
}
