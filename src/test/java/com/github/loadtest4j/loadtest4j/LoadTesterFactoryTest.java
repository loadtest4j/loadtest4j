package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertNotNull;

@Category(UnitTest.class)
public class LoadTesterFactoryTest {

    @Test
    public void testPublicApi() {
        // The static factory method will reference loadtest4j.properties in the test resources
        final LoadTester loadTester = LoadTesterFactory.getLoadTester();

        assertNotNull(loadTester);
    }
}
