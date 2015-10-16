package com.farm.console.prisist.domain;

/**
 * AloneAction entity. @author MyEclipse Persistence Tools
 */

public class AloneAction implements java.io.Serializable {

	// Fields

	private String id;
	private String url;
	private String name;
	private String comments;
	private String type;
	private String ctime;
	private String utime;
	private String cuser;
	private String muser;
	private String state;
	private String action;
	private String ischeck;

	// Constructors

	/** default constructor */
	public AloneAction() {
	}

	/** minimal constructor */
	public AloneAction(String url, String name, String ctime, String utime,
			String cuser, String muser, String state, String ischeck) {
		this.url = url;
		this.name = name;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.ischeck = ischeck;
	}

	/** full constructor */
	public AloneAction(String url, String name, String comments, String type,
			String ctime, String utime, String cuser, String muser,
			String state, String action, String ischeck) {
		this.url = url;
		this.name = name;
		this.comments = comments;
		this.type = type;
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.action = action;
		this.ischeck = ischeck;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

}