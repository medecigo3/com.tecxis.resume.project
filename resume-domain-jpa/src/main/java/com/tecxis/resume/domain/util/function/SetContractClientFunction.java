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
		//TODO update this test case to update a Client which leaves an orphan Contract which will later be removed by ORM. 
		SchemaUtils.testStateAfterSagemContractWithMicropoleClientUpdate(jdbcTemplateProxy);
	}
}
