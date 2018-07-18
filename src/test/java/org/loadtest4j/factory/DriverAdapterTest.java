package org.loadtest4j.factory;

import org.loadtest4j.LoadTester;
import org.loadtest4j.Request;
import org.loadtest4j.driver.DriverRequest;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.NopDriver;
import org.loadtest4j.test_utils.SpyDriver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    private static List<Request> list(Request... elements) {
        return Arrays.asList(elements);
    }
}
