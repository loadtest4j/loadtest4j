package org.loadtest4j;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A description of an HTTP request to be run in a load test.
 */
public final class Request {

    private final Body body;
    private final Map<String, String> headers;
    private final String method;
    private final String path;
    private final Map<String, String> queryParams;

    private Request(Body body, Map<String, String> headers, String method, String path, Map<String, String> queryParams) {
        this.body = body;
        this.headers = headers;
        this.method = method;
        this.path = path;
        this.queryParams = queryParams;
    }

    public static Request get(String path) {
        return withMethodAndPath("GET", path);
    }

    public static Request post(String path) {
        return withMethodAndPath("POST", path);
    }

    public static Request put(String path) {
        return withMethodAndPath("PUT", path);
    }

    public static Request delete(String path) {
        return withMethodAndPath("DELETE", path);
    }

    public static Request head(String path) {
        return withMethodAndPath("HEAD", path);
    }

    public static Request trace(String path) {
        return withMethodAndPath("TRACE", path);
    }

    public static Request options(String path) {
        return withMethodAndPath("OPTIONS", path);
    }

    public static Request patch(String path) {
        return withMethodAndPath("PATCH", path);
    }

    public static Request link(String path) {
        return withMethodAndPath("LINK", path);
    }

    public static Request unlink(String path) {
        return withMethodAndPath("UNLINK", path);
    }

    private static Request withMethodAndPath(String method, String path) {
        return new Request(Body.string(""), Collections.emptyMap(), method, path, Collections.emptyMap());
    }

    public Request withHeader(String key, String value) {
        return new Request(this.getBody(), concatMap(this.getHeaders(), key, value), this.getMethod(), this.getPath(), this.getQueryParams());
    }

    public Request withHeaders(Map<String, String> headers) {
        return new Request(this.getBody(), concatMaps(this.getHeaders(), headers), this.getMethod(), this.getPath(), this.getQueryParams());
    }

    public Request withQueryParam(String key, String value) {
        return new Request(this.getBody(), this.getHeaders(), this.getMethod(), this.getPath(), concatMap(this.getQueryParams(), key, value));
    }

    public Request withBody(Body body) {
        return new Request(body, this.getHeaders(), this.getMethod(), this.getPath(), this.getQueryParams());
    }

    public Body getBody() {
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

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    private static Map<String, String> concatMap(Map<String, String> map, String key, String value) {
        return concatMaps(map, Collections.singletonMap(key, value));
    }

    private static Map<String, String> concatMaps(Map<String, String> a, Map<String, String> b) {
        return Stream.concat(a.entrySet().stream(), b.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
