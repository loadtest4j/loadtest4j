package org.loadtest4j;

import java.nio.file.Path;

public abstract class Body {

    private Body() {}

    public abstract <R> R accept(Visitor<R> visitor);

    public static Body string(String str) {
        return new Body.StringBody(str);
    }

    public static Body file(Path file) {
        return new Body.FileBody(file);
    }

    public interface Visitor<R> {
        R string(String body);

        R file(Path path);
    }

    private static class FileBody extends Body {
        private final Path file;

        private FileBody(Path file) {
            this.file = file;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.file(file);
        }
    }

    private static class StringBody extends Body {
        private final String body;

        private StringBody(String body) {
            this.body = body;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.string(body);
        }
    }
}