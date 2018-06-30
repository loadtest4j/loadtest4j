package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.assertj.core.api.Assertions.*;

@Category(UnitTest.class)
public class LoadTesterExceptionTest {
    @Test
    public void testExceptionWithString() {
        final LoadTesterException e = new LoadTesterException("foo");

        assertThat(e).hasMessage("foo");
    }

    @Test
    public void testExceptionWithWrappedException() {
        final Exception foo = new Exception();
        final LoadTesterException e = new LoadTesterException(foo);

        assertThat(e).hasCause(foo);
    }
}
