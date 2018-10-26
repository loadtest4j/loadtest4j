package org.loadtest4j;

import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.test_utils.MockBodyMatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class RequestTest {
    @Test
    public void testDefaultHeaders() {
        final Request sut = Request.get("/pets");

        assertThat(sut.getHeaders()).isEmpty();
    }

    @Test
    public void testDefaultBody() {
        final Request sut = Request.get("/pets");

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly("");
    }

    @Test
    public void testGetPath() {
        final Request sut = Request.get("/pets");

        assertThat(sut.getPath()).isEqualTo("/pets");
    }

    @Test
    public void testGet() {
        assertThat(Request.get("/").getMethod()).isEqualTo("GET");
    }

    @Test
    public void testPost() {
        assertThat(Request.post("/").getMethod()).isEqualTo("POST");
    }

    @Test
    public void testPut() {
        assertThat(Request.put("/").getMethod()).isEqualTo("PUT");
    }

    @Test
    public void testDelete() {
        assertThat(Request.delete("/").getMethod()).isEqualTo("DELETE");
    }

    @Test
    public void testOptions() {
        assertThat(Request.options("/").getMethod()).isEqualTo("OPTIONS");
    }

    @Test
    public void testHead() {
        assertThat(Request.head("/").getMethod()).isEqualTo("HEAD");
    }

    @Test
    public void testTrace() {
        assertThat(Request.trace("/").getMethod()).isEqualTo("TRACE");
    }

    @Test
    public void testPatch() {
        assertThat(Request.patch("/").getMethod()).isEqualTo("PATCH");
    }

    @Test
    public void testLink() {
        assertThat(Request.link("/").getMethod()).isEqualTo("LINK");
    }

    @Test
    public void testUnlink() {
        assertThat(Request.unlink("/").getMethod()).isEqualTo("UNLINK");
    }

    @Test
    public void testWithBodyString() {
        final Request sut = Request.post("/pets").withBody("{}");

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly("{}");
    }

    @Test
    public void testWithBodyMultipart() {
        final BodyPart part = BodyPart.string("foo", "bar");

        final Request sut = Request.post("/pets").withBody(part);

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly(part);
    }

    @Test
    public void testDoesNotCombineBodies() {
        final Request sut = Request.post("/pets")
                .withBody("foo")
                .withBody("bar");

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly("bar");
    }

    @Test
    public void testWithHeader() {
        final Request sut = Request.post("/pets").withHeader("Content-Type", "application/json");

        assertThat(sut.getHeaders()).containsEntry("Content-Type", "application/json");
    }

    @Test
    public void testWithHeaderMultipleInvocations() {
        final Request sut = Request.get("/pets")
                .withHeader("Accept", "application/json")
                .withHeader("Referer", "https://example.com");

        assertThat(sut.getHeaders())
                .containsEntry("Accept", "application/json")
                .containsEntry("Referer", "https://example.com");
    }

    @Test
    public void testWithHeaderBehavesImmutably() {
        final Request sut = Request.get("/pets");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeader("Foo", "bar");

        assertThat(headersBefore).doesNotContainKey("Foo");
    }

    @Test
    public void testWithHeaders() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Referer", "https://example.com");

        final Request sut = Request.get("/pets")
                .withHeaders(headers);

        assertThat(sut.getHeaders())
                .containsEntry("Accept", "application/json")
                .containsEntry("Referer", "https://example.com");
    }

    @Test
    public void testWithHeadersBehavesImmutably() {
        final Request sut = Request.get("/pets");

        final Map<String, String> headersBefore = sut.getHeaders();

        sut.withHeaders(Collections.singletonMap("Foo", "bar"));

        assertThat(headersBefore).doesNotContainKey("Foo");
    }

    @Test
    public void testCombinesHeaders() {
        final Request sut = Request.get("/pets")
                .withHeader("Referer", "https://example.com")
                .withHeaders(Collections.singletonMap("Accept", "application/json"));

        assertThat(sut.getHeaders())
                .containsEntry("Accept", "application/json")
                .containsEntry("Referer", "https://example.com");
    }

    @Test
    public void testWithQueryParam() {
        final Request sut = Request.get("/pets").withQueryParam("foo", "1");

        assertThat(sut.getQueryParams()).containsEntry("foo", "1");
    }

    @Test
    public void testWithQueryParamMultipleInvocations() {
        final Request sut = Request.get("/pets")
                .withQueryParam("foo", "1")
                .withQueryParam("bar", "2");

        assertThat(sut.getQueryParams())
                .containsEntry("foo", "1")
                .containsEntry("bar", "2");
    }

    @Test
    public void testWithQueryParamBehavesImmutably() {
        final Request sut = Request.get("/pets");

        final Map<String, String> queryParamsBefore = sut.getQueryParams();

        sut.withQueryParam("foo", "bar");

        assertThat(queryParamsBefore).doesNotContainKey("foo");
    }
}
