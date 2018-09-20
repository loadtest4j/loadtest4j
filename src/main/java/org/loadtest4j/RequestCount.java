package org.loadtest4j;

public final class RequestCount {

    private final long ok;
    private final long ko;
    private final long total;

    public RequestCount(long ok, long ko, long total) {
        this.ok = ok;
        this.ko = ko;
        this.total = total;
    }

    public long getOk() {
        return ok;
    }

    public long getKo() {
        return ko;
    }

    public long getTotal() {
        return total;
    }
}
