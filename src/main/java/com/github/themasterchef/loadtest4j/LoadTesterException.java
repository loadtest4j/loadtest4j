package com.github.themasterchef.loadtest4j;

/**
 * An instance of exceptional behavior in this library.
 *
 * In addition, any checked exceptions thrown by sub-components will be wrapped with this class and re-thrown.
 */
public class LoadTesterException extends RuntimeException {

    public LoadTesterException(String reason) {
        super(reason);
    }

    public LoadTesterException(Exception e) {
        super(e);
    }
}
