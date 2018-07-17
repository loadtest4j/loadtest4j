/*
 * This file contains portions of code from the Guava project.
 *
 * Guava project license:
 *
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.loadtest4j.loadtest4j.utils;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

/**
 * An adaptation of the Google Guava SuppliersTest class.
 */
@Category(UnitTest.class)
public class SuppliersTest {

    @Test
    public void testMemoize() {
        final CountingSupplier countingSupplier = new CountingSupplier();
        final Supplier<Integer> memoizedSupplier = Suppliers.memoize(countingSupplier);

        // the underlying supplier hasn't executed yet
        assertThat(countingSupplier.calls).isEqualTo(0);

        assertThat(memoizedSupplier.get()).isEqualTo(10);

        // now it has
        assertThat(countingSupplier.calls).isEqualTo(1);

        assertThat(memoizedSupplier.get()).isEqualTo(10);

        // it still should only have executed once due to memoization
        assertThat(countingSupplier.calls).isEqualTo(1);
    }

    @Test
    public void testMemoizeRedundantly() {
        final Supplier<Integer> memoizedSupplier = Suppliers.memoize(new CountingSupplier());

        assertThat(memoizedSupplier).isSameAs(Suppliers.memoize(memoizedSupplier));
    }

    @Test
    public void testMemoizeExceptionThrown() {
        final Supplier<Integer> memoizedSupplier = Suppliers.memoize(new ThrowingSupplier());

        // call get() twice to make sure that memoization doesn't interfere
        // with throwing the exception
        assertThatNullPointerException().isThrownBy(memoizedSupplier::get);
        assertThatNullPointerException().isThrownBy(memoizedSupplier::get);
    }

    @Test
    public void testMemoizedSupplierThreadSafe() throws Throwable {
        final Function<Supplier<Boolean>, Supplier<Boolean>> memoizer = Suppliers::memoize;

        final AtomicInteger count = new AtomicInteger(0);
        final AtomicReference<Throwable> thrown = new AtomicReference<>(null);
        final int numThreads = 3;
        final Thread[] threads = new Thread[numThreads];
        final long timeout = TimeUnit.SECONDS.toNanos(60);

        final Supplier<Boolean> supplier =
                new Supplier<Boolean>() {
                    boolean isWaiting(Thread thread) {
                        switch (thread.getState()) {
                            case BLOCKED:
                            case WAITING:
                            case TIMED_WAITING:
                                return true;
                            default:
                                return false;
                        }
                    }

                    int waitingThreads() {
                        int waitingThreads = 0;
                        for (Thread thread : threads) {
                            if (isWaiting(thread)) {
                                waitingThreads++;
                            }
                        }
                        return waitingThreads;
                    }

                    @Override
                    public Boolean get() {
                        // Check that this method is called exactly once, by the first
                        // thread to synchronize.
                        long t0 = System.nanoTime();
                        while (waitingThreads() != numThreads - 1) {
                            if (System.nanoTime() - t0 > timeout) {
                                thrown.set(
                                        new TimeoutException(
                                                "timed out waiting for other threads to block"
                                                        + " synchronizing on supplier"));
                                break;
                            }
                            Thread.yield();
                        }
                        count.getAndIncrement();
                        return Boolean.TRUE;
                    }
                };

        final Supplier<Boolean> memoizedSupplier = memoizer.apply(supplier);

        for (int i = 0; i < numThreads; i++) {
            threads[i] =
                    new Thread(() -> assertThat(memoizedSupplier.get()).isSameAs(Boolean.TRUE));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        if (thrown.get() != null) {
            throw thrown.get();
        }
        assertThat(count.get()).isEqualTo(1);
    }

    private static class CountingSupplier implements Supplier<Integer> {
        int calls = 0;

        @Override
        public Integer get() {
            calls++;
            return calls * 10;
        }
    }

    private static class ThrowingSupplier implements Supplier<Integer> {
        @Override
        public Integer get() {
            throw new NullPointerException();
        }
    }
}