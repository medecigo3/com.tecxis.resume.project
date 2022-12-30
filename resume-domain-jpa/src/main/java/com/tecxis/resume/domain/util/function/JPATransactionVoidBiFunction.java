package com.tecxis.resume.domain.util.function;

import java.util.function.BiConsumer;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.SchemaValidator;

public interface JPATransactionVoidBiFunction <T,U> extends BiConsumer<T, U> {

	default void beforeTransactionCompletion(SchemaValidator schemaValidator, JdbcTemplate jdbcTemplate) {
		schemaValidator.validate(jdbcTemplate);
	}

	default void afterTransactionCompletion(SchemaValidator schemaValidator, JdbcTemplate jdbcTemplate) {
		schemaValidator.validate(jdbcTemplate);
	}
}
