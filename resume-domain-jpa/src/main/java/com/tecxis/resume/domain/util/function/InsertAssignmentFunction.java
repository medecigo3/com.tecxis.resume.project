package com.tecxis.resume.domain.util.function;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.SchemaUtils;
import com.vladmihalcea.book.hpjp.util.transaction.JPATransactionVoidFunction;

@FunctionalInterface
public interface InsertAssignmentFunction <K> extends JPATransactionVoidFunction<K> {
	default void beforeTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testInsertAssignmentInitialState(jdbcTemplateProxy);
	}
	
	default void afterTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testStateAfter_Assignment_Insert(jdbcTemplateProxy);
	}
}
