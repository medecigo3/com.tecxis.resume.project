package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.SequenceKeyGenerator;
import com.tecxis.resume.domain.id.Identifiable;

/**
 * The persistent class for the EMPLOYMENT_CONTRACT database table.
 * 
 */
@Entity
@Table(name=SchemaConstants.EMPLOYMENT_CONTRACT_TABLE)
public class EmploymentContract implements Serializable, Identifiable  <Long>  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="EMPLOYMENT_CONTRACT_ID")	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPLOYMENT_CONTRACT_SEQ")
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceKeyGenerator", name="EMPLOYMENT_CONTRACT_SEQ", 
			 parameters = {
			            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
			            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	private long id;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")	
	private Staff staff;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
	private Supplier supplier;
	
	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	@NotNull
	private Date startDate;


	public EmploymentContract() {
	}
	
	public EmploymentContract(Staff staff, Supplier supplier) {
		this();
		this.staff = staff;
		this.supplier = supplier;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmploymentContract)) {
			return false;
		}
		EmploymentContract castOther = (EmploymentContract)other;
		return this.getId().equals(castOther.getId());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;			
		hash = hash * prime + ((int) (this.getId()  ^ (this.getId()  >>> 32)));
		return hash;
	}

	@Override
	public String toString() {
		return  this.getClass().getName() + "@" + this.hashCode() +
				"[employmentContractId=" + this.getId() +				
				"]";
	}
}
