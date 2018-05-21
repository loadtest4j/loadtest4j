package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.DriverRequests;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class WrkRequestTest {

    @Test
    public void testBody() {
        final WrkRequest req = new WrkRequest(DriverRequests.getWithBody("/pets", "The cat's pyjamas"));

        assertEquals("The cat\\'s pyjamas", req.getBody());
    }

    @Test
    public void testMethod() {
        final WrkRequest req = new WrkRequest(DriverRequests.get("/pets"));

        assertEquals("GET", req.getMethod());
    }

    @Test
    public void testPath() {
        final WrkRequest req = new WrkRequest(DriverRequests.get("/pets"));

        assertEquals("/pets", req.getPath());
    }

    @Test
    public void testHeaders() {
        final WrkRequest req = new WrkRequest(DriverRequests.getWithHeaders("/pets", Collections.singletonMap("fo'o", "ba'r")));

        assertEquals("{['fo\\'o'] = 'ba\\'r'}", req.getHeaders().toString());
    }

    @Test
    public void testEmptyHeaders() {
        final WrkRequest req = new WrkRequest(DriverRequests.get("/pets"));

        assertEquals("{}", req.getHeaders().toString());
    }
}
