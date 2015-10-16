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

public class DictionaryOptionTaget extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String isTextValue;
	private String index;

	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		List<Map<String, Object>> list = null;
		list = FarmManager.instance().findDicTitleForIndeHasSort(index);
		JspWriter jspw = this.pageContext.getOut();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, String> type = (Map<String, String>) iterator.next();
			try {
				if (isTextValue.trim().equals("1")) {
					jspw.println("<option value='" + type.get("NAME") + "'>"
							+ type.get("NAME") + "</option>");
				} else {
					jspw.println("<option value='" + type.get("ENTITYTYPE")
							+ "'>" + type.get("NAME") + "</option>");
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIsTextValue() {
		return isTextValue;
	}

	public void setIsTextValue(String isTextValue) {
		this.isTextValue = isTextValue;
	}
}
