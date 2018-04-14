package com.github.themasterchef.loadtest4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class LoadTesterTest {
    protected abstract LoadTester sut();

    @Test
    public void testRun() throws Exception {
        final LoadTester loadTester = sut();

        final Result result = loadTester.run(Request.withUrl("http://example.com")).get();

        assertEquals(0, result.getRequests());
        assertEquals(0, result.getErrors());
    }
}
