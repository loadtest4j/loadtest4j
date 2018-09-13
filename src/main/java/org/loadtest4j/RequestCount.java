package org.loadtest4j;

public final class RequestCount {

    private final long ok;
    private final long ko;

    public RequestCount(long ok, long ko) {
        this.ok = ok;
        this.ko = ko;
    }

    public long getOk() {
        return ok;
    }

    public long getKo() {
        return ko;
    }

    public long getTotal() {
        return ok + ko;
    }
}
