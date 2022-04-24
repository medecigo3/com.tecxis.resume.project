package com.tecxis.resume.domain.util.function;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.SchemaUtils;
import com.vladmihalcea.book.hpjp.util.transaction.JPATransactionFunction;

@FunctionalInterface
public interface InsertClientFunction <K, T> extends JPATransactionFunction<K, T> {

	default void beforeTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testStateBeforeClientInsert(jdbcTemplateProxy);
		
	}

	default void afterTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testStateAfterClientInsert(jdbcTemplateProxy);
	}
	

}
