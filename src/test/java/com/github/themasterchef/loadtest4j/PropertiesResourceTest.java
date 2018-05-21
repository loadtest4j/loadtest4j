package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.junit.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

@Category(IntegrationTest.class)
public class PropertiesResourceTest {
    @Test
    public void getProperties() {
        final PropertiesResource sut = new PropertiesResource("/props/example.properties");

        final Properties properties = sut.getProperties();

        final Properties expectedProperties = new Properties();
        expectedProperties.put("foo", "bar");
        assertEquals(expectedProperties, properties);
    }

    @Test
    public void getEmptyProperties() {
        final PropertiesResource sut = new PropertiesResource("/props/fake.properties");

        final Properties properties = sut.getProperties();

        assertEquals(new Properties(), properties);
    }
}
