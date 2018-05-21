package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ResultTest {
    @Test
    public void testGetErrorRate() {
        final Result result = new Result(0, 2);

        assertEquals(0, result.getErrorRate(), 0.001);
    }

    @Test
    public void testGetErrorRateAvoidsDivideByZero() {
        final Result result = new Result(0, 0);

        assertEquals(0, result.getErrorRate(), 0.001);
    }

    @Test
    public void testGetErrorRateWithErrors() {
        final Result result = new Result(1, 2);

        assertEquals(0.5, result.getErrorRate(), 0.001);
    }

    @Test
    public void testGetErrorRateWithHugeNumbers() {
        final long requests = Long.MAX_VALUE;
        final long errors = requests / 2;

        final Result result = new Result(errors, requests);

        assertEquals(0.5, result.getErrorRate(), 0.001);
    }
}
