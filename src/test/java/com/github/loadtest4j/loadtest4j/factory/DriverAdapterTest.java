package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTester;
import com.github.loadtest4j.loadtest4j.Request;
import com.github.loadtest4j.loadtest4j.Result;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriver;
import com.github.loadtest4j.loadtest4j.test_utils.SpyDriver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(result.getKo()).isEqualTo(0);
        assertThat(result.getOk()).isEqualTo(0);
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
