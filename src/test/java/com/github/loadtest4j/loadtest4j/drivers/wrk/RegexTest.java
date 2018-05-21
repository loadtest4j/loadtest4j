package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Optional;
import java.util.regex.PatternSyntaxException;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class RegexTest {
    @Test
    public void testFirstMatch() {
        final Regex regex = Regex.compile("Foo: (\\d+)");

        final Optional<String> match = regex.firstMatch("Foo: 10");

        assertEquals(Optional.of("10"), match);
    }

    @Test
    public void testNoFirstMatch() {
        final Regex regex = Regex.compile("Foo: (\\d+)");

        final Optional<String> match = regex.firstMatch("Bar");

        assertEquals(Optional.empty(), match);
    }

    @Test(expected = PatternSyntaxException.class)
    public void testInvalidPattern() {
        Regex.compile("[");
    }
}
