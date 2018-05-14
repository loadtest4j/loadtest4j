package com.github.themasterchef.loadtest4j.util;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class PropertiesSubsetTest {
    private Properties props;

    @Before
    public void setupProperties() {
        props = new Properties();
        props.put("foo", "1");
        props.put("foo.bar", "2");
        props.put("foo.bar.baz", "3");
    }

    @Test
    public void testGetSubset() {
        final Map<String, String> actualSubset = PropertiesSubset.getSubset(props, "foo.bar");

        final Map<String, String> expectedSubset = new HashMap<>();
        expectedSubset.put("foo.bar", "2");
        expectedSubset.put("foo.bar.baz", "3");

        assertEquals(expectedSubset, actualSubset);
    }

    @Test
    public void testGetSubsetAndStripPrefix() {
        assertEquals(Collections.singletonMap("baz", "3"), PropertiesSubset.getSubsetAndStripPrefix(props, "foo.bar"));
    }
}
