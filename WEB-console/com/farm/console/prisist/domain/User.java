package com.farm.console.prisist.domain;

public class User
{
  private String id;
  private String name;
  private String password;
  private String comments;
  private String type;
  private String ctime;
  private String utime;
  private String cuser;
  private String muser;
  private String state;
  private String loginname;
  private String logintime;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getComments() {
    return this.comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCtime() {
    return this.ctime;
  }

  public void setCtime(String ctime) {
    this.ctime = ctime;
  }

  public String getUtime() {
    return this.utime;
  }

  public void setUtime(String utime) {
    this.utime = utime;
  }

  public String getCuser() {
    return this.cuser;
  }

  public void setCuser(String cuser) {
    this.cuser = cuser;
  }

  public String getMuser() {
    return this.muser;
  }

  public void setMuser(String muser) {
    this.muser = muser;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getLoginname() {
    return this.loginname;
  }

  public void setLoginname(String loginname) {
    this.loginname = loginname;
  }

  public String getLogintime() {
    return this.logintime;
  }

  public void setLogintime(String logintime) {
    this.logintime = logintime;
  }
}