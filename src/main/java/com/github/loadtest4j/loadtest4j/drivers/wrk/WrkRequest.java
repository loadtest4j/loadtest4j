package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.DriverRequest;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

class WrkRequest {
    private final DriverRequest request;

    WrkRequest(DriverRequest request) {
        this.request = request;
    }

    protected String getMethod() {
        return escapeSingleQuotes(request.getMethod());
    }

    protected String getBody() {
        return escapeSingleQuotes(request.getBody());
    }

    protected String getPath() {
        return escapeSingleQuotes(request.getPath());
    }

    protected Map<String, String> getHeaders() {
        final Map<String, String> escapedMap = escapeSingleQuotes(request.getHeaders());
        return new LuaMap(escapedMap);
    }

    private static Map<String, String> escapeSingleQuotes(Map<String, String> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(e -> escapeSingleQuotes(e.getKey()), e -> escapeSingleQuotes(e.getValue())));
    }

    private static String escapeSingleQuotes(String str) {
        return str.replace("'", "\\'");
    }

    private static class LuaMap extends AbstractMap<String, String> {
        private final Map<String, String> map;

        LuaMap(Map<String, String> map) {
            this.map = map;
        }

        @Override
        public Set<Entry<String, String>> entrySet() {
            return map.entrySet();
        }

        @Override
        public String toString() {
            return luaMap(map);
        }

        private static String luaMap(Map<String, String> map) {
            final StringJoiner sj = new StringJoiner(", ");
            map.forEach((k, v) -> sj.add(luaMapEntry(k, v)));
            return "{" + sj.toString() + "}";
        }

        private static String luaMapEntry(String k, String v) {
            return String.format("['%s'] = '%s'", k, v);
        }
    }
}
