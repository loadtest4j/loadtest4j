package org.loadtest4j.driver;

import org.loadtest4j.Body;
import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.test_utils.MockBodyVisitor;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class DriverRequestTest {

    private final DriverRequest request = new DriverRequest(Body.string("{}"), Collections.singletonMap("Accept", "application/json"), "GET", "/", Collections.singletonMap("foo", "bar"));

    @Test
    public void testGetBody() {
        assertThat(request.getBody().accept(new MockBodyVisitor())).isEqualTo("{}");
    }

    @Test
    public void testGetHeaders() {
        assertThat(request.getHeaders()).containsEntry("Accept", "application/json");
    }

    @Test
    public void testGetMethod() {
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    public void testGetPath() {
        assertThat(request.getPath()).isEqualTo("/");
    }

    @Test
    public void testGetQueryParams() {
        assertThat(request.getQueryParams()).containsEntry("foo", "bar");
    }
}
