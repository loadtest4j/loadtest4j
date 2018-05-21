package com.github.loadtest4j.loadtest4j.drivers.wrk;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Regex {

    private final Pattern pattern;

    private Regex(Pattern pattern) {
        this.pattern = pattern;
    }

    protected Optional<String> firstMatch(String str) {
        final Matcher matcher = pattern.matcher(str);
        final Optional<String> match;
        if (matcher.find()) {
            match = Optional.of(matcher.group(1));
        } else {
            match = Optional.empty();
        }
        return match;
    }

    protected static Regex compile(String pattern) {
        return new Regex(Pattern.compile(pattern));
    }
}
