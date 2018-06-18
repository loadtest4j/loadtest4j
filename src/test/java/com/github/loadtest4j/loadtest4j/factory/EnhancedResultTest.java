package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class EnhancedResultTest {
    @Test
    public void testGetTotal() {
        final EnhancedResult result = new EnhancedResult(1, 1, Duration.ZERO);

        assertEquals(2, result.getTotal());
    }

    @Test
    public void testGetPercentKo() {
        final EnhancedResult result = new EnhancedResult(0, 2, Duration.ZERO);

        assertEquals(100, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoAvoidsDivideByZero() {
        final EnhancedResult result = new EnhancedResult(0, 0, Duration.ZERO);

        assertEquals(0, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoWithErrors() {
        final EnhancedResult result = new EnhancedResult(3, 1, Duration.ZERO);

        assertEquals(25, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetPercentKoWithHugeNumbers() {
        final long ok = Long.MAX_VALUE / 2;
        final long ko = Long.MAX_VALUE / 2;

        final EnhancedResult result = new EnhancedResult(ok, ko, Duration.ZERO);

        assertEquals(50, result.getPercentKo(), 0.001);
    }

    @Test
    public void testGetActualDuration() {
        final EnhancedResult result = new EnhancedResult(1, 1, Duration.ofSeconds(2));

        assertEquals(Duration.ofSeconds(2), result.getActualDuration());
    }

    @Test
    public void testGetRequestsPerSecond() {
        final EnhancedResult result = new EnhancedResult(5, 5, Duration.ofMillis(2500));

        assertEquals(4, result.getRequestsPerSecond(), 0.001);
    }

    @Test
    public void testGetRequestsPerSecondAvoidsDivideByZero() {
        final EnhancedResult result = new EnhancedResult(2, 0, Duration.ZERO);

        assertEquals(0, result.getRequestsPerSecond(), 0.001);
    }
}
