package org.loadtest4j;

/**
 * An instance of exceptional behavior in this library.
 */
public class LoadTesterException extends RuntimeException {

    public LoadTesterException(String reason) {
        super(reason);
    }

    public LoadTesterException(Exception e) {
        super(e);
    }
}
