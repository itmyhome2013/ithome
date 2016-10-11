package com.ithome.autoform.domain;

/**
 * 任务节点权限配置
 */

public class FrmTaskUser implements java.io.Serializable {

	// Fields

	private String taskusercfgid; //主键
	private String taskkey;
	private String selectivetype;
	private String userorgroupid;
	
	private String users; //人员
	private String role; //角色
	private String group; //小组
	
	public String getTaskusercfgid() {
		return taskusercfgid;
	}
	public void setTaskusercfgid(String taskusercfgid) {
		this.taskusercfgid = taskusercfgid;
	}
	public String getTaskkey() {
		return taskkey;
	}
	public void setTaskkey(String taskkey) {
		this.taskkey = taskkey;
	}
	public String getSelectivetype() {
		return selectivetype;
	}
	public void setSelectivetype(String selectivetype) {
		this.selectivetype = selectivetype;
	}
	public String getUserorgroupid() {
		return userorgroupid;
	}
	public void setUserorgroupid(String userorgroupid) {
		this.userorgroupid = userorgroupid;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

}