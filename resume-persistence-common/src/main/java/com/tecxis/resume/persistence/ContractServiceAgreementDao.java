package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Service;

public interface ContractServiceAgreementDao extends Dao<ContractServiceAgreement> {

	public ContractServiceAgreement findByContractAndService(Contract contract, Service service);

}
