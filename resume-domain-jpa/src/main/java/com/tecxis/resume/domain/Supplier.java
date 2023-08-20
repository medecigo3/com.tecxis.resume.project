package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.tecxis.resume.domain.id.Identifiable;



/**
 * The persistent class for the SUPPLIER database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= {  "NAME" }))
public class Supplier implements Serializable, Identifiable <Long>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="SUPPLIER_ID")
	@SequenceGenerator(name="SUPPLIER_SUPPLIERID_GENERATOR", sequenceName="SUPPLIER_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUPPLIER_SUPPLIERID_GENERATOR")
	private long id;
	
	/**
	 *  bi-directional one-to-many association to EmploymentContract
	 * 	In SQL terms, EmploymentContract is the "owner" of this relationship with Supplier as it contains the relationship's foreign key
	 *  In OO terms, this Supplier "employs" through EmploymentContracts.
	 */
	@OneToMany (mappedBy="supplier", cascade = CascadeType.ALL, orphanRemoval=true)
	private List <EmploymentContract> employmentContracts;
			

	/**
	 * bi-directional one-to-many association to SupplyContract. 
	 * In SQL terms, SupplyContract is the "owner" of this relationship with Supplier as it contains the relationship's foreign keys
	 * In OO terms, this Supplier "AGREES" to these SupplyContracts.
	 */
	@OneToMany(mappedBy="supplier", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	private List<SupplyContract> supplyContracts;

	@NotNull
	private String name;


	public Supplier() {
		this.supplyContracts = new ArrayList <> ();
		this.employmentContracts = new ArrayList <> ();
	}

	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public List <EmploymentContract> getEmploymentContracts() {
		return this.employmentContracts;
	}

	/**
	 * @param supplyContracts orphans are removed when set to null.
	 * */
	public void setSupplyContracts(List<SupplyContract> supplyContracts) {
		this.supplyContracts.clear();//RES-52
		if(supplyContracts != null) {
			this.supplyContracts.addAll(supplyContracts);
		}
	}
	
	public void addSupplyContract(SupplyContract supplyContract) {
		this.supplyContracts.add(supplyContract);
		supplyContract.setSupplier(this);
	}
	
	public void removeSupplyContract(SupplyContract supplyContract) {
		this.supplyContracts.remove(supplyContract);
		supplyContract.setSupplier(null);
	}
	
	public void setEmploymentContracts(List<EmploymentContract> employmentContracts) {
		if (employmentContracts != null) {
			this.employmentContracts.clear();
			for (EmploymentContract employmentContract : employmentContracts) { //TODO RES-51
				this.employmentContracts.add(employmentContract); 				
			}
		}
		else {
			this.employmentContracts.clear();
		}
	}
	
	public void addEmploymentContract(EmploymentContract employmentContract) {
		this.employmentContracts.add(employmentContract);
		employmentContract.setSupplier(this);
	}
	
	public void removeEmploymentContract(EmploymentContract employmentContract) {
		this.employmentContracts.remove(employmentContract);
		employmentContract.setSupplier(null);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<SupplyContract> getSupplyContracts() {
		return this.supplyContracts;
	}
		
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Supplier)) {
			return false;
		}
		Supplier castOther = (Supplier)other;
		return 
			this.getId()== castOther.getId();		
	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + + ((int) (this.getId() ^ (this.getId() >>> 32)));		
		
		return hash;
	}

	@Override
	public String toString() {
		return  this.getClass().getName() + "@" + this.hashCode() +		
				"[name=" +this.getName() + 
				", supplierId=" + getId() +
				"]";
	}

}