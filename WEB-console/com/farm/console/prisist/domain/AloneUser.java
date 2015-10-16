package com.farm.console.prisist.domain;


/**
 * AloneUser entity. @author MyEclipse Persistence Tools
 */

public class AloneUser extends User implements java.io.Serializable  {

	// Fields

	private String id;
	private String name;
	private String password;
	private String comments;
	private String type;
	private String ctime;
	private String utime;
	private String cuser;
	private String muser;
	private String state;
	private String loginname;
	private String logintime;

	// Constructors

	/** default constructor */
	public AloneUser() {
	}

	/** minimal constructor */
	public AloneUser(String name, String password, String ctime, String utime,
			String cuser, String muser, String state, String loginname) {
		this.name = name;
		this.password = password;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.loginname = loginname;
	}

	/** full constructor */
	public AloneUser(String name, String password, String comments,
			String type, String ctime, String utime, String cuser,
			String muser, String state, String loginname, String logintime) {
		this.name = name;
		this.password = password;
		this.comments = comments;
		this.type = type;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.loginname = loginname;
		this.logintime = logintime;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLogintime() {
		return this.logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

}