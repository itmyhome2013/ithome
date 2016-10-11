package com.farm.web.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.farm.console.FarmManager;
import com.farm.web.spring.BeanFactory;

public class TableOptionTaget extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String isTextValue;
	private String table;
	private String fieldKey;
	private String fieldName;
	private String title; //备用字段

	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		List<Map<String, Object>> list = null;
		list = FarmManager.instance().findOptionFromTable(table,fieldKey,fieldName,title);
		JspWriter jspw = this.pageContext.getOut();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, String> type = (Map<String, String>) iterator.next();
			try {
				if (isTextValue.trim().equals("1")) {
					jspw.println("<option value='" + type.get("NAME") + "'>"
							+ type.get("NAME") + "</option>");
				} else {
					jspw.println("<option value='" + type.get("ID")
							+ "' title='" +type.get("TITLE")+ "'>" + type.get("NAME") + "</option>");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return 0;
	}

	@Override
	public int doStartTag() throws JspException {

		return 0;
	}


	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getIsTextValue() {
		return isTextValue;
	}

	public void setIsTextValue(String isTextValue) {
		this.isTextValue = isTextValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
