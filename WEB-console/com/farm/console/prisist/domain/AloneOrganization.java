package com.farm.console.prisist.domain;

import java.math.BigDecimal;

/**
 * AloneOrganization entity. @author MyEclipse Persistence Tools
 */

public class AloneOrganization implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String treecode;
	private String comments;
	private String name;
	private String ctime;
	private String utime;
	private String state;
	private String cuser;
	private String muser;
	private String parentid;
	private BigDecimal sort;
	private String type;

	// Constructors

	/** default constructor */
	public AloneOrganization() {
	}

	/** minimal constructor */
	public AloneOrganization(String treecode, String name, String ctime,
			String utime, String state, String cuser, String muser, String type) {
		this.treecode = treecode;
		this.name = name;
		this.ctime = ctime;
		this.utime = utime;
		this.state = state;
		this.cuser = cuser;
		this.muser = muser;
		this.type = type;
	}

	/** full constructor */
	public AloneOrganization(String treecode, String comments, String name,
			String ctime, String utime, String state, String cuser,
			String muser, String parentid, BigDecimal sort, String type) {
		this.treecode = treecode;
		this.comments = comments;
		this.name = name;
		this.ctime = ctime;
		this.utime = utime;
		this.state = state;
		this.cuser = cuser;
		this.muser = muser;
		this.parentid = parentid;
		this.sort = sort;
		this.type = type;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTreecode() {
		return this.treecode;
	}

	public void setTreecode(String treecode) {
		this.treecode = treecode;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public BigDecimal getSort() {
		return this.sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


}