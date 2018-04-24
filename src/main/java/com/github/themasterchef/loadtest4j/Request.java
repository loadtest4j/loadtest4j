package com.github.themasterchef.loadtest4j;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An HTTP request for a load test.
 */
public class Request {

    private final String body;
    private final Map<String, String> headers;
    private final String method;
    private final String path;

    Request(String body, Map<String, String> headers, String method, String path) {
        this.body = body;
        this.headers = headers;
        this.method = method;
        this.path = path;
    }

    public static Request withPath(String path) {
        return new Request("", Collections.emptyMap(), "GET", path);
    }

    public Request withHeader(String key, String value) {
        return new Request(this.getBody(), concatMap(this.getHeaders(), key, value), this.getMethod(), this.getPath());
    }

    public Request withHeaders(Map<String, String> headers) {
        return new Request(this.getBody(), concatMaps(this.getHeaders(), headers), this.getMethod(), this.getPath());
    }

    public Request withBody(String body) {
        return new Request(body, this.getHeaders(), this.getMethod(), this.getPath());
    }

    public Request withMethod(String method) {
        return new Request(this.getBody(), this.getHeaders(), method, this.getPath());
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(body, request.body) &&
                Objects.equals(headers, request.headers) &&
                Objects.equals(method, request.method) &&
                Objects.equals(path, request.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, headers, method, path);
    }

    private static Map<String, String> concatMap(Map<String, String> map, String key, String value) {
        return concatMaps(map, Collections.singletonMap(key, value));
    }

    private static Map<String, String> concatMaps(Map<String, String> a, Map<String, String> b) {
        return Stream.concat(a.entrySet().stream(), b.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
