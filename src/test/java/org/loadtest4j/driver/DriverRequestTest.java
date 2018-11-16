package org.loadtest4j.driver;

import org.loadtest4j.Body;
import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.test_utils.MockBodyMatcher;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class DriverRequestTest {

    private final DriverRequest request = new DriverRequest(Body.string("{}"), Collections.singletonMap("Accept", "application/json"), "GET", "/", Collections.singletonMap("foo", "bar"));

    @Test
    public void shouldHaveBody() {
        assertThat(request.getBody().match(new MockBodyMatcher())).containsExactly("{}");
    }

    @Test
    public void shouldHaveHeaders() {
        assertThat(request.getHeaders()).containsEntry("Accept", "application/json");
    }

    @Test
    public void shouldHaveMethod() {
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    public void shouldHavePath() {
        assertThat(request.getPath()).isEqualTo("/");
    }

    @Test
    public void shouldHaveQueryParams() {
        assertThat(request.getQueryParams()).containsEntry("foo", "bar");
    }
}
