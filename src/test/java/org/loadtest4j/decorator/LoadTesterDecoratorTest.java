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

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Category(UnitTest.class)
public class LoadTesterDecoratorTest {

    @Test
    public void shouldDecorate() {
        final StubReporter reporter = new StubReporter();
        final StubLoadTester stubLoadTester = new StubLoadTester();

        final LoadTester loadTester = new LoadTesterDecorator()
                .add(reporter)
                .decorate(stubLoadTester);

        loadTester.run(Collections.emptyList());

        assertSoftly(s -> {
            s.assertThat(stubLoadTester.getNumCalls()).isEqualTo(1);
            s.assertThat(reporter.getNumCalls()).isEqualTo(1);
        });
    }

    @Test
    public void shouldDecorateWithMultipleReporters() {
        final StubReporter reporter1 = new StubReporter();
        final StubReporter reporter2 = new StubReporter();
        final StubLoadTester stubLoadTester = new StubLoadTester();

        final LoadTester loadTester = new LoadTesterDecorator()
                .add(reporter1)
                .add(reporter2)
                .decorate(stubLoadTester);

        loadTester.run(Collections.emptyList());

        assertSoftly(s -> {
            s.assertThat(stubLoadTester.getNumCalls()).isEqualTo(1);
            s.assertThat(reporter1.getNumCalls()).isEqualTo(1);
            s.assertThat(reporter2.getNumCalls()).isEqualTo(1);
        });
    }

    @Test
    public void shouldDecorateMultipleLoadTesters() {
        final StubReporter reporter = new StubReporter();
        final LoadTesterDecorator decorator = new LoadTesterDecorator()
                .add(reporter);

        final StubLoadTester stub1 = new StubLoadTester();
        decorator.decorate(stub1).run(Collections.emptyList());

        final StubLoadTester stub2 = new StubLoadTester();
        decorator.decorate(stub2).run(Collections.emptyList());

        assertSoftly(s -> {
            s.assertThat(stub1.getNumCalls()).isEqualTo(1);
            s.assertThat(stub2.getNumCalls()).isEqualTo(1);
            s.assertThat(reporter.getNumCalls()).isEqualTo(2);
        });
    }

    private static class StubReporter implements Reporter {

        private final AtomicLong numCalls = new AtomicLong();

        @Override
        public void report(Result result) {
            numCalls.incrementAndGet();
        }

        long getNumCalls() {
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
