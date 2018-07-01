package com.github.loadtest4j.loadtest4j.utils;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class PropertyTest {
    @Test
    public void testGetKey() {
        final Property property = new Property("foo", "bar");

        assertThat(property.getKey()).isEqualTo("foo");
    }

    @Test
    public void testGetValue() {
        final Property property = new Property("foo", "bar");

        assertThat(property.getValue()).isEqualTo("bar");
    }
}
