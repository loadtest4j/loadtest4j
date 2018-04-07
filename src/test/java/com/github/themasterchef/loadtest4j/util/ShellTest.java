package com.github.themasterchef.loadtest4j.util;

import com.github.themasterchef.loadtest4j.junit.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(IntegrationTest.class)
public class ShellTest {
    @Test
    public void testRun() throws Exception {
        final Shell sut = new Shell();

        final int exitStatus = sut.start(new Shell.Command("whoami")).run().get();

        assertEquals(0, exitStatus);
    }
}
