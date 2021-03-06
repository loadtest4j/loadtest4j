package org.loadtest4j.factory;

import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.StubDriver;
import org.loadtest4j.test_utils.TestDriverResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.loadtest4j.test_utils.TestResponseTime;

import java.time.Duration;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ValidatingDriverTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldRun() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        final DriverResult expectedResult = TestDriverResult.zero();
        stubDriver.expectRun(expectedResult);

        // When
        final DriverResult actualResult = sut.run(Collections.emptyList());

        // Then
        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    public void shouldThrowExceptionWhenOkIsNegative() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        stubDriver.expectRun(new TestDriverResult(Duration.ZERO, -1, 0, new TestResponseTime(Duration.ZERO)));

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The load test driver returned a negative number of OK requests.");

        // When
        sut.run(Collections.emptyList());
    }

    @Test
    public void shouldThrowExceptionWhenKoIsNegative() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        stubDriver.expectRun(new TestDriverResult(Duration.ZERO, 0, -1, new TestResponseTime(Duration.ZERO)));

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The load test driver returned a negative number of KO requests.");

        // When
        sut.run(Collections.emptyList());
    }
}
