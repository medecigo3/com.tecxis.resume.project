package com.tecxis.resume.domain.util.function;

import java.util.function.Consumer;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.SchemaValidator;

@FunctionalInterface

public interface JPATransactionVoidFunction <K>  extends Consumer<K> {
	
	default void beforeTransactionCompletion(SchemaValidator schemaValidator, JdbcTemplate jdbcTemplate) {
		schemaValidator.validate(jdbcTemplate);
	}

	default void afterTransactionCompletion(SchemaValidator schemaValidator, JdbcTemplate jdbcTemplate) {
		schemaValidator.validate(jdbcTemplate);
	}

}
