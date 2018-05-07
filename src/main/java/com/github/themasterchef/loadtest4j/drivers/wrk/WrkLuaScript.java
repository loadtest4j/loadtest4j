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

        s.add("init = function(args)");
        s.add("  local r = {}");
        long i = 1;
        for (Request request: requests) {
            s.add(String.format("  r[%d] = %s", i, wrkRequest(request)));
            i++;
        }
        s.add("  req = table.concat(r)");
        s.add("end");
        s.add("");
        s.add("request = function()");
        s.add("  return req");
        s.add("end");

        return s.toString();
    }

    private static String wrkRequest(Request request) {
        final WrkRequest req = new WrkRequest(request);
        return String.format("wrk.format('%s', '%s', %s, '%s')", req.getMethod(), req.getPath(), req.getHeaders(), req.getBody());
    }
}
