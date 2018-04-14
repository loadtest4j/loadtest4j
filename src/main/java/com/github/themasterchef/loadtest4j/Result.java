package com.github.themasterchef.loadtest4j;

/**
 * The result of a load test.
 */
public class Result {

    private final int errors;
    private final int requests;

    public Result(int errors, int requests) {
        this.errors = errors;
        this.requests = requests;
    }

    public int getErrors() {
        return errors;
    }

    public int getRequests() {
        return requests;
    }
}
