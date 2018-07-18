package org.loadtest4j.utils;

class Property {
    private final String key;
    private final String value;

    Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    protected String getKey() {
        return key;
    }

    protected String getValue() {
        return value;
    }
}
