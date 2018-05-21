package com.github.loadtest4j.loadtest4j;

import java.util.Map;

/**
 * The HTTP request model for a load test driver.
 *
 * To ensure greater API stability over the long term, this contains only the attributes that the drivers need, and
 * nothing more.
 */
public final class DriverRequest {
    private final String body;
    private final Map<String, String> headers;
    private final String method;
    private final String path;

    DriverRequest(String body, Map<String, String> headers, String method, String path) {
        this.body = body;
        this.headers = headers;
        this.method = method;
        this.path = path;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
