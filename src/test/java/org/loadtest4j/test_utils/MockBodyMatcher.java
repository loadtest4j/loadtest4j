package org.loadtest4j.test_utils;

import org.loadtest4j.Body;
import org.loadtest4j.BodyPart;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * This returns a List<Object> because AssertJ is smart and knows how to compare things loosely in a type sense.
 * If we were not using AssertJ then we would need more strictly typed body matchers for testing.
 */
public class MockBodyMatcher implements Body.Matcher<List<Object>> {

    @Override
    public List<Object> string(String body) {
        return Collections.singletonList(body);
    }

    @Override
    public List<Object> multipart(List<BodyPart> body) {
        return body.stream().map(bodyPart -> (Object) bodyPart).collect(Collectors.toList());
    }
}
