package com.tecxis.resume.domain.util.function;

import java.util.function.Function;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.SchemaValidator;

public interface JPATransactionFunction<T, R> extends Function<T, R> {
	
	default void beforeTransactionCompletion(SchemaValidator schemaValidator, JdbcTemplate jdbcTemplate) {
		schemaValidator.validate(jdbcTemplate);
	}

	default void afterTransactionCompletion(SchemaValidator schemaValidator, JdbcTemplate jdbcTemplate) {
		schemaValidator.validate(jdbcTemplate);
	}

}
