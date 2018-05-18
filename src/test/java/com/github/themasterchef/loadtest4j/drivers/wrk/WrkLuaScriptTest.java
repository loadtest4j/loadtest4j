package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.Request;
import com.github.themasterchef.loadtest4j.Requests;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class WrkLuaScriptTest {
    @Test
    public void testMinimalScript() {
        final WrkLuaScript script = new WrkLuaScript(Collections.emptyList());

        assertEquals(m( "init = function(args)",
                "  local r = {}",
                "  req = table.concat(r)",
                "end",
                "",
                "request = function()",
                "  return req",
                "end"), script.toString());
    }

    @Test
    public void testScript() {
        final Request request1 = Requests.get("/pets");

        final Request request2 = Requests.post("/pets")
                .withHeader("Accept", "application/json")
                .withHeader("Content-Type", "application/json")
                .withBody("{}");

        final WrkLuaScript script = new WrkLuaScript(request1, request2);

        assertEquals(m( "init = function(args)",
                "  local r = {}",
                "  r[1] = wrk.format('GET', '/pets', {}, '')",
                "  r[2] = wrk.format('POST', '/pets', {['Accept'] = 'application/json', ['Content-Type'] = 'application/json'}, '{}')",
                "  req = table.concat(r)",
                "end",
                "",
                "request = function()",
                "  return req",
                "end"), script.toString());
    }

    private static String m(String... lines) {
        return String.join("\n", lines);
    }
}
