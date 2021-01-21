package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.commons.persistence.id.ContractId;

@Generated(value="Dali", date="2018-07-20T21:52:58.793+0200")
@StaticMetamodel(ContractId.class)
public class ContractPK_ {
	public static volatile SingularAttribute<ContractId, Long> clientId;
	public static volatile SingularAttribute<ContractId, Long> contractId;
	public static volatile SingularAttribute<ContractId, Long> staffId;
	public static volatile SingularAttribute<ContractId, Long> supplierId;	

}
