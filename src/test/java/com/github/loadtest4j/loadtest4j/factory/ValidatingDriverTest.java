package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverResult;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.StubDriver;
import com.github.loadtest4j.loadtest4j.test_utils.TestDriverResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ValidatingDriverTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRun() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        final DriverResult expectedResult = new TestDriverResult(0, 0);
        stubDriver.expectRun(expectedResult);

        // When
        final DriverResult actualResult = sut.run(Collections.emptyList());

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRunWithReportUrl() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        final DriverResult expectedResult = new TestDriverResult(0, 0, "https://example.com");
        stubDriver.expectRun(expectedResult);

        // When
        final DriverResult actualResult = sut.run(Collections.emptyList());

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRunWithNegativeOkValue() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        stubDriver.expectRun(new TestDriverResult(-1, 0));

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The load test driver returned a negative number of OK requests.");

        // When
        sut.run(Collections.emptyList());
    }

    @Test
    public void testRunWithNegativeKoValue() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        stubDriver.expectRun(new TestDriverResult(0, -1));

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The load test driver returned a negative number of KO requests.");

        // When
        sut.run(Collections.emptyList());
    }

    @Test
    public void testRunWithInvalidReportUrl() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        stubDriver.expectRun(new TestDriverResult(0, 0, "foo"));

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The load test driver returned an invalid report URL.");

        // When
        sut.run(Collections.emptyList());
    }
}
