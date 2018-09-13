package org.loadtest4j.factory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.loadtest4j.*;
import org.loadtest4j.driver.DriverRequest;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.NopDriver;
import org.loadtest4j.test_utils.SpyDriver;
import org.loadtest4j.test_utils.TestDriverResult;
import org.loadtest4j.test_utils.TestResponseTime;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Category(UnitTest.class)
public class DriverAdapterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRun() {
        // Given
        final SpyDriver driver = new SpyDriver(new NopDriver());
        final LoadTester loadTester = new DriverAdapter(driver);

        // When
        loadTester.run(list(Request.get("/")));

        // Then
        assertThat(driver.getActualRequests())
                .extracting(DriverRequest::getPath)
                .containsSequence("/");
    }

    @Test
    public void testRunPreservesRequestOrdering() {
        // Given
        final SpyDriver driver = new SpyDriver(new NopDriver());
        final LoadTester loadTester = new DriverAdapter(driver);

        // When
        loadTester.run(list(Request.get("/foo"), Request.get("/bar")));

        // Then
        assertThat(driver.getActualRequests())
                .extracting(DriverRequest::getPath)
                .containsSequence("/foo", "/bar");
    }

    @Test
    public void testGetPercentOk() {
        assertThat(DriverAdapter.getPercentOk(2, 2)).isEqualTo(100);
    }

    @Test
    public void testGetPercentOkAvoidsDivideByZero() {
        assertThat(DriverAdapter.getPercentOk(0, 0)).isEqualTo(0);
    }

    @Test
    public void testGetPercentOkWithErrors() {
        assertThat(DriverAdapter.getPercentOk(1, 4)).isEqualTo(25);
    }

    @Test
    public void testGetPercentOkWithHugeNumbers() {
        final long ok = Long.MAX_VALUE / 2;
        final long total = Long.MAX_VALUE;

        assertThat(DriverAdapter.getPercentOk(ok, total)).isEqualTo(50);
    }

    @Test
    public void testGetRequestsPerSecond() {
        assertThat(DriverAdapter.getRequestsPerSecond(10, Duration.ofMillis(2500))).isEqualTo(4);
    }

    @Test
    public void testGetRequestsPerSecondAvoidsDivideByZero() {
        assertThat(DriverAdapter.getRequestsPerSecond(2, Duration.ZERO)).isEqualTo(0);
    }

    @Test
    public void testPostprocessResult() {
        final DriverResult input = new TestDriverResult(Duration.ofMillis(2500), 5, 5, new TestResponseTime(Duration.ZERO));

        final Result result = DriverAdapter.postprocessResult(input);

        assertSoftly(s -> {
            s.assertThat(result.getPercentOk()).isEqualTo(50);

            final ResponseTime responseTime = result.getResponseTime();
            s.assertThat(responseTime.getPercentile(0)).isEqualTo(Duration.ZERO);
            s.assertThat(responseTime.getPercentile(50)).isEqualTo(Duration.ZERO);
            s.assertThat(responseTime.getPercentile(100)).isEqualTo(Duration.ZERO);

            final Diagnostics diagnostics = result.getDiagnostics();
            s.assertThat(diagnostics.getDuration()).isEqualTo(Duration.ofMillis(2500));
            s.assertThat(diagnostics.getRequestsPerSecond()).isEqualTo(4);

            final RequestCount requestCount = diagnostics.getRequestCount();
            s.assertThat(requestCount.getOk()).isEqualTo(5);
            s.assertThat(requestCount.getKo()).isEqualTo(5);
            s.assertThat(requestCount.getTotal()).isEqualTo(10);
        });

    }

    private static List<Request> list(Request... elements) {
        return Arrays.asList(elements);
    }
}
