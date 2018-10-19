package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.MockBodyPartVisitor;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class BodyPartTest {
    @Test
    public void testStringPart() {
        final BodyPart bodyPart = BodyPart.string("foo");

        assertThat(bodyPart.accept(new MockBodyPartVisitor()))
                .isEqualTo("foo");
    }

    @Test
    public void testFilePart() {
        final BodyPart bodyPart = BodyPart.file(Paths.get("/tmp/foo.txt"));

        assertThat(bodyPart.accept(new MockBodyPartVisitor()))
                .isEqualTo("/tmp/foo.txt");
    }
}
