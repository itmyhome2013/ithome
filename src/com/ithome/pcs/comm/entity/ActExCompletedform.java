package com.ithome.pcs.comm.entity;

/**
 * ActExCompletedform entity. @author MyEclipse Persistence Tools
 */

public class ActExCompletedform implements java.io.Serializable {

	// Fields

	private String completedformid;
	private String processid;
	private String pcsfromcfgid;
	private String informant;
	private String infordate;
	private String dataid;
	private String formtablename;
	private String path; // 文档路径
	private String timestamp;

	// Constructors

	/** default constructor */
	public ActExCompletedform() {
	}

	/** minimal constructor */
	public ActExCompletedform(String completedformid) {
		this.completedformid = completedformid;
	}

	/** full constructor */
	public ActExCompletedform(String completedformid, String processid,
			String pcsfromcfgid,String informant, String infordate, String dataid,String formtablename) {
		this.completedformid = completedformid;
		this.processid = processid;
		this.pcsfromcfgid = pcsfromcfgid;
		this.informant = informant;
		this.infordate = infordate;
		this.dataid = dataid;
		this.formtablename=formtablename;
	}

	// Property accessors

	public String getCompletedformid() {
		return this.completedformid;
	}

	public void setCompletedformid(String completedformid) {
		this.completedformid = completedformid;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getPcsfromcfgid() {
		return this.pcsfromcfgid;
	}

	public void setPcsfromcfgid(String pcsfromcfgid) {
		this.pcsfromcfgid = pcsfromcfgid;
	}

	public String getInformant() {
		return this.informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getInfordate() {
		return this.infordate;
	}

	public void setInfordate(String infordate) {
		this.infordate = infordate;
	}

	public String getDataid() {
		return this.dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getFormtablename() {
		return formtablename;
	}

	public void setFormtablename(String formtablename) {
		this.formtablename = formtablename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	

}