package org.loadtest4j.factory;

import org.loadtest4j.Diagnostics;
import org.loadtest4j.ResponseTime;
import org.loadtest4j.Result;

import java.time.Duration;

class ResultAdapter implements Result {
    private final ApdexAdapter apdex;
    private final Diagnostics diagnostics;
    private final double percentOk;
    private final ResponseTime responseTime;

    ResultAdapter(ApdexAdapter apdex, Diagnostics diagnostics, double percentOk, ResponseTime responseTime) {
        this.apdex = apdex;
        this.diagnostics = diagnostics;
        this.percentOk = percentOk;
        this.responseTime = responseTime;
    }

    @Override
    public double getApdexScore(Duration satisfiedThreshold) {
        return apdex.getScore(satisfiedThreshold);
    }

    @Override
    public Diagnostics getDiagnostics() {
        return diagnostics;
    }

    @Override
    public double getPercentOk() {
        return percentOk;
    }

    @Override
    public ResponseTime getResponseTime() {
        return responseTime;
    }
}
