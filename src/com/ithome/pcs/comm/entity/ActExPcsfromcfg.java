package com.ithome.pcs.comm.entity;

/**
 * ActExPcsfromcfg entity. @author MyEclipse Persistence Tools
 */

public class ActExPcsfromcfg implements java.io.Serializable {

	// Fields

	private String pcsfromcfgid;
	private String taskkey;
	private String formname;
	private String fromtype;
	private String isrequired;
	private String formtablename;
	private String formurl;
	private String wordurl;
	private String isdisable;
	private String taskname;

	// Constructors

	/** default constructor */
	public ActExPcsfromcfg() {
	}

	/** minimal constructor */
	public ActExPcsfromcfg(String pcsfromcfgid, String taskkey,
			String formname, String formurl, String isdisable) {
		this.pcsfromcfgid = pcsfromcfgid;
		this.taskkey = taskkey;
		this.formname = formname;
		this.formurl = formurl;
		this.isdisable = isdisable;
	}

	/** full constructor */
	public ActExPcsfromcfg(String pcsfromcfgid, String taskkey,
			String formname, String fromtype, String isrequired,
			String formtablename, String formurl,String wordurl, String isdisable,String taskname) {
		this.pcsfromcfgid = pcsfromcfgid;
		this.taskkey = taskkey;
		this.formname = formname;
		this.fromtype = fromtype;
		this.isrequired = isrequired;
		this.formtablename = formtablename;
		this.formurl = formurl;
		this.wordurl = wordurl;
		this.isdisable = isdisable;
		this.taskname=taskname;
	}

	// Property accessors

	public String getPcsfromcfgid() {
		return this.pcsfromcfgid;
	}

	public void setPcsfromcfgid(String pcsfromcfgid) {
		this.pcsfromcfgid = pcsfromcfgid;
	}

	public String getTaskkey() {
		return this.taskkey;
	}

	public void setTaskkey(String taskkey) {
		this.taskkey = taskkey;
	}

	public String getFormname() {
		return this.formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getFromtype() {
		return this.fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}

	public String getIsrequired() {
		return this.isrequired;
	}

	public void setIsrequired(String isrequired) {
		this.isrequired = isrequired;
	}

	

	public String getFormtablename() {
		return formtablename;
	}

	public void setFormtablename(String formtablename) {
		this.formtablename = formtablename;
	}

	public String getFormurl() {
		return this.formurl;
	}

	public void setFormurl(String formurl) {
		this.formurl = formurl;
	}

	public String getIsdisable() {
		return this.isdisable;
	}

	public void setIsdisable(String isdisable) {
		this.isdisable = isdisable;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getWordurl() {
		return wordurl;
	}

	public void setWordurl(String wordurl) {
		this.wordurl = wordurl;
	}
    
}