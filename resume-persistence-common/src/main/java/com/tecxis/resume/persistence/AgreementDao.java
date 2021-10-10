package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Service;

public interface AgreementDao extends Dao<Agreement> {

	public Agreement findByContractAndService(Contract contract, Service service);

}
