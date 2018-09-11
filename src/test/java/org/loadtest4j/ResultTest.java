package org.loadtest4j;

import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.NopResponseTime;
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
    public void testGetPercentOk() {
        final Result result = new Result(2, 0, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentOk()).isEqualTo(100);
    }

    @Test
    public void testGetPercentOkAvoidsDivideByZero() {
        final Result result = new Result(0, 0, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentOk()).isEqualTo(0);
    }

    @Test
    public void testGetPercentOkWithErrors() {
        final Result result = new Result(1, 3, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentOk()).isEqualTo(25);
    }

    @Test
    public void testGetPercentOkWithHugeNumbers() {
        final long ok = Long.MAX_VALUE / 2;
        final long ko = Long.MAX_VALUE / 2;

        final Result result = new Result(ok, ko, Duration.ZERO, createResponseTime());

        assertThat(result.getPercentOk()).isEqualTo(50);
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
