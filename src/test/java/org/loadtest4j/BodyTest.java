package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.MockBodyVisitor;

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
    public void testFileBody() {
        final Body body = Body.file(Paths.get("/tmp/foo.txt"));

        assertThat(body.accept(new MockBodyVisitor()))
                .isEqualTo("/tmp/foo.txt");
    }
}
