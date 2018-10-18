package org.loadtest4j;

import java.nio.file.Path;
import java.util.Objects;

public abstract class Body {

    private Body() {}

    public abstract <R> R accept(Visitor<R> visitor);

    public static Body string(String str) {
        return new Body.StringBody(str);
    }

    public static Body file(Path file) {
        return new Body.FileBody(file);
    }

    interface Visitor<R> {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FileBody fileBody = (FileBody) o;
            return Objects.equals(file, fileBody.file);
        }

        @Override
        public int hashCode() {
            return Objects.hash(file);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StringBody that = (StringBody) o;
            return Objects.equals(body, that.body);
        }

        @Override
        public int hashCode() {
            return Objects.hash(body);
        }
    }
}