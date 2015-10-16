package com.farm.console.prisist.domain;

/**
 * AloneApplog entity. @author MyEclipse Persistence Tools
 */

public class AloneApplog implements java.io.Serializable {

	// Fields

	private String id;
	private String ctime;
	private String describes;
	private String appuser;
	private String level;
	private String method;
	private String classname;
	private String ip;

	// Constructors

	/** default constructor */
	public AloneApplog() {
	}

	/** minimal constructor */
	public AloneApplog(String ctime, String describes, String appuser) {
		this.ctime = ctime;
		this.describes = describes;
		this.appuser = appuser;
	}

	/** full constructor */
	public AloneApplog(String ctime, String describes, String appuser,
			String level, String method, String classname, String ip) {
		this.ctime = ctime;
		this.describes = describes;
		this.appuser = appuser;
		this.level = level;
		this.method = method;
		this.classname = classname;
		this.ip = ip;
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

	public String getDescribes() {
		return this.describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getAppuser() {
		return this.appuser;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}