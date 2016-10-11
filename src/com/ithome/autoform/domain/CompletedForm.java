package com.ithome.autoform.domain;

/**
 * 已填业务表单
 *
 */
public class CompletedForm {
	private String completedformid;
	private String dataid;
	private String processid; // 外键（ACT_EX_PROCESS）test
	private String pcsfromcfgid; // 外键（ACT_EX_PCSFROMCFG）
	private String informant; // 填报人
	private String formtablename; // 表单对应的表名

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getPcsfromcfgid() {
		return pcsfromcfgid;
	}

	public void setPcsfromcfgid(String pcsfromcfgid) {
		this.pcsfromcfgid = pcsfromcfgid;
	}

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getFormtablename() {
		return formtablename;
	}

	public void setFormtablename(String formtablename) {
		this.formtablename = formtablename;
	}

	public String getCompletedformid() {
		return completedformid;
	}

	public void setCompletedformid(String completedformid) {
		this.completedformid = completedformid;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	

}
