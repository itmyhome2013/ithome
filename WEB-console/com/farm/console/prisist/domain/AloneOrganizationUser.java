package com.farm.console.prisist.domain;

/**
 * AloneOrganizationUser entity. @author MyEclipse Persistence Tools
 */

public class AloneOrganizationUser implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private String organizationid;
	private String type;

	// Constructors

	/** default constructor */
	public AloneOrganizationUser() {
	}

	/** full constructor */
	public AloneOrganizationUser(String userid, String organizationid,
			String type) {
		this.userid = userid;
		this.organizationid = organizationid;
		this.type = type;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}