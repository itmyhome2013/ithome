package com.ithome.autoform.domain;

import java.math.BigDecimal;

/**
 * FrmSqu entity. @author MyEclipse Persistence Tools
 */

public class FrmSqu implements java.io.Serializable {

	// Fields

	private String id;
	private String type;
	private BigDecimal num;

	// Constructors

	/** default constructor */
	public FrmSqu() {
	}

	/** minimal constructor */
	public FrmSqu(String id) {
		this.id = id;
	}

	/** full constructor */
	public FrmSqu(String id, String type, BigDecimal num) {
		this.id = id;
		this.type = type;
		this.num = num;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getNum() {
		return this.num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

}