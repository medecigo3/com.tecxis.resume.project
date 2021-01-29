package com.tecxis.resume.persistence;

import java.util.List;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;

public interface ContractDao extends Dao<Contract> {

	public List <Contract> findByClientOrderByIdAsc(Client client);

	public Contract getContractByName(String name);

}
