package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SUPPLIER database table.
 * 
 */
@Entity
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SupplierPK id;

	private String name;

//	bi-directional many-to-one association to Contract. 
//	In SQL terms, Contract is the "owner" of this relationship with Supplier as it contains the relationship's foreign key
//	@OneToMany(mappedBy="supplier")
	/**
	 * uni-directional one-to-many association to Contract. 
	 * In OO terms, this Supplier "holds" Contracts.
	 */
	@OneToMany
	private List<Contract> contracts;

	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name="STAFF_ID")
//	private Staff staff;

	public Supplier() {
	}

	public SupplierPK getId() {
		return this.id;
	}

	public void setId(SupplierPK id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
//		contract.setSupplier(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
//		contract.setSupplier(null);

		return contract;
	}

//	public Staff getStaff() {
//		return this.staff;
//	}
//
//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}

}