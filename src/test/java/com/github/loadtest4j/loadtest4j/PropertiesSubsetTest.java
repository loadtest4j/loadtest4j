package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class PropertiesSubsetTest {
    @Test
    public void testGetSubsetAndStripPrefix() {
        final Properties props = new Properties();
        props.put("foo", "1");
        props.put("foo.bar", "2");
        props.put("foo.bar.baz", "3");

        assertEquals(Collections.singletonMap("baz", "3"), LoadTesterFactory.PropertiesSubset.getSubsetAndStripPrefix(props, "foo.bar"));
    }
}
