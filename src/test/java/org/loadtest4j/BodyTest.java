package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class BodyTest {
    @Test
    public void testStringBody() {
        final Body body = Body.string("foo bar");

        assertThat(body.accept(new MockBodyVisitor()))
                .isEqualTo("foo bar");
    }

    @Test
    public void testStringBodyEquality() {
        final Body a = Body.string("foo bar");
        final Body b = Body.string("foo bar");
        assertThat(a)
                .isEqualTo(b)
                .hasSameHashCodeAs(b);
    }

    @Test
    public void testFileBody() {
        final Body body = Body.file(Paths.get("/tmp/foo.txt"));

        assertThat(body.accept(new MockBodyVisitor()))
                .isEqualTo("/tmp/foo.txt");
    }

    @Test
    public void testFileBodyEquality() {
        final Body a = Body.file(Paths.get("/tmp/foo.txt"));
        final Body b = Body.file(Paths.get("/tmp/foo.txt"));

        assertThat(a)
                .isEqualTo(b)
                .hasSameHashCodeAs(b);
    }

    private static class MockBodyVisitor implements Body.Visitor<String> {

        @Override
        public String string(String body) {
            return body;
        }

        @Override
        public String file(Path path) {
            return path.toString();
        }
    }
}
