package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.function.Function;

import com.tecxis.resume.domain.Client;

@FunctionalInterface
public interface ClientValidator extends Function<Client, ValidationResult> {
	
	static ClientValidator isClientNameValid(String clientName) {
		return client -> client.getName().equals(clientName)? SUCCESS : ValidationResult.CLIENT_NAME_IS_NOT_VALID;
	}

}
