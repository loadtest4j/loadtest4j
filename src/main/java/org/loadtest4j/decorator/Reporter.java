package org.loadtest4j.decorator;

import org.loadtest4j.Result;

public interface Reporter {
    void report(Result result);
}
