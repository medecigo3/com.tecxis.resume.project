package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SERVICE_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.function.Function;

import com.tecxis.resume.domain.Agreement;

@FunctionalInterface
public interface AgreementValidator extends Function <Agreement, ValidationResult> {	

	static AgreementValidator isContractValid(String contractName) {
		return agreement -> agreement.getContract().getName().equals(contractName) ? SUCCESS : CONTRACT_NAME_IS_NOT_VALID;
	}
	
	static AgreementValidator isServiceValid(String serviceName) {
		return agreement -> agreement.getService().getName().equals(serviceName) ? SUCCESS : SERVICE_NAME_IS_NOT_VALID;
	}

}
