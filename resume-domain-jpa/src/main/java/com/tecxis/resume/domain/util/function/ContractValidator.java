package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_AGREEMENTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_CLIENT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_ID_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.function.Function;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;

@FunctionalInterface
public interface ContractValidator extends Function<Contract, ValidationResult> {

	static ContractValidator isContractIdValid(long id) {
		return contract -> contract.getId().getContractId() == id ? SUCCESS : CONTRACT_ID_IS_NOT_VALID;
	}
	
	static ContractValidator isClientValid(Client client) {
		return contract -> contract.getClient().equals(client) ? SUCCESS : CONTRACT_CLIENT_IS_NOT_VALID;
	}
	
	static ContractValidator areAgreementsValid(int size) {		
		return contract -> contract.getAgreements().size() == size ? SUCCESS : CONTRACT_AGREEMENTS_ARE_NOT_VALID; 
	}
	
	static ContractValidator areSupplyContractsValid(int size) {
		return contract -> contract.getSupplyContracts().size() == size ? SUCCESS : CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID; 
	}
}
