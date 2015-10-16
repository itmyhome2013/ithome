package com.farm.console.prisist.domain;

/**
 * AloneParameter entity. @author MyEclipse Persistence Tools
 */

public class AloneParameter implements java.io.Serializable {

	// Fields

	private String id;
	private String ctime;
	private String utime;
	private String cuser;
	private String muser;
	private String name;
	private String state;
	private String pkey;
	private String pvalue;
	private String rules;
	private String domain;
	private String comments;
	private String vtype;

	// Constructors

	/** default constructor */
	public AloneParameter() {
	}

	/** minimal constructor */
	public AloneParameter(String ctime, String utime, String cuser,
			String muser, String name, String state, String pkey,
			String pvalue, String vtype) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.name = name;
		this.state = state;
		this.pkey = pkey;
		this.pvalue = pvalue;
		this.vtype = vtype;
	}

	/** full constructor */
	public AloneParameter(String ctime, String utime, String cuser,
			String muser, String name, String state, String pkey,
			String pvalue, String rules, String domain, String comments,
			String vtype) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.name = name;
		this.state = state;
		this.pkey = pkey;
		this.pvalue = pvalue;
		this.rules = rules;
		this.domain = domain;
		this.comments = comments;
		this.vtype = vtype;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPkey() {
		return this.pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getPvalue() {
		return this.pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public String getRules() {
		return this.rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getVtype() {
		return this.vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

}