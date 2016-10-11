package com.ithome.pcs.comm.entity;

/**
 * ActExGroupuser entity. @author MyEclipse Persistence Tools
 */

public class ActExGroupuser implements java.io.Serializable {

	// Fields

	private String groupuserid;
	private String groupid;
	private String userid;

	// Constructors

	/** default constructor */
	public ActExGroupuser() {
	}

	/** minimal constructor */
	public ActExGroupuser(String groupuserid, String groupid) {
		this.groupuserid = groupuserid;
		this.groupid = groupid;
	}

	/** full constructor */
	public ActExGroupuser(String groupuserid, String groupid, String userid) {
		this.groupuserid = groupuserid;
		this.groupid = groupid;
		this.userid = userid;
	}

	// Property accessors

	public String getGroupuserid() {
		return this.groupuserid;
	}

	public void setGroupuserid(String groupuserid) {
		this.groupuserid = groupuserid;
	}

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}