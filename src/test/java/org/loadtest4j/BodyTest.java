package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.MockBodyMatcher;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class BodyTest {
    @Test
    public void testStringBody() {
        final Body body = Body.string("foo bar");

        assertThat(body.match(new MockBodyMatcher()))
                .containsExactly("foo bar");
    }

    @Test
    public void testBodyParts() {
        final BodyPart foo = BodyPart.string("foo", "abc");
        final BodyPart bar = BodyPart.string("bar", "def");
        final Body body = Body.multipart(foo, bar);

        assertThat(body.match(new MockBodyMatcher()))
                .containsExactly(foo, bar);
    }

    @Test
    public void testNoBodyParts() {
        final Body body = Body.multipart();

        assertThat(body.match(new MockBodyMatcher()))
                .isEmpty();
    }
}
