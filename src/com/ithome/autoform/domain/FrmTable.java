package com.ithome.autoform.domain;

/**
 * FrmTable entity. @author MyEclipse Persistence Tools
 */

public class FrmTable implements java.io.Serializable {

	// Fields

	private String id;
	private String enname;   //英文名
	private String cnname;   //中文名
	private String state;    //状态
	private String type;     //类型
	private String url;
	private String sort;     //排序
	private String owner;
	private String flag;
	private String cretime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getCnname() {
		return cnname;
	}
	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCretime() {
		return cretime;
	}
	public void setCretime(String cretime) {
		this.cretime = cretime;
	}
	
	
}