package com.github.themasterchef.loadtest4j.util;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;

@Category(UnitTest.class)
public class ValidatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testValidatePresenceOf() {
        final Map<String, String> map = Collections.singletonMap("foo", "1");

        Validator.validatePresenceOf(map, "foo");
    }

    @Test
    public void testValidatePresenceOfMissingKeys() {
        final Map<String, String> map = Collections.emptyMap();

        thrown.expect(Validator.MissingPropertiesException.class);
        thrown.expect(hasProperty("missingProperties", contains("foo", "bar")));

        Validator.validatePresenceOf(map,"foo", "bar");
    }

    @Test
    public void testValidatePresenceOfKeysWithMissingValues() {
        final Map<String, String> map = Collections.singletonMap("foo", null);

        Validator.validatePresenceOf(map,"foo");
    }
}
