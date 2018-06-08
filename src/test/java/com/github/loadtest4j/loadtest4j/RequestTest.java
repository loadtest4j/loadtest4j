package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Category(UnitTest.class)
public class RequestTest {
    @Test
    public void testDefaultHeaders() {
        final Request sut = Request.get("/pets");

        assertEquals(Collections.emptyMap(), sut.getHeaders());
    }

    @Test
    public void testDefaultBody() {
        final Request sut = Request.get("/pets");

        assertEquals("", sut.getBody());
    }

    @Test
    public void testGetPath() {
        final Request sut = Request.get("/pets");

        assertEquals("/pets", sut.getPath());
    }

    @Test
    public void testGet() {
        assertEquals("GET", Request.get("/").getMethod());
    }

    @Test
    public void testPost() {
        assertEquals("POST", Request.post("/").getMethod());
    }

    @Test
    public void testPut() {
        assertEquals("PUT", Request.put("/").getMethod());
    }

    @Test
    public void testDelete() {
        assertEquals("DELETE", Request.delete("/").getMethod());
    }

    @Test
    public void testOptions() {
        assertEquals("OPTIONS", Request.options("/").getMethod());
    }

    @Test
    public void testHead() {
        assertEquals("HEAD", Request.head("/").getMethod());
    }

    @Test
    public void testTrace() {
        assertEquals("TRACE", Request.trace("/").getMethod());
    }

    @Test
    public void testPatch() {
        assertEquals("PATCH", Request.patch("/").getMethod());
    }

    @Test
    public void testLink() {
        assertEquals("LINK", Request.link("/").getMethod());
    }

    @Test
    public void testUnlink() {
        assertEquals("UNLINK", Request.unlink("/").getMethod());
    }

    @Test
    public void testWithBody() {
        final Request sut = Request.post("/pets").withBody("{}");

        assertEquals("{}", sut.getBody());
    }

    @Test
    public void testWithHeader() {
        final Request sut = Request.post("/pets").withHeader("Content-Type", "application/json");

        assertEquals(Collections.singletonMap("Content-Type", "application/json"), sut.getHeaders());
    }

    @Test
    public void testWithHeaderMultipleInvocations() {
        final Request sut = Request.get("/pets")
                .withHeader("Accept", "application/json")
                .withHeader("Referer", "https://example.com");

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("https://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testWithHeaderBehavesImmutably() {
        final Request sut = Request.get("/pets");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeader("Foo", "bar");

        assertFalse(headersBefore.containsKey("Foo"));
    }

    @Test
    public void testWithHeaders() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Referer", "https://example.com");

        final Request sut = Request.get("/pets")
                .withHeaders(headers);

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("https://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testWithHeadersBehavesImmutably() {
        final Request sut = Request.get("/pets");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeaders(Collections.singletonMap("Foo", "bar"));

        assertFalse(headersBefore.containsKey("Foo"));
    }

    @Test
    public void testCombinesHeaders() {
        final Request sut = Request.get("/pets")
                .withHeader("Referer", "https://example.com")
                .withHeaders(Collections.singletonMap("Accept", "application/json"));

        assertEquals("application/json", sut.getHeaders().get("Accept"));
        assertEquals("https://example.com", sut.getHeaders().get("Referer"));
    }

    @Test
    public void testWithQueryParam() {
        final Request sut = Request.get("/pets").withQueryParam("foo", "1");

        assertEquals(Collections.singletonMap("foo", "1"), sut.getQueryParams());
    }

    @Test
    public void testWithQueryParamMultipleInvocations() {
        final Request sut = Request.get("/pets")
                .withQueryParam("foo", "1")
                .withQueryParam("bar", "2");

        assertEquals("1", sut.getQueryParams().get("foo"));
        assertEquals("2", sut.getQueryParams().get("bar"));
    }

    @Test
    public void testWithQueryParamBehavesImmutably() {
        final Request sut = Request.get("/pets");

        final Map<String, String> queryParamsBefore = sut.getQueryParams();

        sut.withQueryParam("foo", "bar");

        assertFalse(queryParamsBefore.containsKey("foo"));
    }
}
