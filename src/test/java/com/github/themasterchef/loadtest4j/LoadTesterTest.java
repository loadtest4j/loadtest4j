package com.github.themasterchef.loadtest4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class LoadTesterTest {
    protected abstract LoadTester sut();

    @Test
    public void testRun() throws Exception {
        final LoadTester loadTester = sut();

        final Result result = loadTester.run(Request.get("/")).get();

        assertTrue(result.getRequests() >= 0);
        assertEquals(0, result.getErrors());
    }

    @Test
    public void testRunWithMultipleRequests() throws Exception {
        final LoadTester loadTester = sut();

        final Result result = loadTester.run(Request.get("/"), Request.get("/pets")).get();

        assertTrue(result.getRequests() >= 0);
        assertEquals(0, result.getErrors());
    }
}
