package com.farm.console.prisist.domain;

/**
 * AloneMenuRolegroup entity. @author MyEclipse Persistence Tools
 */

public class AloneMenuRolegroup implements java.io.Serializable {

	// Fields

	private String id;
	private String rolegroupid;
	private String menuid;

	// Constructors

	/** default constructor */
	public AloneMenuRolegroup() {
	}

	/** full constructor */
	public AloneMenuRolegroup(String rolegroupid, String menuid) {
		this.rolegroupid = rolegroupid;
		this.menuid = menuid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolegroupid() {
		return this.rolegroupid;
	}

	public void setRolegroupid(String rolegroupid) {
		this.rolegroupid = rolegroupid;
	}

	public String getMenuid() {
		return this.menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

}