package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Category(UnitTest.class)
public class RequestsTest {
    @Test
    public void testDefaultHeaders() {
        final Request sut = Requests.get("/pets");

        assertEquals(Collections.emptyMap(), sut.getHeaders());
    }

    @Test
    public void testDefaultBody() {
        final Request sut = Requests.get("/pets");

        assertEquals("", sut.getBody());
    }

    @Test
    public void testGetPath() {
        final Request sut = Requests.get("/pets");

        assertEquals("/pets", sut.getPath());
    }

    @Test
    public void testGet() {
        assertEquals("GET", Requests.get("/").getMethod());
    }

    @Test
    public void testPost() {
        assertEquals("POST", Requests.post("/").getMethod());
    }

    @Test
    public void testPut() {
        assertEquals("PUT", Requests.put("/").getMethod());
    }

    @Test
    public void testDelete() {
        assertEquals("DELETE", Requests.delete("/").getMethod());
    }

    @Test
    public void testOptions() {
        assertEquals("OPTIONS", Requests.options("/").getMethod());
    }

    @Test
    public void testHead() {
        assertEquals("HEAD", Requests.head("/").getMethod());
    }

    @Test
    public void testTrace() {
        assertEquals("TRACE", Requests.trace("/").getMethod());
    }

    @Test
    public void testPatch() {
        assertEquals("PATCH", Requests.patch("/").getMethod());
    }

    @Test
    public void testLink() {
        assertEquals("LINK", Requests.link("/").getMethod());
    }

    @Test
    public void testUnlink() {
        assertEquals("UNLINK", Requests.unlink("/").getMethod());
    }

    @Test
    public void testWithBody() {
        final Request sut = Requests.post("/pets").withBody("{}");

        assertEquals("{}", sut.getBody());
    }

    @Test
    public void testWithHeader() {
        final Request sut = Requests.post("/pets").withHeader("Content-Type", "application/json");

        assertEquals(Collections.singletonMap("Content-Type", "application/json"), sut.getHeaders());
    }

    @Test
    public void testWithHeaderMultipleInvocations() {
        final Request sut = Requests.get("/pets")
                .withHeader("Accept", "application/json")
                .withHeader("Referer", "https://example.com");

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("https://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testWithHeaders() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Referer", "https://example.com");

        final Request sut = Requests.get("/pets")
                .withHeaders(headers);

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("https://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testCombinesHeaders() {
        final Request sut = Requests.get("/pets")
                .withHeader("Referer", "https://example.com")
                .withHeaders(Collections.singletonMap("Accept", "application/json"));

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("https://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testWithHeaderBehavesImmutably() {
        final Requests sut = Requests.get("/pets");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeader("Foo", "bar");

        assertFalse(headersBefore.containsKey("Foo"));
    }

    @Test
    public void testWithHeadersBehavesImmutably() {
        final Requests sut = Requests.get("/pets");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeaders(Collections.singletonMap("Foo", "bar"));

        assertFalse(headersBefore.containsKey("Foo"));
    }
}
