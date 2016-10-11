package com.ithome.autoform.domain;

/**
 * 流程表单配置
 */

public class FrmProcessForm implements java.io.Serializable {

	// Fields

	private String pcsfromcfgid; //主键
	private String taskkey; // 任务节点KEY
	private String formname; // 表单名称
	private String fromtype; // 表单类型（0：url,1：实体 2:Word文件）
	private String isrequired; // 是否必填(0:是，1：否)
	private String formtablename; //表单对应的表名
	private String formurl; // 表单URL
	private String wordurl; // word路径
	private String word;   //文档名称
	private String isdisable; //是否禁用
	private String taskname; //任务节点名称
	private String formtableid;  //表单ID

	public String getPcsfromcfgid() {
		return pcsfromcfgid;
	}

	public void setPcsfromcfgid(String pcsfromcfgid) {
		this.pcsfromcfgid = pcsfromcfgid;
	}

	public String getTaskkey() {
		return taskkey;
	}

	public void setTaskkey(String taskkey) {
		this.taskkey = taskkey;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getFromtype() {
		return fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}

	public String getIsrequired() {
		return isrequired;
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
		return formurl;
	}

	public void setFormurl(String formurl) {
		this.formurl = formurl;
	}

	public String getIsdisable() {
		return isdisable;
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

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getFormtableid() {
		return formtableid;
	}

	public void setFormtableid(String formtableid) {
		this.formtableid = formtableid;
	}
	

}