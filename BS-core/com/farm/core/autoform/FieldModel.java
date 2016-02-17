package com.farm.core.autoform;

import java.io.Serializable;

public class FieldModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 字段名
	 */
	private String fieldName;

	/**
	 * 字段类型
	 */
	private String fieldType;

	/**
	 * 字段长度
	 */
	private String fieldLen;

	/**
	 * 是否为空
	 */
	private String isNull;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * @param tableName  表名
	 * @param fieldName  字段名
	 * @param fieldType  字段类型
	 * @param fieldLen   字段长度
	 * @param isNull     是否为空
	 */
	public FieldModel(String tableName, String fieldName, String fieldType,
			String fieldLen,String isNull) {
		super();
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldLen = fieldLen;
		this.isNull = isNull;
	}
	
	public FieldModel(String tableName, String fieldName) {
		super();
		this.tableName = tableName;
		this.fieldName = fieldName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldLen() {
		return fieldLen;
	}

	public void setFieldLen(String fieldLen) {
		this.fieldLen = fieldLen;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
