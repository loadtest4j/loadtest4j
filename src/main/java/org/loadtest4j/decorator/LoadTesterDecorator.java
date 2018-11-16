package org.loadtest4j.decorator;

import org.loadtest4j.LoadTester;
import org.loadtest4j.Request;
import org.loadtest4j.Result;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Attach auxiliary behavior to a {@link LoadTester}. This behavior will run every time that the decorated
 * {@link LoadTester} runs.
 */
public class LoadTesterDecorator {

    private final List<Reporter> reporters;

    private LoadTesterDecorator(List<Reporter> reporters) {
        this.reporters = reporters;
    }

    public LoadTesterDecorator() {
        this(Collections.emptyList());
    }

    public LoadTesterDecorator add(Reporter reporter) {
        return new LoadTesterDecorator(concatenate(reporters, reporter));
    }

    public LoadTester decorate(LoadTester loadTester) {
        return new DecoratedLoadTester(loadTester, reporters);
    }

    private static <T> List<T> concatenate(List<T> list, T element) {
        return Stream.concat(list.stream(), Stream.of(element))
                .collect(Collectors.toList());
    }

    private static class DecoratedLoadTester implements LoadTester {
        private final LoadTester delegate;
        private final List<Reporter> reporters;

        private DecoratedLoadTester(LoadTester delegate, List<Reporter> reporters) {
            this.delegate = delegate;
            this.reporters = reporters;
        }

        @Override
        public Result run(List<Request> requests) {
            final Result result = delegate.run(requests);

            reporters.forEach(reporter -> reporter.report(result));

            return result;
        }
    }
}
