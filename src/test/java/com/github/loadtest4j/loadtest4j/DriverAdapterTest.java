package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver.NopDriver;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

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
        assertEquals(0, result.getErrors());
        assertEquals(0, result.getRequests());
    }

}
