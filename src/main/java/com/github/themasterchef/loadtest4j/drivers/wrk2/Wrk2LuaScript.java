package com.github.themasterchef.loadtest4j.drivers.wrk2;

import java.util.Map;
import java.util.StringJoiner;

class Wrk2LuaScript {

    private final String body;
    private final Map<String, String> headers;
    private final String method;

    Wrk2LuaScript(String body, Map<String, String> headers, String method) {
        this.body = body;
        this.headers = headers;
        this.method = method;
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner("\n");

        joiner.add(String.format("wrk.body = '%s'", body));

        headers.forEach((k, v) -> joiner.add(String.format("wrk.headers['%s'] = '%s'", k, v)));

        joiner.add(String.format("wrk.method = '%s'", method));

        return joiner.toString();
    }
}
