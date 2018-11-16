package org.loadtest4j;

import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.test_utils.MockBodyMatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Category(UnitTest.class)
public class RequestTest {

    @Test
    public void shouldHaveBody() {
        final Request sut = Request.post("/pets");

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly("");
    }

    @Test
    public void shouldHaveHeaders() {
        final Request sut = Request.post("/pets");

        assertThat(sut.getHeaders()).isEmpty();
    }

    @Test
    public void shouldHaveMethod() {
        assertSoftly(s -> {
            s.assertThat(Request.get("/").getMethod()).as("GET").isEqualTo("GET");
            s.assertThat(Request.post("/").getMethod()).as("POST").isEqualTo("POST");
            s.assertThat(Request.put("/").getMethod()).as("PUT").isEqualTo("PUT");
            s.assertThat(Request.delete("/").getMethod()).as("DELETE").isEqualTo("DELETE");
            s.assertThat(Request.options("/").getMethod()).as("OPTIONS").isEqualTo("OPTIONS");
            s.assertThat(Request.head("/").getMethod()).as("HEAD").isEqualTo("HEAD");
            s.assertThat(Request.trace("/").getMethod()).as("TRACE").isEqualTo("TRACE");
            s.assertThat(Request.patch("/").getMethod()).as("PATCH").isEqualTo("PATCH");
            s.assertThat(Request.link("/").getMethod()).as("LINK").isEqualTo("LINK");
            s.assertThat(Request.unlink("/").getMethod()).as("UNLINK").isEqualTo("UNLINK");
        });
    }

    @Test
    public void shouldHavePath() {
        final Request sut = Request.get("/pets");

        assertThat(sut.getPath()).isEqualTo("/pets");
    }

    @Test
    public void shouldHaveQueryParams() {
        final Request sut = Request.get("/pets");

        assertThat(sut.getQueryParams()).isEmpty();
    }

    @Test
    public void shouldBeImmutable() {
        final Request sut = Request.get("/pets");

        sut.withBody("foo");
        sut.withQueryParam("foo", "bar");
        sut.withHeader("Foo", "bar");
        sut.withHeaders(Collections.singletonMap("Foo", "bar"));

        assertThat(sut).isEqualToComparingFieldByFieldRecursively(Request.get("/pets"));
    }

    @Test
    public void shouldBuildWithStringBody() {
        final Request sut = Request.post("/pets").withBody("{}");

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly("{}");
    }

    @Test
    public void shouldBuildWithMultipartBody() {
        final BodyPart part = BodyPart.string("foo", "bar");

        final Request sut = Request.post("/pets").withBody(part);

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly(part);
    }

    @Test
    public void shouldBuildWithHeader() {
        final Request sut = Request.post("/pets")
                .withHeader("Content-Type", "application/json");

        assertThat(sut.getHeaders()).containsEntry("Content-Type", "application/json");
    }

    @Test
    public void shouldBuildWithHeaders() {
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
    public void shouldBuildWithQueryParam() {
        final Request sut = Request.get("/pets").withQueryParam("foo", "1");

        assertThat(sut.getQueryParams()).containsEntry("foo", "1");
    }

    @Test
    public void shouldNotCombineBodies() {
        final Request sut = Request.post("/pets")
                .withBody("foo")
                .withBody("bar");

        assertThat(sut.getBody().match(new MockBodyMatcher())).containsExactly("bar");
    }

    @Test
    public void shouldCombineHeaderAndHeader() {
        final Request sut = Request.get("/pets")
                .withHeader("Accept", "application/json")
                .withHeader("Referer", "https://example.com");

        assertThat(sut.getHeaders())
                .containsEntry("Accept", "application/json")
                .containsEntry("Referer", "https://example.com");
    }

    @Test
    public void shouldCombineHeaderAndHeaders() {
        final Request sut = Request.get("/pets")
                .withHeader("Referer", "https://example.com")
                .withHeaders(Collections.singletonMap("Accept", "application/json"));

        assertThat(sut.getHeaders())
                .containsEntry("Accept", "application/json")
                .containsEntry("Referer", "https://example.com");
    }

    @Test
    public void shouldCombineQueryParams() {
        final Request sut = Request.get("/pets")
                .withQueryParam("foo", "1")
                .withQueryParam("bar", "2");

        assertThat(sut.getQueryParams())
                .containsEntry("foo", "1")
                .containsEntry("bar", "2");
    }
}
