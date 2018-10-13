package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.fail;

@Category(UnitTest.class)
public class ApdexTest {

    @Test
    public void testCalculateWithInvalidPrecondition() {
        final Apdex apdex = new Apdex((min, max) -> 0, 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> apdex.calculate(Duration.ofSeconds(2), Duration.ofSeconds(1)))
                .withMessage("toleratedThreshold must be greater than satisfiedThreshold.");
    }

    @Test
    public void testCalculate() {
        fail("Implement me");
    }

    @Test
    public void testUnaryCalculate() {
        fail("Implement me");
    }

    @Test
    public void testApdex() {
        // Example: if there are 100 samples with a target time of 3 seconds,
        // where 60 are below 3 seconds,
        // 30 are between 3 and 12 seconds,
        // and the remaining 10 are above 12 seconds,
        // the Apdex score is 0.75.
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
}
