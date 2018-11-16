package org.loadtest4j;

import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class LoadTesterExceptionTest {
    @Test
    public void shouldWrapMessages() {
        final LoadTesterException e = new LoadTesterException("foo");

        assertThat(e).hasMessage("foo");
    }

    @Test
    public void shouldWrapCauses() {
        final Exception foo = new Exception();
        final LoadTesterException e = new LoadTesterException(foo);

        assertThat(e).hasCause(foo);
    }
}
