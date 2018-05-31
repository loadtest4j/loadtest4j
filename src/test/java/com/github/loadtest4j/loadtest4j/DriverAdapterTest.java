package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver.NopDriver;
import com.github.loadtest4j.loadtest4j.driver.SpyDriver;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

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
        final Result result = loadTester.run(Request.get("/"));

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
        loadTester.run(Request.get("/foo"), Request.get("/bar"));

        // Then
        final List<DriverRequest> actualRequests = driver.getActualRequests();
        assertEquals("/foo", actualRequests.get(0).getPath());
        assertEquals("/bar", actualRequests.get(1).getPath());
    }
}
