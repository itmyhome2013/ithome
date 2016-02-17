package com.ithome.autoform.domain;

/**
 * FrmFieldRel entity. @author MyEclipse Persistence Tools
 */

public class FrmFieldRel implements java.io.Serializable {

	// Fields

	private String id;
	private String fieldid;
	private String fieldname;
	private String tablename;
	private String condition;

	// Constructors

	/** default constructor */
	public FrmFieldRel() {
	}

	/** minimal constructor */
	public FrmFieldRel(String id) {
		this.id = id;
	}

	/** full constructor */
	public FrmFieldRel(String id, String fieldid, String fieldname,
			String tablename, String condition) {
		this.id = id;
		this.fieldid = fieldid;
		this.fieldname = fieldname;
		this.tablename = tablename;
		this.condition = condition;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldid() {
		return this.fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}