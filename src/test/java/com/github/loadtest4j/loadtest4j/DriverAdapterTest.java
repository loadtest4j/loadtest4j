package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriver;
import com.github.loadtest4j.loadtest4j.test_utils.SpyDriver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class DriverAdapterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRun() {
        // Given
        final NopDriver driver = new NopDriver();
        final LoadTester loadTester = new DriverAdapter(driver);

        // When
        final Result result = loadTester.run(list(Request.get("/")));

        // Then
        assertEquals(0, result.getKo());
        assertEquals(0, result.getOk());
    }

    @Test
    public void testRunPreservesRequestOrdering() {
        // Given
        final SpyDriver driver = new SpyDriver(new NopDriver());
        final LoadTester loadTester = new DriverAdapter(driver);

        // When
        loadTester.run(list(Request.get("/foo"), Request.get("/bar")));

        // Then
        final List<DriverRequest> actualRequests = driver.getActualRequests();
        assertEquals("/foo", actualRequests.get(0).getPath());
        assertEquals("/bar", actualRequests.get(1).getPath());
    }

    private static List<Request> list(Request... elements) {
        return Arrays.asList(elements);
    }
}
