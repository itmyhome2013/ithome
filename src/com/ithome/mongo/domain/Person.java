package com.ithome.mongo.domain;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

public class Person implements java.io.Serializable {

	// Fields
	@Id
	private String id;
	private String name; // 人员姓名
	private String mobile; // 手机号码
	private String address; //出生地址
	private String note; //备注
	
	@DBRef // mongodb的注解，文档之间建立关联关系，可以认为是关系型数据库中的外键
	@Field("book")
	private List<Book> book;

	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}


}