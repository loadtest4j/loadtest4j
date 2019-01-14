package org.loadtest4j.factory;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.LoadTester;
import org.loadtest4j.driver.Driver;
import org.loadtest4j.junit.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class LoadTesterBuilderTest {

    @Test
    public void shouldBuild() {
        final MockBuilder builder = new MockBuilder();
        final LoadTester lt = builder.build();

        assertThat(lt).isNotNull();
        assertThat(builder.calls).isEqualTo(1);
    }

    private static class MockBuilder extends LoadTesterBuilder {

        private int calls = 0;

        @Override
        protected Driver buildDriver() {
            calls += 1;
            return null;
        }
    }
}
