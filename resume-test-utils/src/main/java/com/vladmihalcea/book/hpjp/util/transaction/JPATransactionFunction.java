package com.vladmihalcea.book.hpjp.util.transaction;

import java.util.function.Function;

/**
 * Similarly to the {@link java.util.function.Function Function} this is a function that accepts one argument and produces a result.
 * @author Vlad Mihalcea
 */
@FunctionalInterface
public interface JPATransactionFunction<K, T> extends Function<K, T> {
	default void beforeTransactionCompletion() {

	}

	default void afterTransactionCompletion() {

	}
}
