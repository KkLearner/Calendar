package com.gxl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lala")
public class Lala implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="testId")
	private Integer testId;
	
	@Column(name="context")
	private String	context;

	public Lala(Integer id, Integer testId, String context) {
		super();
		this.id = id;
		this.testId = testId;
		this.context = context;
	}

	public Lala(Integer testId, String context) {
		super();
		this.testId = testId;
		this.context = context;
	}

	public Lala() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	

}
