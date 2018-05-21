package com.github.themasterchef.loadtest4j;

import java.util.Collections;
import java.util.Map;

public class DriverRequests {
    public static DriverRequest get(String path) {
        return new DriverRequest("", Collections.emptyMap(), "GET", path);
    }

    public static DriverRequest getWithBody(String path, String body) {
        return new DriverRequest(body, Collections.emptyMap(), "GET", path);
    }

    public static DriverRequest getWithHeaders(String path, Map<String, String> headers) {
        return new DriverRequest("", headers, "GET", path);
    }

    public static DriverRequest getWithBodyAndHeaders(String path, String body, Map<String, String> headers) {
        return new DriverRequest(body, headers, "GET", path);
    }
}
