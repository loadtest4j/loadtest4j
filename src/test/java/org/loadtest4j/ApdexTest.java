package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import java.math.BigDecimal;
import java.time.Duration;

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
public class ApdexTest {

    @Test
    public void testGetScore() {
        final Apdex apdex = new StubApdex();

        assertThat(apdex.getScore(Duration.ofSeconds(3)))
                .isEqualTo(0.75);
    }

    @Test
    public void testApdex() {
        assertThat(Apdex.apdex(60, 30, 100))
                .isEqualTo(new BigDecimal("0.75"));
    }

    @Test
    public void testApdexWithNegativeSatisfiedRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Apdex.apdex(-1, 0, 0))
                .withMessage("satisfiedRequests must not be negative.");
    }

    @Test
    public void testApdexWithNegativeToleratedRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Apdex.apdex(0, -1, 0))
                .withMessage("toleratedRequests must not be negative.");
    }

    @Test
    public void testApdexWithNegativeTotalRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Apdex.apdex(0, 0, -1))
                .withMessage("totalRequests must not be negative.");
    }

    @Test
    public void testApdexWithNonsensicalTotalRequests() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Apdex.apdex(5, 5, 1))
                .withMessage("totalRequests must be greater than (satisfiedRequests + toleratedRequests).");
    }

    private static class StubApdex extends Apdex {
        @Override
        protected long getTotalRequests() {
            return 100;
        }

        @Override
        public long getOkRequestsBetween(Duration min, Duration max) {
            if (max.getSeconds() == 3) {
                return 60;
            }
            if (min.getSeconds() == 3 && max.getSeconds() == 12) {
                return 30;
            }
            return 0;
        }
    }
}
