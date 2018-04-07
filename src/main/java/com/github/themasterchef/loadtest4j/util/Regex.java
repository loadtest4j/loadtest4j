package com.github.themasterchef.loadtest4j.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private final Pattern pattern;

    private Regex(Pattern pattern) {
        this.pattern = pattern;
    }

    public Optional<String> firstMatch(String str) {
        final Matcher matcher = pattern.matcher(str);
        final Optional<String> match;
        if (matcher.find()) {
            match = Optional.of(matcher.group(1));
        } else {
            match = Optional.empty();
        }
        return match;
    }

    public static Regex compile(String pattern) {
        return new Regex(Pattern.compile(pattern));
    }
}
