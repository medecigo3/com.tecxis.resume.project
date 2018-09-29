package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the ASSIGNMENT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "\"DESC\"" }))
public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="\"DESC\"")
	private String desc;

	private BigDecimal priority;

	@Id
	@SequenceGenerator(name="ASSIGNMENT_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSIGNMENT_ASSIGNMENTID_GENERATOR")
	@Column(name="ASSIGNMENT_ID")	
	private long id;

	//bi-directional many-to-one association to Project
//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID"),
//		@JoinColumn(name="NAME", referencedColumnName="NAME"),
//		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID"),
//		@JoinColumn(name="VERSION", referencedColumnName="VERSION")
//		})
//	private Project project;

	public Assignment() {
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getPriority() {
		return this.priority;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}

//	public Project getProject() {
//		return this.project;
//	}
//
//	public void setProject(Project project) {
//		this.project = project;
//	}

	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}