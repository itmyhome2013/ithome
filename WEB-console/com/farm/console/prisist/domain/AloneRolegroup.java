package com.farm.console.prisist.domain;

/**
 * AloneRolegroup entity. @author MyEclipse Persistence Tools
 */

public class AloneRolegroup implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String comments;
	private String ctime;
	private String utime;
	private String cuser;
	private String muser;
	private String state;

	// Constructors

	/** default constructor */
	public AloneRolegroup() {
	}

	/** minimal constructor */
	public AloneRolegroup(String name, String ctime, String utime,
			String cuser, String muser, String state) {
		this.name = name;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
	}

	/** full constructor */
	public AloneRolegroup(String name, String comments, String ctime,
			String utime, String cuser, String muser, String state) {
		this.name = name;
		this.comments = comments;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

}