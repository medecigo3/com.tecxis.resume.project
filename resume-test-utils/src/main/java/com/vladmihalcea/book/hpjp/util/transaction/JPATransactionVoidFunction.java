package com.vladmihalcea.book.hpjp.util.transaction;

import java.util.function.Consumer;

/**
 * Similarly to the {@link java.util.function.Consumer Consumer} specification represents an operation that accepts a single input argument and returns no result. Unlike most other functional interfaces, Consumer is expected to operate via side-effects.
 * @author Vlad Mihalcea
 */
@FunctionalInterface
public interface  JPATransactionVoidFunction <K> extends Consumer<K> {
	default void beforeTransactionCompletion() {

	}

	default void afterTransactionCompletion() {

	}
}
