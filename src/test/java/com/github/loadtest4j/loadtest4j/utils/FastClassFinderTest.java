package com.github.loadtest4j.loadtest4j.utils;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class FastClassFinderTest {

    private ClassFinder sut() {
        return new FastClassFinder();
    }

    @Test
    public void testFind() {
        final ClassFinder classFinder = sut();

        final Collection<A> found = classFinder.findImplementationsOf(A.class);

        assertThat(found)
                .isNotEmpty()
                .hasOnlyElementsOfType(AImpl.class);
    }

    @Test
    public void testFindWithNoImplementations() {
        final ClassFinder classFinder = sut();

        final Collection<B> found = classFinder.findImplementationsOf(B.class);

        assertThat(found).isEmpty();
    }

    public interface A {
        void foo();
    }

    public static class AImpl implements A {

        @Override
        public void foo() {
            // No-op
        }
    }

    public interface B {
        void bar();
    }
}
