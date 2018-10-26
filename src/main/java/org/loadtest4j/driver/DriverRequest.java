package org.loadtest4j.driver;

import org.loadtest4j.Body;

import java.util.Map;

/**
 * The HTTP request model for a load test driver.
 *
 * To ensure greater API stability over the long term, this contains only the attributes that the drivers need, and
 * nothing more.
 */
public final class DriverRequest {
    private final Body body;
    private final Map<String, String> headers;
    private final String method;
    private final String path;
    private final Map<String, String> queryParams;

    public DriverRequest(Body body, Map<String, String> headers, String method, String path, Map<String, String> queryParams) {
        this.body = body;
        this.headers = headers;
        this.method = method;
        this.path = path;
        this.queryParams = queryParams;
    }

    public Body getBody() {
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

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
