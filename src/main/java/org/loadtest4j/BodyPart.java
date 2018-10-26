package org.loadtest4j;

import java.nio.file.Path;

/**
 * An Algebraic Data Type (ADT) representing an HTTP request body part.
 */
public abstract class BodyPart {

    /**
     * An ADT is closed; the set of cases is restricted. Use one of the static factory functions below to construct it.
     */
    private BodyPart() {

    }

    /**
     * Create a body part from a simple string.
     *
     * @param name The name (identifier) for the body part
     * @param content The string content of the body part
     * @return A string body part
     */
    public static BodyPart string(String name, String content) {
        return new StringPart(name, content);
    }

    /**
     * Create a file body part.
     *
     * @param content The path to the file that will be a body part
     * @return A file body part
     */
    public static BodyPart file(Path content) {
        return new FilePart(content);
    }

    /**
     * Performs a pattern match on the ADT and returns some output.
     *
     * @param matcher The pattern matcher which defines how to behave in each case
     * @param <R> The type of output to return in all cases
     * @return The output of the pattern match
     */
    public abstract <R> R match(Matcher<R> matcher);

    public interface Matcher<R> {
        R stringPart(String name, String content);

        R filePart(Path content);
    }

    private static final class FilePart extends BodyPart {
        private final Path content;

        private FilePart(Path content) {
            this.content = content;
        }

        @Override
        public <R> R match(Matcher<R> matcher) {
            return matcher.filePart(content);
        }
    }

    private static final class StringPart extends BodyPart {
        private final String name;
        private final String content;

        private StringPart(String name, String content) {
            this.name = name;
            this.content = content;
        }

        @Override
        public <R> R match(Matcher<R> matcher) {
            return matcher.stringPart(name, content);
        }
    }
}
