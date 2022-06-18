package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.*;

import java.util.List;
import java.util.function.Function;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;

@FunctionalInterface
public interface ClientValidator extends Function<Client, ValidationResult> {
	
	static ClientValidator isClientNameValid(String clientName) {
		return client -> client.getName().equals(clientName)? SUCCESS : ValidationResult.CLIENT_NAME_IS_NOT_VALID;
	}
	
	static ClientValidator areContractsValid(List <Contract> contracts) {
		return client -> contracts.containsAll(client.getContracts()) ? SUCCESS : CLIENT_CONTRACTS_ARE_NOT_VALID;  
	}

}
