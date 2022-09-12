package com.vladmihalcea.book.hpjp.util.transaction;

import java.util.function.Function;

import org.jboss.logging.Logger;

/**
 * Similarly to the {@link java.util.function.Function Function} this is a function that accepts one argument and produces a result.
 * @author Vlad Mihalcea
 */
@FunctionalInterface
public interface JPATransactionFunction<K, T> extends Function<K, T> {
	default void beforeTransactionCompletion() {
		Logger.getLogger(this.getClass()).error("Nothing to test after transaction complete!!!");
	}

	default void afterTransactionCompletion() {
		Logger.getLogger(this.getClass()).error("Nothing to test after transaction complete!!!");
	}
}
