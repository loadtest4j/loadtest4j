package com.github.themasterchef.loadtest4j;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The official builder for {@link Request}.
 */
public class Requests implements Request {

    private final String body;
    private final Map<String, String> headers;
    private final String method;
    private final String path;

    private Requests(String body, Map<String, String> headers, String method, String path) {
        this.body = body;
        this.headers = headers;
        this.method = method;
        this.path = path;
    }

    public static Requests get(String path) {
        return withMethodAndPath("GET", path);
    }

    public static Requests post(String path) {
        return withMethodAndPath("POST", path);
    }

    public static Requests put(String path) {
        return withMethodAndPath("PUT", path);
    }

    public static Requests delete(String path) {
        return withMethodAndPath("DELETE", path);
    }

    public static Requests head(String path) {
        return withMethodAndPath("HEAD", path);
    }

    public static Requests trace(String path) {
        return withMethodAndPath("TRACE", path);
    }

    public static Requests options(String path) {
        return withMethodAndPath("OPTIONS", path);
    }

    public static Requests patch(String path) {
        return withMethodAndPath("PATCH", path);
    }

    public static Requests link(String path) {
        return withMethodAndPath("LINK", path);
    }

    public static Requests unlink(String path) {
        return withMethodAndPath("UNLINK", path);
    }

    private static Requests withMethodAndPath(String method, String path) {
        return new Requests("", Collections.emptyMap(), method, path);
    }

    public Requests withHeader(String key, String value) {
        return new Requests(this.getBody(), concatMap(this.getHeaders(), key, value), this.getMethod(), this.getPath());
    }

    public Requests withHeaders(Map<String, String> headers) {
        return new Requests(this.getBody(), concatMaps(this.getHeaders(), headers), this.getMethod(), this.getPath());
    }

    public Requests withBody(String body) {
        return new Requests(body, this.getHeaders(), this.getMethod(), this.getPath());
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

    private static Map<String, String> concatMap(Map<String, String> map, String key, String value) {
        return concatMaps(map, Collections.singletonMap(key, value));
    }

    private static Map<String, String> concatMaps(Map<String, String> a, Map<String, String> b) {
        return Stream.concat(a.entrySet().stream(), b.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
