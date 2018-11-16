package org.loadtest4j.decorator;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.LoadTester;
import org.loadtest4j.Request;
import org.loadtest4j.Result;
import org.loadtest4j.junit.UnitTest;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Category(UnitTest.class)
public class LoadTesterDecoratorTest {

    @Test
    public void shouldDecorate() {
        final Counter counter = new Counter();
        final StubLoadTester stubLoadTester = new StubLoadTester();

        final LoadTester loadTester = new LoadTesterDecorator()
                .add(counter)
                .decorate(stubLoadTester);

        loadTester.run(Collections.emptyList());

        assertSoftly(s -> {
            s.assertThat(stubLoadTester.getNumCalls()).isEqualTo(1);
            s.assertThat(counter.getCount()).isEqualTo(1);
        });
    }

    @Test
    public void shouldDecorateWithMultipleReporters() {
        final Counter counter1 = new Counter();
        final Counter counter2 = new Counter();
        final StubLoadTester stubLoadTester = new StubLoadTester();

        final LoadTester loadTester = new LoadTesterDecorator()
                .add(counter1)
                .add(counter2)
                .decorate(stubLoadTester);

        loadTester.run(Collections.emptyList());

        assertSoftly(s -> {
            s.assertThat(stubLoadTester.getNumCalls()).isEqualTo(1);
            s.assertThat(counter1.getCount()).isEqualTo(1);
            s.assertThat(counter2.getCount()).isEqualTo(1);
        });
    }

    @Test
    public void shouldDecorateMultipleLoadTesters() {
        final Counter counter = new Counter();
        final LoadTesterDecorator decorator = new LoadTesterDecorator()
                .add(counter);

        final StubLoadTester stub1 = new StubLoadTester();
        decorator.decorate(stub1).run(Collections.emptyList());

        final StubLoadTester stub2 = new StubLoadTester();
        decorator.decorate(stub2).run(Collections.emptyList());

        assertSoftly(s -> {
            s.assertThat(stub1.getNumCalls()).isEqualTo(1);
            s.assertThat(stub2.getNumCalls()).isEqualTo(1);
            s.assertThat(counter.getCount()).isEqualTo(2);
        });
    }

    @Test
    public void shouldBeImmutable() {
        final LoadTesterDecorator decorator = new LoadTesterDecorator();

        decorator.add(new Counter());

        assertThat(decorator).isEqualToComparingFieldByField(new LoadTesterDecorator());
    }

    private static class Counter implements Consumer<Result> {

        private final AtomicLong numCalls = new AtomicLong();

        @Override
        public void accept(Result result) {
            numCalls.incrementAndGet();
        }

        long getCount() {
            return numCalls.longValue();
        }
    }

    private static class StubLoadTester implements LoadTester {

        private final AtomicLong numCalls = new AtomicLong();

        @Override
        public Result run(List<Request> requests) {
            numCalls.incrementAndGet();
            return null;
        }

        long getNumCalls() {
            return numCalls.longValue();
        }
    }
}
