package org.loadtest4j.factory;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.ResponseTime;
import org.loadtest4j.junit.UnitTest;

import java.time.Duration;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ResponseTimeAdapterTest {

    private static final Function<Double, Duration> NOP_PERCENTILE_FUNCTION = (percentile) -> Duration.ZERO;

    @Test
    public void testGetPercentile() {
        final ResponseTime responseTime = new ResponseTimeAdapter(NOP_PERCENTILE_FUNCTION);
        
        assertThat(responseTime.getPercentile(90.5)).isEqualTo(Duration.ZERO);
    }
    
    @Test
    public void testGetMax() {
        final ResponseTime responseTime = new ResponseTimeAdapter(NOP_PERCENTILE_FUNCTION);
        
        assertThat(responseTime.getMax()).isEqualTo(Duration.ZERO);
    }
}
