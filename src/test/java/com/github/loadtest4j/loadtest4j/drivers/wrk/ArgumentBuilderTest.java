package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ArgumentBuilderTest {
    @Test
    public void testAddArgument() {
        final ArgumentBuilder sut = new ArgumentBuilder();

        final List<String> args = sut.addArgument("foo").build();

        assertEquals(Collections.singletonList("foo"), args);
    }

    @Test
    public void testAddNamedArgument() {
        final ArgumentBuilder sut = new ArgumentBuilder();

        final List<String> args = sut.addNamedArgument("--foo", "bar").build();

        assertEquals(Arrays.asList("--foo", "bar"), args);
    }
}
