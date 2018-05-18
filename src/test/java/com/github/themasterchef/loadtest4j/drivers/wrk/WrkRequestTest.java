package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.Requests;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class WrkRequestTest {

    @Test
    public void testBody() {
        final WrkRequest req = new WrkRequest(Requests.get("/pets").withBody("The cat's pyjamas"));

        assertEquals("The cat\\'s pyjamas", req.getBody());
    }

    @Test
    public void testMethod() {
        final WrkRequest req = new WrkRequest(Requests.get("/pets"));

        assertEquals("GET", req.getMethod());
    }

    @Test
    public void testPath() {
        final WrkRequest req = new WrkRequest(Requests.get("/pets"));

        assertEquals("/pets", req.getPath());
    }

    @Test
    public void testHeaders() {
        final WrkRequest req = new WrkRequest(Requests.get("/pets").withHeader("fo'o", "ba'r"));

        assertEquals("{['fo\\'o'] = 'ba\\'r'}", req.getHeaders().toString());
    }

    @Test
    public void testEmptyHeaders() {
        final WrkRequest req = new WrkRequest(Requests.get("/pets"));

        assertEquals("{}", req.getHeaders().toString());
    }
}
