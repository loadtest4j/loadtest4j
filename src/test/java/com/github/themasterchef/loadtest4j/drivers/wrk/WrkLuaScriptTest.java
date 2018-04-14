package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class WrkLuaScriptTest {
    @Test
    public void testScript() {
        final WrkLuaScript script = new WrkLuaScript("{}", Collections.singletonMap("Content-Type", "application/json"), "POST");

        assertEquals(m("wrk.body = '{}'", "wrk.headers['Content-Type'] = 'application/json'", "wrk.method = 'POST'"), script.toString());
    }

    @Test
    public void testMinimalScript() {
        final WrkLuaScript script = new WrkLuaScript("{}", Collections.emptyMap(), "GET");

        assertEquals(m("wrk.body = '{}'", "wrk.method = 'GET'"), script.toString());
    }

    private static String m(String... lines) {
        final StringJoiner joiner = new StringJoiner("\n");
        for (String line: lines) {
            joiner.add(line);
        }
        return joiner.toString();
    }
}
