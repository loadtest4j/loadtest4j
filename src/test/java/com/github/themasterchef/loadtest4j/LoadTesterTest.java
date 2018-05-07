package com.github.themasterchef.loadtest4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class LoadTesterTest {
    protected abstract LoadTester sut();

    @Test
    public void testRun() throws Exception {
        final LoadTester loadTester = sut();

        final Result result = loadTester.run(Request.withPath("/pets")).get();

        assertEquals(0, result.getRequests());
        assertEquals(0, result.getErrors());
    }

    @Test
    public void testRunWithMultipleRequests() throws Exception {
        final LoadTester loadTester = sut();

        final Result result = loadTester.run(Request.withPath("/"), Request.withPath("/pets")).get();

        assertEquals(0, result.getRequests());
        assertEquals(0, result.getErrors());
    }
}
