package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.Request;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class WrkRequestTest {
    private final Request request = Request.withPath("/pets");

    @Test
    public void testBody() {
        final WrkRequest req = new WrkRequest(request.withBody("The cat's pyjamas"));

        assertEquals("The cat\\'s pyjamas", req.getBody());
    }

    @Test
    public void testMethod() {
        final WrkRequest req = new WrkRequest(request.withMethod("INVALI'D"));

        assertEquals("INVALI\\'D", req.getMethod());
    }

    @Test
    public void testPath() {
        final WrkRequest req = new WrkRequest(request);

        assertEquals("/pets", req.getPath());
    }

    @Test
    public void testHeaders() {
        final WrkRequest req = new WrkRequest(request.withHeader("fo'o", "ba'r"));

        assertEquals("{['fo\\'o'] = 'ba\\'r'}", req.getHeaders().toString());
    }

    @Test
    public void testEmptyHeaders() {
        final WrkRequest req = new WrkRequest(request);

        assertEquals("{}", req.getHeaders().toString());
    }
}
