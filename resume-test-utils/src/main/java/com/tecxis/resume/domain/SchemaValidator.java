package com.tecxis.resume.domain;

import org.springframework.jdbc.core.JdbcTemplate;

public interface SchemaValidator {
	
	void validate(JdbcTemplate jdbcTemplate);

}
