package com.ithome.autoform.domain;

/**
 * FrmField entity. @author MyEclipse Persistence Tools
 */

public class FrmField implements java.io.Serializable {

	// Fields

	private String id;
	private String tableid;  //表单ID
	private String cnname;   //中文名 
	private String enname;   //英文名
	private String filedtype;//字段类型
	private String labeltype;//标签类型
	private String len;      //字段长度
	private String sort;     //排序
	private String inputtype;  //type类型
	private String state;    //状态
	private String cretime;  //创建时间
	private String updtime;  //更新时间
	private String isrel;
	private String isnull;   //是否为空
	private String isrequired;  //是否必填
	private String note; //注释
	private String validaInfo; //验证信息
	private String constant; //数据字典常量值

	// Constructors

	/** default constructor */
	public FrmField() {
	}

	/** minimal constructor */
	public FrmField(String id) {
		this.id = id;
	}

	/** full constructor */
	public FrmField(String id, String tableid, String cnname, String enname,
			String filedtype, String len, String sort, String inputtype,
			String state, String cretime, String updtime, String isrel,
			String isnull) {
		this.id = id;
		this.tableid = tableid;
		this.cnname = cnname;
		this.enname = enname;
		this.filedtype = filedtype;
		this.len = len;
		this.sort = sort;
		this.inputtype = inputtype;
		this.state = state;
		this.cretime = cretime;
		this.updtime = updtime;
		this.isrel = isrel;
		this.isnull = isnull;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableid() {
		return this.tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getCnname() {
		return this.cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getEnname() {
		return this.enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getFiledtype() {
		return this.filedtype;
	}

	public void setFiledtype(String filedtype) {
		this.filedtype = filedtype;
	}

	public String getLen() {
		return this.len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getInputtype() {
		return this.inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCretime() {
		return this.cretime;
	}

	public void setCretime(String cretime) {
		this.cretime = cretime;
	}

	public String getUpdtime() {
		return this.updtime;
	}

	public void setUpdtime(String updtime) {
		this.updtime = updtime;
	}

	public String getIsrel() {
		return this.isrel;
	}

	public void setIsrel(String isrel) {
		this.isrel = isrel;
	}

	public String getIsnull() {
		return this.isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getLabeltype() {
		return labeltype;
	}

	public void setLabeltype(String labeltype) {
		this.labeltype = labeltype;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIsrequired() {
		return isrequired;
	}

	public void setIsrequired(String isrequired) {
		this.isrequired = isrequired;
	}

	public String getValidaInfo() {
		return validaInfo;
	}

	public void setValidaInfo(String validaInfo) {
		this.validaInfo = validaInfo;
	}

	public String getConstant() {
		return constant;
	}

	public void setConstant(String constant) {
		this.constant = constant;
	}

}