package org.loadtest4j;

import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.NopResponseTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ResponseTimeTest {
    @Test
    public void testGetPercentile() {
        final ResponseTime responseTime = new ResponseTime(new NopResponseTime());
        
        assertThat(responseTime.getPercentile(90)).isEqualTo(Duration.ZERO);
    }
    
    @Test
    public void testGetMax() {
        final ResponseTime responseTime = new ResponseTime(new NopResponseTime());
        
        assertThat(responseTime.getMax()).isEqualTo(Duration.ZERO);
    }
    
    @Test
    public void testGetMedian() {
        final ResponseTime responseTime = new ResponseTime(new NopResponseTime());
        
        assertThat(responseTime.getMedian()).isEqualTo(Duration.ZERO);
    }
}
