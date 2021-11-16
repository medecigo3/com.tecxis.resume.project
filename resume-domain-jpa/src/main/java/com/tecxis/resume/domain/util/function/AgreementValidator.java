package com.tecxis.resume.domain.util.function;

import java.util.function.Function;

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.util.function.AgreementValidator.AgreementValidationResult;

@FunctionalInterface
public interface AgreementValidator extends Function <Agreement, AgreementValidationResult> {
	

	static AgreementValidator isContractValid(String contractName) {
		return agreement -> agreement.getContract().getName().equals(contractName) ? AgreementValidationResult.SUCCESS : AgreementValidationResult.CONTRACT_IS_NOT_VALID;
	}
	
	static AgreementValidator isServiceValid(String serviceName) {
		return agreement -> agreement.getService().getName().equals(serviceName) ? AgreementValidationResult.SUCCESS : AgreementValidationResult.SERVICE_IS_NOT_VALID;
	}
	
	default AgreementValidator and(AgreementValidator other) {
		return agreement -> {
			AgreementValidationResult result = this.apply(agreement);
			return result.equals(AgreementValidationResult.SUCCESS) ? other.apply(agreement) : result;
		};	
		
	}

	enum AgreementValidationResult{
		SUCCESS,
		CONTRACT_IS_NOT_VALID,
		SERVICE_IS_NOT_VALID
	}
	
}
