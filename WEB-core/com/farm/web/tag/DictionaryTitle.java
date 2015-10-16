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

public class DictionaryTitle extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String type;
	private String index;

	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		List<Map<String, Object>> list = null;
		list = FarmManager.instance().findDicTitleForIndeHasSort(index);
		JspWriter jspw = this.pageContext.getOut();
		try {
			for (Map<String, Object> node : list) {
				if (node.get("ENTITYTYPE").equals(type)) {

					jspw.println(node.get("NAME"));
					return 0;

				}
			}
			jspw.println(type);
		} catch (IOException e) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
