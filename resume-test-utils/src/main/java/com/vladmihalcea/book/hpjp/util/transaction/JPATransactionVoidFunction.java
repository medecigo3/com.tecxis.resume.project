package com.vladmihalcea.book.hpjp.util.transaction;

import java.util.function.Consumer;

/**
 * @author Vlad Mihalcea
 */
@FunctionalInterface
public interface  JPATransactionVoidFunction <K> extends Consumer<K> {
	default void beforeTransactionCompletion() {

	}

	default void afterTransactionCompletion() {

	}
}
