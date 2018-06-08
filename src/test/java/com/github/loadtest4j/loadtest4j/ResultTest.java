package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ResultTest {

    @Test
    public void testGetTotal() {
        final Result result = new Result(1, 1, Duration.ZERO);

        assertEquals(2, result.getTotal());
    }

    @Test
    public void testGetPercentKo() {
        final Result result = new Result(0, 2, Duration.ZERO);

        assertEquals(1.0, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoAvoidsDivideByZero() {
        final Result result = new Result(0, 0, Duration.ZERO);

        assertEquals(0, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoWithErrors() {
        final Result result = new Result(2, 2, Duration.ZERO);

        assertEquals(0.5, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoWithHugeNumbers() {
        final long ok = Long.MAX_VALUE / 2;
        final long ko = Long.MAX_VALUE / 2;

        final Result result = new Result(ok, ko, Duration.ZERO);

        assertEquals(0.5, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetActualDuration() {
        final Result result = new Result(1, 1, Duration.ofSeconds(2));

        assertEquals(Duration.ofSeconds(2), result.getActualDuration());
    }

    @Test
    public void testGetRequestsPerSecond() {
        final Result result = new Result(5, 5, Duration.ofMillis(2500));

        assertEquals(4, result.getRequestsPerSecond(), 0.001);
    }

    @Test
    public void testGetRequestsPerSecondAvoidsDivideByZero() {
        final Result result = new Result(2, 0, Duration.ZERO);

        assertEquals(0, result.getRequestsPerSecond(), 0.001);
    }
}
