package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.MockBodyVisitor;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class BodyTest {
    @Test
    public void testStringBody() {
        final Body body = Body.string("foo bar");

        assertThat(body.accept(new MockBodyVisitor()))
                .containsExactly("foo bar");
    }

    @Test
    public void testBodyParts() {
        final BodyPart foo = BodyPart.string("foo");
        final BodyPart bar = BodyPart.string("bar");
        final Body body = Body.parts(foo, bar);

        assertThat(body.accept(new MockBodyVisitor()))
                .containsExactly(foo, bar);
    }

    @Test
    public void testNoBodyParts() {
        final Body body = Body.parts();

        assertThat(body.accept(new MockBodyVisitor()))
                .isEmpty();
    }
}
