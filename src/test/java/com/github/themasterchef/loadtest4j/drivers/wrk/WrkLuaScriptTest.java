package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.Request;
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

        assertEquals(m( "local requests = {}",
                "local counter = 1",
                "",
                "init = function(args)",
                "end",
                "",
                "request = function()",
                "  if counter > #requests then",
                "    counter = 1",
                "  end",
                "  local r = requests[counter]",
                "  counter = counter + 1",
                "  return r",
                "end"), script.toString());
    }

    @Test
    public void testScript() {
        final Request request1 = Request.get("/pets");

        final Request request2 = Request.post("/pets")
                .withHeader("Accept", "application/json")
                .withHeader("Content-Type", "application/json")
                .withBody("{}");

        final WrkLuaScript script = new WrkLuaScript(request1, request2);

        assertEquals(m( "local requests = {}",
                "local counter = 1",
                "",
                "init = function(args)",
                "  table.insert(requests, wrk.format('GET', '/pets', {}, ''))",
                "  table.insert(requests, wrk.format('POST', '/pets', {['Accept'] = 'application/json', ['Content-Type'] = 'application/json'}, '{}'))",
                "end",
                "",
                "request = function()",
                "  if counter > #requests then",
                "    counter = 1",
                "  end",
                "  local r = requests[counter]",
                "  counter = counter + 1",
                "  return r",
                "end"), script.toString());
    }

    private static String m(String... lines) {
        return String.join("\n", lines);
    }
}
