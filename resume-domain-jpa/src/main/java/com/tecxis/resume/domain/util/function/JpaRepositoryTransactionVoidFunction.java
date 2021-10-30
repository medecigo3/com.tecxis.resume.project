package com.tecxis.resume.domain.util.function;

import java.util.function.Consumer;

import org.springframework.data.jpa.repository.JpaRepository;

@FunctionalInterface
public interface  JpaRepositoryTransactionVoidFunction <T,K> extends Consumer<JpaRepository<T, K>> {
	default void beforeTransactionCompletion() {

	}

	default void afterTransactionCompletion() {

	}
}
