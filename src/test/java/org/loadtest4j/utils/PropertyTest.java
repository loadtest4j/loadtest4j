package org.loadtest4j.utils;

import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class PropertyTest {
    @Test
    public void shouldHaveKey() {
        final Property property = new Property("foo", "bar");

        assertThat(property.getKey()).isEqualTo("foo");
    }

    @Test
    public void shouldHaveValue() {
        final Property property = new Property("foo", "bar");

        assertThat(property.getValue()).isEqualTo("bar");
    }
}
