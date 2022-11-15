package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.SchemaUtils.testInsertCityInitialState;
import static com.tecxis.resume.domain.SchemaUtils.testStateAfter_City_Insert;

import org.springframework.jdbc.core.JdbcTemplate;

import com.vladmihalcea.book.hpjp.util.transaction.JPATransactionVoidFunction;

@FunctionalInterface
public interface InsertCityFunction <K> extends JPATransactionVoidFunction<K> {

	default void beforeTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		testInsertCityInitialState(jdbcTemplateProxy);
	}
	
	default void afterTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		testStateAfter_City_Insert(jdbcTemplateProxy);
	}
}
