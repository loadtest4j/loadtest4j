package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.drivers.Driver;
import com.github.themasterchef.loadtest4j.drivers.DriverResult;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class StandardLoadTesterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRun() throws Exception {
        // Given
        final StubDriver driver = new StubDriver();
        final LoadTester loadTester = new StandardLoadTester(driver);

        // When
        final Result result = loadTester.run(Requests.get("/")).get();

        // Then
        assertEquals(0, result.getErrors());
        assertEquals(0, result.getRequests());
    }

    @Test
    public void testRunWithNoRequests() throws Exception {
        // Given
        final LoadTester loadTester = new StandardLoadTester(new StubDriver());

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No requests were specified for the load test.");

        // When
        loadTester.run().get();
    }

    private static class StubDriver implements Driver {
        @Override
        public CompletableFuture<DriverResult> run(Request... requests) {
            final DriverResult nopResult = new StubDriverResult();
            return completedFuture(nopResult);
        }
    }

    private static class StubDriverResult implements DriverResult {
        public long getErrors() {
            return 0;
        }

        public long getRequests() {
            return 0;
        }
    }

}
