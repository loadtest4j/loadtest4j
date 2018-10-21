package org.loadtest4j;

import java.util.Arrays;
import java.util.List;

/**
 * An Algebraic Data Type representing an HTTP request body.
 */
public abstract class Body {

    private Body() {

    }

    public abstract <R> R accept(Visitor<R> visitor);

    /**
     * Create a request body from a simple String in memory.
     *
     * @param content the string content of the body
     * @return a string body
     */
    public static Body string(String content) {
        return new Body.StringBody(content);
    }

    /**
     * Create a multipart request body from a list of body parts.
     *
     * @param parts list of body parts
     * @return a multipart request body
     */
    public static Body parts(BodyPart... parts) {
        return new Body.BodyParts(Arrays.asList(parts));
    }

    public interface Visitor<R> {
        R string(String body);

        R parts(List<BodyPart> body);
    }

    private static class BodyParts extends Body {
        private final List<BodyPart> parts;

        private BodyParts(List<BodyPart> parts) {
            this.parts = parts;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.parts(parts);
        }
    }

    private static class StringBody extends Body {
        private final String content;

        private StringBody(String content) {
            this.content = content;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.string(content);
        }
    }
}