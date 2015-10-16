package com.farm.console.prisist.domain;

/**
 * AloneUserRolegroup entity. @author MyEclipse Persistence Tools
 */

public class AloneUserRolegroup implements java.io.Serializable {

	// Fields

	private String id;
	private String rolegroup;
	private String userid;

	// Constructors

	/** default constructor */
	public AloneUserRolegroup() {
	}

	/** minimal constructor */
	public AloneUserRolegroup(String rolegroup) {
		this.rolegroup = rolegroup;
	}

	/** full constructor */
	public AloneUserRolegroup(String rolegroup, String userid) {
		this.rolegroup = rolegroup;
		this.userid = userid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolegroup() {
		return this.rolegroup;
	}

	public void setRolegroup(String rolegroup) {
		this.rolegroup = rolegroup;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}