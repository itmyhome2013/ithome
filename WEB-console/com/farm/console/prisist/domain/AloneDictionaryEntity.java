package com.farm.console.prisist.domain;

/**
 * AloneDictionaryEntity entity. @author MyEclipse Persistence Tools
 */

public class AloneDictionaryEntity implements java.io.Serializable {

	// Fields

	private String id;
	private String ctime;
	private String utime;
	private String cuser;
	private String muser;
	private String state;
	private String name;
	private String entityindex;
	private String comments;
	private String type;

	// Constructors

	/** default constructor */
	public AloneDictionaryEntity() {
	}

	/** minimal constructor */
	public AloneDictionaryEntity(String ctime, String utime, String cuser,
			String muser, String state, String name, String entityindex) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.entityindex = entityindex;
	}

	/** full constructor */
	public AloneDictionaryEntity(String ctime, String utime, String cuser,
			String muser, String state, String name, String entityindex,
			String comments) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.entityindex = entityindex;
		this.comments = comments;
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

	public String getEntityindex() {
		return this.entityindex;
	}

	public void setEntityindex(String entityindex) {
		this.entityindex = entityindex;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}