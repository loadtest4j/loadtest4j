package org.loadtest4j.utils;

import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class PropertiesSubsetTest {
    @Test
    public void shouldGetSubsetAndStripPrefix() {
        final Map<String, String> props = new ConcurrentHashMap<>();
        props.put("foo", "1");
        props.put("foo.bar", "2");
        props.put("foo.bar.baz", "3");

        assertThat(PropertiesSubset.getSubsetAndStripPrefix(props, "foo.bar")).containsEntry("baz", "3");
    }
}
