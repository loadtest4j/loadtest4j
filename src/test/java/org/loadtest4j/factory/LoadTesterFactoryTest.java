package org.loadtest4j.factory;

import org.loadtest4j.LoadTester;
import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class LoadTesterFactoryTest {

    @Test
    public void testPublicApi() {
        // The static factory method will reference loadtest4j.properties in the test resources
        final LoadTester loadTester = LoadTesterFactory.getLoadTester();

        assertThat(loadTester).isNotNull();
    }
}
