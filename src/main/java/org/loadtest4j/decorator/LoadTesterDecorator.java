package org.loadtest4j.decorator;

import org.loadtest4j.LoadTester;
import org.loadtest4j.Request;
import org.loadtest4j.Result;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Attach auxiliary behavior to a {@link LoadTester}. This behavior will run every time that the decorated
 * {@link LoadTester} runs.
 */
public class LoadTesterDecorator {

    private final List<Consumer<Result>> consumers;

    private LoadTesterDecorator(List<Consumer<Result>> consumers) {
        this.consumers = consumers;
    }

    public LoadTesterDecorator() {
        this(Collections.emptyList());
    }

    public LoadTesterDecorator add(Consumer<Result> consumer) {
        return new LoadTesterDecorator(concatenate(consumers, consumer));
    }

    public LoadTester decorate(LoadTester loadTester) {
        return new DecoratedLoadTester(loadTester, consumers);
    }

    private static <T> List<T> concatenate(List<T> list, T element) {
        return Stream.concat(list.stream(), Stream.of(element))
                .collect(Collectors.toList());
    }

    private static class DecoratedLoadTester implements LoadTester {
        private final LoadTester delegate;
        private final List<Consumer<Result>> consumers;

        private DecoratedLoadTester(LoadTester delegate, List<Consumer<Result>> consumers) {
            this.delegate = delegate;
            this.consumers = consumers;
        }

        @Override
        public Result run(List<Request> requests) {
            final Result result = delegate.run(requests);

            consumers.forEach(reporter -> reporter.accept(result));

            return result;
        }
    }
}
