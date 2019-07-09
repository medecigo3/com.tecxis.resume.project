package com.tecxis.resume;

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
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;
import com.tecxis.resume.Supplier.SupplierPK;


/**
 * The persistent class for the SUPPLIER database table.
 * 
 */
@Entity
@IdClass(SupplierPK.class)
public class Supplier implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;

	public static class SupplierPK implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@Column(name="SUPPLIER_ID")
		@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="SUPPLIER_SEQ", 
		 parameters = {
		            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
		            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
		)
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUPPLIER_SEQ")
		private long id;


		@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
		private Staff staff;
		
		public SupplierPK(Staff staff, long supplierId) {
			this();
			this.id = supplierId;
			this.staff = staff;
		}
		
		private SupplierPK() {
			super();
		}
		
		public long getId() {
			return this.id;
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

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof SupplierPK)) {
				return false;
			}
			SupplierPK castOther = (SupplierPK)other;
			return 
				this.id== castOther.id
				&& (this.staff == castOther.staff);
		}

		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + + ((int) (this.id 		^ (this.id >>> 32)));
			hash = hash * prime + ((int) (this.staff.getId() 	^ (this.staff.getId() >>> 32)));
			
			return hash;
		}
		
		@Override
		public String toString() {
			return "[" + this.getClass().getName() + 
					"[id=" + id + 
					", staffId=" + (this.getStaff() != null ? this.getStaff().getId() : " null") + "]]";
		}
	}
	
	@Id
	@Column(name="SUPPLIER_ID")
	@SequenceGenerator(name="SUPPLIER_SUPPLIERID_GENERATOR", sequenceName="SUPPLIER_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUPPLIER_SUPPLIERID_GENERATOR")
	private long id;
	
	/**
	 * bi-directional many-to-one association to Staff. 
	 * In SQL terms, Supplier is the "owner" of this relationship with Staff as it contains the relationship's foreign key
	 * In OO terms, this staff "works for" this supplier.
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
			

	/**
	 * bi-directional one-to-many association to Contract. 
	 * In SQL terms, Contract is the "owner" of this relationship with Supplier as it contains the relationship's foreign keys
	 * In OO terms, this Supplier "holds" these Contracts.
	 */
	@OneToMany(mappedBy="supplier", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Contract> contracts;

	private String name;


	public Supplier() {
		this.contracts = new ArrayList <> ();
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
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
			this.id== castOther.id
			&& (this.staff == castOther.staff);
	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + + ((int) (this.id 		^ (this.id >>> 32)));
		hash = hash * prime + ((int) (this.staff.getId() 	^ (this.staff.getId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getName() + "@" + this.hashCode() +
				"["+ Supplier.SupplierPK.class.getName()+ 
				"[id=" + id + 
				", staffId=" + (this.getStaff() != null ? this.getStaff().getId() : " null") + "]]]";
	}

}