package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopResponseTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ResultTest {

    @Test
    public void testGetTotal() {
        final Result result = new Result(1, 1, Duration.ZERO, new NopResponseTime());

        assertEquals(2, result.getTotal());
    }

    @Test
    public void testGetPercentKo() {
        final Result result = new Result(0, 2, Duration.ZERO, new NopResponseTime());

        assertEquals(100, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoAvoidsDivideByZero() {
        final Result result = new Result(0, 0, Duration.ZERO, new NopResponseTime());

        assertEquals(0, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoWithErrors() {
        final Result result = new Result(3, 1, Duration.ZERO, new NopResponseTime());

        assertEquals(25, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoWithHugeNumbers() {
        final long ok = Long.MAX_VALUE / 2;
        final long ko = Long.MAX_VALUE / 2;

        final Result result = new Result(ok, ko, Duration.ZERO, new NopResponseTime());

        assertEquals(50, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetActualDuration() {
        final Result result = new Result(1, 1, Duration.ofSeconds(2), new NopResponseTime());

        assertEquals(Duration.ofSeconds(2), result.getActualDuration());
    }

    @Test
    public void testGetRequestsPerSecond() {
        final Result result = new Result(5, 5, Duration.ofMillis(2500), new NopResponseTime());

        assertEquals(4, result.getRequestsPerSecond(), 0.001);
    }

    @Test
    public void testGetRequestsPerSecondAvoidsDivideByZero() {
        final Result result = new Result(2, 0, Duration.ZERO, new NopResponseTime());

        assertEquals(0, result.getRequestsPerSecond(), 0.001);
    }

    @Test
    public void testGetResponseTime() {
        final Result result = new Result(2, 0, Duration.ZERO, new NopResponseTime());

        assertEquals(Duration.ZERO, result.getResponseTime().getPercentile(50));
    }
}
