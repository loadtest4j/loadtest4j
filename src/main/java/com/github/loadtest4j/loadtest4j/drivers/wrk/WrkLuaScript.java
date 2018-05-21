package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.DriverRequest;

import java.util.Collection;
import java.util.StringJoiner;

class WrkLuaScript {
    private final Collection<DriverRequest> requests;

    WrkLuaScript(Collection<DriverRequest> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        final StringJoiner s = new StringJoiner("\n");

        s.add("init = function(args)");
        s.add("  local r = {}");
        long i = 1;
        for (DriverRequest request: requests) {
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

    private static String wrkRequest(DriverRequest request) {
        final WrkRequest req = new WrkRequest(request);
        return String.format("wrk.format('%s', '%s', %s, '%s')", req.getMethod(), req.getPath(), req.getHeaders(), req.getBody());
    }
}
