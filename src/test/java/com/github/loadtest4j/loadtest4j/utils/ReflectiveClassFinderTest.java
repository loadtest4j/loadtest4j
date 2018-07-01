package com.github.loadtest4j.loadtest4j.utils;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ReflectiveClassFinderTest {

    private ClassFinder<A> sut() {
        return new ReflectiveClassFinder<>(A.class);
    }

    @Test
    public void testFind() {
        final ClassFinder<A> finder = sut();

        final Optional<A> found = finder.find(AImpl.class.getName());

        assertThat(found).containsInstanceOf(AImpl.class);
    }

    @Test(expected = ClassCastException.class)
    public void testFindWithWrongClass() {
        final ClassFinder<A> finder = sut();

        finder.find("java.lang.String");
    }

    @Test
    public void testFindWithNonExistentClass() {
        final ClassFinder<A> finder = sut();

        final Optional<A> found = finder.find("foo");

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
}
