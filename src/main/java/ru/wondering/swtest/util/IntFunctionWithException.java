package ru.wondering.swtest.util;

import java.io.IOException;

/**
 * Functional interface with Exception handling
 */
@FunctionalInterface
public interface IntFunctionWithException<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws IOException Exception in case of error
     */
    R apply(int value) throws IOException;
}
