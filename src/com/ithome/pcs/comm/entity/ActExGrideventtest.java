package com.ithome.pcs.comm.entity;

/**
 * ActExGrideventtest entity. @author MyEclipse Persistence Tools
 */

public class ActExGrideventtest implements java.io.Serializable {

	// Fields

	private String grideventid;
	private String processid;
	private String grideventname;
	private String happendate;

	// Constructors

	/** default constructor */
	public ActExGrideventtest() {
	}

	/** minimal constructor */
	public ActExGrideventtest(String grideventid) {
		this.grideventid = grideventid;
	}

	/** full constructor */
	public ActExGrideventtest(String grideventid, String processid,
			String grideventname, String happendate) {
		this.grideventid = grideventid;
		this.processid = processid;
		this.grideventname = grideventname;
		this.happendate = happendate;
	}

	// Property accessors

	public String getGrideventid() {
		return this.grideventid;
	}

	public void setGrideventid(String grideventid) {
		this.grideventid = grideventid;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getGrideventname() {
		return this.grideventname;
	}

	public void setGrideventname(String grideventname) {
		this.grideventname = grideventname;
	}

	public String getHappendate() {
		return this.happendate;
	}

	public void setHappendate(String happendate) {
		this.happendate = happendate;
	}

}