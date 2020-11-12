package com.tecxis.commons.persistence.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;

public class EmploymentContractId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="EMPLOYMENT_CONTRACT_ID")	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPLOYMENT_CONTRACT_SEQ")
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="EMPLOYMENT_CONTRACT_SEQ", 
			 parameters = {
			            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
			            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	private long id;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")	
	private Staff staff;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
	private Supplier supplier;

	public EmploymentContractId() {
		super();
	}		

	public EmploymentContractId(long id, Staff staff, Supplier supplier) {
		super();
		this.id = id;
		this.staff = staff;
		this.supplier = supplier;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;			
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmploymentContractId)) {
			return false;
		}
		EmploymentContractId castOther = (EmploymentContractId)other;
		if (this.getSupplier() != null && castOther.getSupplier() != null) {
			if (this.getStaff() != null && castOther.getStaff() != null) {		
				
				return 	this.getSupplier().equals(castOther.getSupplier()) && 
						this.getStaff().equals(castOther.getStaff());
				
			} else return false;
		} else return false;

			
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;		
		hash = hash * prime + ((int) (this.getId()  ^ (this.getId()  >>> 32)));
		
		if (this.getSupplier() != null)
			hash = hash * prime + this.getSupplier().hashCode();
		
		if (this.getStaff() != null)
			hash = hash * prime + this.getStaff().hashCode();
		
		return hash;
	}

	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[id=" + this.getId() +
				", supplierId=" + (this.getSupplier() != null ? this.getSupplier().getId() : " null" ) +
				", staffId=" + (this.staff != null ? this.staff.getId() : "null") + 
				"]]";
	}
			
}