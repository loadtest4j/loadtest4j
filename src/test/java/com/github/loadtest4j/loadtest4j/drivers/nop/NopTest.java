package com.github.loadtest4j.loadtest4j.drivers.nop;

import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverRequest;
import com.github.loadtest4j.loadtest4j.DriverRequests;
import com.github.loadtest4j.loadtest4j.DriverResult;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class NopTest {
    private Driver sut() {
        return new Nop();
    }

    @Test
    public void testRun()  {
        // Given
        final Driver driver = sut();

        // When
        final Collection<DriverRequest> requests = Collections.singletonList(DriverRequests.get("/"));
        final DriverResult result = driver.run(requests);

        // Then
        assertEquals(0, result.getRequests());
        assertEquals(0, result.getErrors());
    }
}
