package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Contract;

@Generated(value="Dali", date="2018-07-20T21:52:58.793+0200")
@StaticMetamodel(Contract.ContractPK.class)
public class ContractPK_ {
	public static volatile SingularAttribute<Contract.ContractPK, Long> clientId;
	public static volatile SingularAttribute<Contract.ContractPK, Long> contractId;
	public static volatile SingularAttribute<Contract.ContractPK, Long> staffId;
	public static volatile SingularAttribute<Contract.ContractPK, Long> supplierId;	

}
