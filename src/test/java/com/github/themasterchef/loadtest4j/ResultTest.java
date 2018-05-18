package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ResultTest {
    @Test
    public void testGetErrorRate() {
        final Result result = new StubResult(0, 2);

        assertEquals(0, result.getErrorRate(), 0.001);
    }

    @Test
    public void testGetErrorRateAvoidsDivideByZero() {
        final Result result = new StubResult(0, 0);

        assertEquals(0, result.getErrorRate(), 0.001);
    }

    @Test
    public void testGetErrorRateWithErrors() {
        final Result result = new StubResult(1, 2);

        assertEquals(0.5, result.getErrorRate(), 0.001);
    }

    private static class StubResult implements Result {
        private final long errors;

        private final long requests;

        private StubResult(long errors, long requests) {
            this.errors = errors;
            this.requests = requests;
        }

        @Override
        public long getErrors() {
            return errors;
        }

        @Override
        public long getRequests() {
            return requests;
        }
    }

}
