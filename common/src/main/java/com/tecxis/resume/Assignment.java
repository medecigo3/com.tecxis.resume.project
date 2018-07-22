package com.tecxis.resume;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


/**
 * The persistent class for the ASSIGNMENT database table.
 * 
 */
@Entity
public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AssignmentPK id;

	@Column(name="\"DESC\"")
	private String desc;

	private BigDecimal priority;

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

	public AssignmentPK getId() {
		return this.id;
	}

	public void setId(AssignmentPK id) {
		this.id = id;
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

}