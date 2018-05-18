package com.github.themasterchef.loadtest4j;

import java.util.Map;

/**
 * The HTTP request model for a load test.
 *
 * The counterpart builder for this is {@link Requests}.
 */
public interface Request {
    String getBody();

    Map<String, String> getHeaders();

    String getMethod();

    String getPath();
}
