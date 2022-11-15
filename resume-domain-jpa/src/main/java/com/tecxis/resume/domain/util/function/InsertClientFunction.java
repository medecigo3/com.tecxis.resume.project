package com.tecxis.resume.domain.util.function;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.SchemaUtils;
import com.vladmihalcea.book.hpjp.util.transaction.JPATransactionFunction;

@FunctionalInterface
public interface InsertClientFunction <K, T> extends JPATransactionFunction<K, T> {

	default void beforeTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testStateBefore_Client_Insert(jdbcTemplateProxy);
		
	}

	default void afterTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testStateAfter_Client_Insert(jdbcTemplateProxy);
	}
	

}
