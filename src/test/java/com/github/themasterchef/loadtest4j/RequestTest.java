package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Category(UnitTest.class)
public class RequestTest {
    @Test
    public void testCreateDefaults() {
        final Request sut = Request.withUrl("http://example.com");

        assertEquals(new Request("", emptyMap(), "GET", "http://example.com"), sut);
    }

    @Test
    public void testCreate() {
        final Request sut = Request.withUrl("http://example.com")
                .withBody("{}")
                .withHeader("Content-Type", "application/json")
                .withMethod("POST");

        assertEquals(new Request("{}", singletonMap("Content-Type", "application/json"), "POST", "http://example.com"), sut);
    }

    @Test
    public void testCreateWithMultipleHeaders() {
        final Request sut = Request.withUrl("http://example.com")
                .withHeader("Accept", "application/json")
                .withHeader("Referer", "http://example.com");

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("http://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testCreateWithHeaderMap() {
        final Map<String, String> headers = new HashMap<String, String>() {{
            put("Accept", "application/json");
            put("Referer", "http://example.com");
        }};

        final Request sut = Request.withUrl("http://example.com")
                .withHeaders(headers);

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("http://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testCreateCombinesHeaders() {
        final Request sut = Request.withUrl("http://example.com")
                .withHeader("Referer", "http://example.com")
                .withHeaders(singletonMap("Accept", "application/json"));

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("http://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testWithHeaderBehavesImmutably() {
        final Request sut = Request.withUrl("http://example.com");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeader("Foo", "bar");

        assertFalse(headersBefore.containsKey("Foo"));
    }

    @Test
    public void testWithHeadersBehavesImmutably() {
        final Request sut = Request.withUrl("http://example.com");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeaders(Collections.singletonMap("Foo", "bar"));

        assertFalse(headersBefore.containsKey("Foo"));
    }
}
