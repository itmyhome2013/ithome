package com.ithome.autoform.domain;

/**
 * 已填业务表单
 *
 */
public class CompletedForm {
	private String processid; // 外键（ACT_EX_PROCESS）
	private String pcsfromcfgid; // 外键（ACT_EX_PCSFROMCFG）
	private String informant; // 填报人
	private String fromtablename; // 表单对应的表名

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

	public String getFromtablename() {
		return fromtablename;
	}

	public void setFromtablename(String fromtablename) {
		this.fromtablename = fromtablename;
	}

}
