package com.ithome.pcs.comm.entity;

/**
 * ActExTaskusercfg entity. @author MyEclipse Persistence Tools
 */

public class ActExTaskusercfg implements java.io.Serializable {

	// Fields

	private String taskusercfgid;
	private String taskkey;
	private String selectivetype;
	private String userorgroupid;

	// Constructors

	/** default constructor */
	public ActExTaskusercfg() {
	}

	/** full constructor */
	public ActExTaskusercfg(String taskusercfgid, String taskkey,
			String selectivetype, String userorgroupid) {
		this.taskusercfgid = taskusercfgid;
		this.taskkey = taskkey;
		this.selectivetype = selectivetype;
		this.userorgroupid = userorgroupid;
	}

	// Property accessors

	public String getTaskusercfgid() {
		return this.taskusercfgid;
	}

	public void setTaskusercfgid(String taskusercfgid) {
		this.taskusercfgid = taskusercfgid;
	}

	public String getTaskkey() {
		return this.taskkey;
	}

	public void setTaskkey(String taskkey) {
		this.taskkey = taskkey;
	}

	public String getSelectivetype() {
		return this.selectivetype;
	}

	public void setSelectivetype(String selectivetype) {
		this.selectivetype = selectivetype;
	}

	public String getUserorgroupid() {
		return this.userorgroupid;
	}

	public void setUserorgroupid(String userorgroupid) {
		this.userorgroupid = userorgroupid;
	}

}