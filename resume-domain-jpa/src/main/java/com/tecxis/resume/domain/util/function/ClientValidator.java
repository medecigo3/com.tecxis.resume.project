package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.CLIENT_CONTRACTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.List;
import java.util.function.Function;

import javax.validation.constraints.Null;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;

@FunctionalInterface
public interface ClientValidator extends Function<Client, ValidationResult> {
	Logger logger = LoggerFactory.getLogger(ClientValidator.class);
	
	static ClientValidator isClientNameValid(String clientName) {
		return client -> client.getName().equals(clientName)? SUCCESS : ValidationResult.CLIENT_NAME_IS_NOT_VALID;
	}
	
	static ClientValidator areContractsValid(@Null List <Contract> contracts) {
		ClientValidator ret = client -> {
			final List <Contract>  clientContracts = client.getContracts(); 
			logger.debug("Comparing Client contracts: '" +  clientContracts + "' vs. param: '" + contracts + "'");
			
			if (clientContracts == null && contracts == null)
				return SUCCESS;
			
			if (clientContracts != null && contracts == null || clientContracts == null && contracts != null)
				return CLIENT_CONTRACTS_ARE_NOT_VALID;
				
			if (contracts.containsAll(clientContracts)) 
				return contracts.containsAll(client.getContracts()) ? SUCCESS : CLIENT_CONTRACTS_ARE_NOT_VALID;
			
			else 
				return CLIENT_CONTRACTS_ARE_NOT_VALID;
		};
		return ret;   
	}

}
