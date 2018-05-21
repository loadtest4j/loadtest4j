package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class DriverRequestTest {

    private DriverRequest request;

    @Before
    public void setup() {
        request = new DriverRequest("{}", Collections.singletonMap("Accept", "application/json"), "GET", "/");
    }

    @Test
    public void testGetBody() {
        assertEquals("{}", request.getBody());
    }

    @Test
    public void testGetHeaders() {
        assertEquals(Collections.singletonMap("Accept", "application/json"), request.getHeaders());
    }

    @Test
    public void testGetMethod() {
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testGetPath() {
        assertEquals("/", request.getPath());
    }
}
