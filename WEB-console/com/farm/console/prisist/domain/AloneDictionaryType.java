package com.farm.console.prisist.domain;

import java.math.BigDecimal;

/**
 * AloneDictionaryType entity. @author MyEclipse Persistence Tools
 */

public class AloneDictionaryType implements java.io.Serializable {

	// Fields

	private String id;
	private String ctime;
	private String utime;
	private String cuser;
	private String muser;
	private String state;
	private String name;
	private String comments;
	private String entitytype;
	private String entity;
	private BigDecimal sort;
	private String parentid;
	private String treecode;

	// Constructors

	/** default constructor */
	public AloneDictionaryType() {
	}

	/** minimal constructor */
	public AloneDictionaryType(String ctime, String utime, String cuser,
			String muser, String state, String name, String entitytype,
			String entity, BigDecimal sort) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.entitytype = entitytype;
		this.entity = entity;
		this.sort = sort;
	}

	/** full constructor */
	public AloneDictionaryType(String ctime, String utime, String cuser,
			String muser, String state, String name, String comments,
			String entitytype, String entity, BigDecimal sort) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.comments = comments;
		this.entitytype = entitytype;
		this.entity = entity;
		this.sort = sort;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getUtime() {
		return this.utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getMuser() {
		return this.muser;
	}

	public void setMuser(String muser) {
		this.muser = muser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEntitytype() {
		return this.entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public BigDecimal getSort() {
		return this.sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getTreecode() {
		return treecode;
	}

	public void setTreecode(String treecode) {
		this.treecode = treecode;
	}
}