package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.id.ContractId;


@StaticMetamodel(ContractId.class)
public class ContractPK_ {
	public static volatile SingularAttribute<ContractId, Long> clientId;
	public static volatile SingularAttribute<ContractId, Long> contractId;
	public static volatile SingularAttribute<ContractId, Long> staffId;
	public static volatile SingularAttribute<ContractId, Long> supplierId;	

}
