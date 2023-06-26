package com.tecxis.resume.domain.util.function;

import java.util.List;
import java.util.function.Function;

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.SupplyContract;

import static com.tecxis.resume.domain.util.function.ValidationResult.*;

@FunctionalInterface
public interface ContractValidator extends Function<Contract, ValidationResult> {

	static ContractValidator isContractIdValid(long id) {
		return contract -> contract.getId().getContractId() == id ? SUCCESS : CONTRACT_ID_IS_NOT_VALID;
	}
	
	static ContractValidator isClientValid(Client client) {
		return contract -> contract.getClient().equals(client) ? SUCCESS : CONTRACT_CLIENT_IS_NOT_VALID;
	}
	
	static ContractValidator areAgreementsValid(List<Agreement> agreements) {//RES-56
		ContractValidator ret = contract -> {
			if (agreements == null || contract.getAgreements() == null)
				return CONTRACT_AGREEMENTS_ARE_NULL;
			else
			if (agreements.size() != contract.getAgreements().size())
				return CONTRACT_SIZE_AGREEMENTS_ARE_DIFFERENT;
			else
				return agreements.containsAll(contract.getAgreements()) ? SUCCESS : CONTRACT_AGREEMENTS_ARE_NOT_VALID;
		};
		return ret;
	}
	
	static ContractValidator areSupplyContractsValid(List<SupplyContract> supplyContracts) {//RES-56
		ContractValidator ret = contract -> {
			if (supplyContracts == null || contract.getSupplyContracts() == null)
				return CONTRACT_SUPPLYCONTRACTS_ARE_NULL;
			else
			if (supplyContracts.size() != contract.getSupplyContracts().size())
				return CONTRACT_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT;
			else
				return supplyContracts.containsAll(contract.getSupplyContracts()) ? SUCCESS : CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID;
		};
		return ret;
	}
}
