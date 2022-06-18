package com.tecxis.resume.domain.util.function;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.SchemaUtils;
import com.vladmihalcea.book.hpjp.util.transaction.JPATransactionVoidFunction;

@FunctionalInterface
public interface SetContractClientFunction <K> extends JPATransactionVoidFunction<K> {

	default void beforeTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testStateAfterSagemContractDelete(jdbcTemplateProxy);
	}
	
	default void afterTransactionCompletion(JdbcTemplate jdbcTemplateProxy) {
		SchemaUtils.testStateAfterSagemContractWithMicropoleClientUpdate(jdbcTemplateProxy);
	}
}
