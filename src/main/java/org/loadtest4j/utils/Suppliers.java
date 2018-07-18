/*
 * This file contains portions of code from the Guava project (commit 4adedb7).
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

package org.loadtest4j.utils;

import java.util.function.Supplier;

public final class Suppliers {
    private Suppliers() {
    }

    /**
     * Returns a supplier which caches the instance retrieved during the first call to {@code get()}
     * and returns that value on subsequent calls to {@code get()}. This technique is called memoization.
     *
     * Thread-safety: The returned supplier is thread-safe. The delegate's {@code get()} method will be invoked at
     * most once unless the underlying {@code get()} throws an exception.
     *
     * Exception handling: When the underlying delegate throws an exception then this memoizing supplier will keep
     * delegating calls until it returns valid data.
     *
     * Nesting: If {@code delegate} is an instance created by an earlier call to {@code memoize}, it is
     * returned directly.
     *
     * Serialization: NO GUARANTEES are made about behavior in a serialized context. Loadtest4j does not use this
     * utility in a serialized context, so serialization handling has been removed to make the implementation simpler.
     *
     * @param <T> The type returned by the Supplier function
     * @param delegate The Supplier to memoize
     * @return A memoized version of the delegate
     */
    public static <T> Supplier<T> memoize(Supplier<T> delegate) {
        if (delegate instanceof NonSerializableMemoizingSupplier) {
            return delegate;
        }
        return new NonSerializableMemoizingSupplier<>(delegate);
    }

    private static class NonSerializableMemoizingSupplier<T> implements Supplier<T> {
        private volatile Supplier<T> delegate;
        private volatile T value;

        NonSerializableMemoizingSupplier(Supplier<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get() {
            // Double-checked locking (https://en.wikipedia.org/wiki/Double-checked_locking)
            if (value == null) {
                synchronized (this) {
                    if (value == null) {
                        T t = delegate.get();
                        value = t;

                        // Release the delegate to GC.
                        delegate = null;
                        return t;
                    }
                }
            }
            return value;
        }
    }
}