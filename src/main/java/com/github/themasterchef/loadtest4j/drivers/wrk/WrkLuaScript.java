package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.Request;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;

class WrkLuaScript {
    private final Collection<Request> requests;

    WrkLuaScript(Request... requests) {
        this(Arrays.asList(requests));
    }

    WrkLuaScript(Collection<Request> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        final StringJoiner s = new StringJoiner("\n");
        s.add("local requests = {}");
        s.add("local counter = 1");
        s.add("");
        s.add("init = function(args)");
        for (Request request: requests) {
            s.add("  table.insert(requests, " + wrkRequest(request) + ")");
        }
        s.add("end");
        s.add("");
        s.add("request = function()");
        s.add("  if counter > #requests then");
        s.add("    counter = 1");
        s.add("  end");
        s.add("  local r = requests[counter]");
        s.add("  counter = counter + 1");
        s.add("  return r");
        s.add("end");
        return s.toString();
    }

    private static String wrkRequest(Request request) {
        final WrkRequest req = new WrkRequest(request);
        return String.format("wrk.format('%s', '%s', %s, '%s')", req.getMethod(), req.getPath(), req.getHeaders(), req.getBody());
    }
}
