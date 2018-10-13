package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ResponseTimeTest {
    @Test
    public void testGetPercentile() {
        final ResponseTime responseTime = new StubResponseTime();
        
        assertThat(responseTime.getPercentile(90.5)).isEqualTo(Duration.ZERO);
    }
    
    @Test
    public void testGetMax() {
        final ResponseTime responseTime = new StubResponseTime();
        
        assertThat(responseTime.getMax()).isEqualTo(Duration.ZERO);
    }

    @Test
    public void testGetMedian() {
        final ResponseTime responseTime = new StubResponseTime();

        assertThat(responseTime.getMedian()).isEqualTo(Duration.ZERO);
    }

    private static class StubResponseTime extends ResponseTime {

        @Override
        public Duration getPercentile(double percentile) {
            return Duration.ZERO;
        }
    }
}
