package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.DriverRequest;
import com.github.loadtest4j.loadtest4j.DriverRequests;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;
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
        final DriverRequest request1 = DriverRequests.get("/pets");

        final DriverRequest request2 = DriverRequests.getWithBodyAndHeaders("/pets", "{}",
                Collections.singletonMap("Accept", "application/json"));

        final WrkLuaScript script = new WrkLuaScript(Arrays.asList(request1, request2));

        assertEquals(m( "init = function(args)",
                "  local r = {}",
                "  r[1] = wrk.format('GET', '/pets', {}, '')",
                "  r[2] = wrk.format('GET', '/pets', {['Accept'] = 'application/json'}, '{}')",
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
