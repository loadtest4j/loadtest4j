package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopResponseTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ResultTest {

    @Test
    public void testGetTotal() {
        final Result result = new Result(1, 1, Duration.ZERO, createResponseTime());

        assertThat(result.getTotal()).isEqualTo(2);
    }

    @Test
    public void testGetPercentKo() {
        final Result result = new Result(0, 2, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentKo()).isEqualTo(100);
    }

    @Test
    public void testGetPercentKoAvoidsDivideByZero() {
        final Result result = new Result(0, 0, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentKo()).isEqualTo(0);
    }

    @Test
    public void testGetPercentKoWithErrors() {
        final Result result = new Result(3, 1, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentKo()).isEqualTo(25);
    }

    @Test
    public void testGetPercentKoWithHugeNumbers() {
        final long ok = Long.MAX_VALUE / 2;
        final long ko = Long.MAX_VALUE / 2;

        final Result result = new Result(ok, ko, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentKo()).isEqualTo(50);
    }

    @Test
    public void testGetActualDuration() {
        final Result result = new Result(1, 1, Duration.ofSeconds(2), createResponseTime());

        assertThat(result.getActualDuration()).isEqualTo(Duration.ofSeconds(2));
    }

    @Test
    public void testGetRequestsPerSecond() {
        final Result result = new Result(5, 5, Duration.ofMillis(2500), createResponseTime());

        assertThat(result.getRequestsPerSecond()).isEqualTo(4);
    }

    @Test
    public void testGetRequestsPerSecondAvoidsDivideByZero() {
        final Result result = new Result(2, 0, Duration.ZERO, createResponseTime());

        assertThat(result.getRequestsPerSecond()).isEqualTo(0);
    }

    @Test
    public void testGetResponseTime() {
        final Result result = new Result(2, 0, Duration.ZERO, createResponseTime());

        assertThat(result.getResponseTime().getPercentile(50)).isEqualTo(Duration.ZERO);
    }
    
    private static ResponseTime createResponseTime() {
        return new ResponseTime(new NopResponseTime());
    }
}
