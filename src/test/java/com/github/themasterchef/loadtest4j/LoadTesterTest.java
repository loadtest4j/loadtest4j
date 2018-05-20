package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class LoadTesterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRun() {
        // Given
        final StubDriver driver = new StubDriver();
        final LoadTester loadTester = new LoadTester(driver);

        // When
        final Result result = loadTester.run(Request.get("/"));

        // Then
        assertEquals(0, result.getErrors());
        assertEquals(0, result.getRequests());
    }

    @Test
    public void testRunWithNoRequests() {
        // Given
        final LoadTester loadTester = new LoadTester(new StubDriver());

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No requests were specified for the load test.");

        // When
        loadTester.run();
    }

    private static class StubDriver implements Driver {
        @Override
        public DriverResult run(Collection<DriverRequest> requests) {
            return new DriverResult(0, 0);
        }
    }
}
