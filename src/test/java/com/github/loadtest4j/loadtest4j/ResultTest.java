package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ResultTest {

    @Test
    public void testGetTotal() {
        final Result result = new Result(1, 1);

        assertEquals(2, result.getTotal());
    }

    @Test
    public void testGetErrorRate() {
        final Result result = new Result(0, 2);

        assertEquals(1.0, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetErrorRateAvoidsDivideByZero() {
        final Result result = new Result(0, 0);

        assertEquals(0, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetErrorRateWithErrors() {
        final Result result = new Result(2, 2);

        assertEquals(0.5, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetErrorRateWithHugeNumbers() {
        final long ok = Long.MAX_VALUE / 2;
        final long ko = Long.MAX_VALUE / 2;

        final Result result = new Result(ok, ko);

        assertEquals(0.5, result.getPercentKo(), 0.001);
    }
}
