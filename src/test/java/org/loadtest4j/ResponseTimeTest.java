package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.TestResponseTime;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ResponseTimeTest {
    @Test
    public void shouldHavePercentileDistribution() {
        final ResponseTime responseTime = new ResponseTime(TestResponseTime.ZERO);
        
        assertThat(responseTime.getPercentile(90.5)).isEqualTo(Duration.ZERO);
    }
    
    @Test
    public void shouldHaveMax() {
        final ResponseTime responseTime = new ResponseTime(TestResponseTime.ZERO);
        
        assertThat(responseTime.getMax()).isEqualTo(Duration.ZERO);
    }

    @Test
    public void shouldHaveMedian() {
        final ResponseTime responseTime = new ResponseTime(TestResponseTime.ZERO);

        assertThat(responseTime.getMedian()).isEqualTo(Duration.ZERO);
    }
}
