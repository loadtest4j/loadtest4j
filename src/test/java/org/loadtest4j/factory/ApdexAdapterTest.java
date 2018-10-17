package org.loadtest4j.factory;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Valid tests for the apdex are based on the following example.
 *
 * If there are 100 samples with a target time of 3 seconds,
 * where 60 are below 3 seconds,
 * 30 are between 3 and 12 seconds,
 * and the remaining 10 are above 12 seconds,
 * the Apdex score is 0.75.
 */
@Category(UnitTest.class)
public class ApdexAdapterTest {

    @Test
    public void testGetScore() {
        final long totalRequests = 100;
        final BiFunction<Duration, Duration, Long> distribution = (min, max) -> {
            if (max.getSeconds() == 3) {
                return 60L;
            }
            if (min.getSeconds() == 3 && max.getSeconds() == 12) {
                return 30L;
            }
            return 0L;
        };
        final ApdexAdapter apdex = new ApdexAdapter(distribution, totalRequests);

        assertThat(apdex.getScore(Duration.ofSeconds(3)))
                .isEqualTo(0.75);
    }

    @Test
    public void testApdex() {
        assertThat(ApdexAdapter.apdex(60, 30, 100))
                .isEqualTo(new BigDecimal("0.75"));
    }

    @Test
    public void testApdexWithNegativeSatisfiedRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ApdexAdapter.apdex(-1, 0, 0))
                .withMessage("satisfiedRequests must not be negative.");
    }

    @Test
    public void testApdexWithNegativeToleratedRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ApdexAdapter.apdex(0, -1, 0))
                .withMessage("toleratedRequests must not be negative.");
    }

    @Test
    public void testApdexWithNegativeTotalRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ApdexAdapter.apdex(0, 0, -1))
                .withMessage("totalRequests must not be negative.");
    }

    @Test
    public void testApdexWithNonsensicalTotalRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ApdexAdapter.apdex(5, 5, 1))
                .withMessage("totalRequests must be greater than (satisfiedRequests + toleratedRequests).");
    }
}
