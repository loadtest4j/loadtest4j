package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.junit.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(IntegrationTest.class)
public class ShellTest {
    @Test
    public void testRun() throws Exception {
        final Shell sut = new Shell();

        final Command command = new Command(Collections.emptyList(), "whoami");
        final int exitStatus = sut.start(command).run().get();

        assertEquals(0, exitStatus);
    }
}
