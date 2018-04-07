package com.github.themasterchef.loadtest4j.util;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class PropertiesSubsetTest {
    private static final Properties PROPS = new Properties() {{
        put("foo", "1");
        put("foo.bar", "2");
        put("foo.bar.baz", "3");
    }};

    @Test
    public void testGetSubset() {
        final Map<String, String> actualSubset = PropertiesSubset.getSubset(PROPS, "foo.bar");

        final Map<String, String> expectedSubset = new HashMap<String, String>() {{
            put("foo.bar", "2");
            put("foo.bar.baz", "3");
        }};
        assertEquals(expectedSubset, actualSubset);
    }

    @Test
    public void testGetSubsetAndStripPrefix() {
        assertEquals(Collections.singletonMap("baz", "3"), PropertiesSubset.getSubsetAndStripPrefix(PROPS, "foo.bar"));
    }
}
