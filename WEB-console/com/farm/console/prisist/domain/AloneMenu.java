package com.farm.console.prisist.domain;

import java.math.BigDecimal;

/**
 * AloneMenu entity. @author MyEclipse Persistence Tools
 */

public class AloneMenu implements java.io.Serializable {

	// Fields

	private String id;
	private BigDecimal sort;
	private String parentid;
	private String name;
	private String treecode;
	private String comments;
	private String type;
	private String ctime;
	private String utime;
	private String cuser;
	private String uuser;
	private String state;
	private String action;
	private String domain;
	private String img;

	// Constructors

	/** default constructor */
	public AloneMenu() {
	}

	/** minimal constructor */
	public AloneMenu(String name, String treecode, String ctime, String utime,
			String cuser, String uuser, String state, String domain) {
		this.name = name;
		this.treecode = treecode;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.uuser = uuser;
		this.state = state;
		this.domain = domain;
	}

	/** full constructor */
	public AloneMenu(BigDecimal sort, String parentid, String name,
			String treecode, String comments, String type, String ctime,
			String utime, String cuser, String uuser, String state,
			String action, String domain, String img) {
		this.sort = sort;
		this.parentid = parentid;
		this.name = name;
		this.treecode = treecode;
		this.comments = comments;
		this.type = type;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.uuser = uuser;
		this.state = state;
		this.action = action;
		this.domain = domain;
		this.img = img;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getSort() {
		return this.sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}