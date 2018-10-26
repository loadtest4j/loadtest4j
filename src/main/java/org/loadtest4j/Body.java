package org.loadtest4j;

import java.util.Arrays;
import java.util.List;

/**
 * An Algebraic Data Type (ADT) representing an HTTP request body.
 */
public abstract class Body {

    /**
     * An ADT is closed; the set of cases is restricted. Use one of the static factory functions below to construct it.
     */
    private Body() {

    }

    /**
     * Performs a pattern match on the ADT and returns some output.
     *
     * @param matcher The pattern matcher which defines how to behave in each case
     * @param <R> The type of output to return in all cases
     * @return The output of the pattern match
     */
    public abstract <R> R match(Matcher<R> matcher);

    /**
     * Create a request body from a simple String in memory.
     *
     * @param content The string content of the body
     * @return A string body
     */
    public static Body string(String content) {
        return new Body.StringBody(content);
    }

    /**
     * Create a multipart request body from a list of body parts.
     *
     * @param parts The list of body parts
     * @return A multipart request body
     */
    public static Body multipart(BodyPart... parts) {
        return new MultiPartBody(Arrays.asList(parts));
    }

    public interface Matcher<R> {
        R string(String body);

        R multipart(List<BodyPart> body);
    }

    private static final class MultiPartBody extends Body {
        private final List<BodyPart> parts;

        private MultiPartBody(List<BodyPart> parts) {
            this.parts = parts;
        }

        @Override
        public <R> R match(Matcher<R> matcher) {
            return matcher.multipart(parts);
        }
    }

    private static final class StringBody extends Body {
        private final String content;

        private StringBody(String content) {
            this.content = content;
        }

        @Override
        public <R> R match(Matcher<R> matcher) {
            return matcher.string(content);
        }
    }
}