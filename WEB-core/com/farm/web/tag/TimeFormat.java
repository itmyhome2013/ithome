package com.farm.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.farm.core.time.TimeTool;

public class TimeFormat extends TagSupport {
	private String date;
	private String yyyyMMddHHmmss;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter jspw = this.pageContext.getOut();
		try {
			jspw.print(TimeTool.getFormatTimeDate12(date, yyyyMMddHHmmss));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getYyyyMMddHHmmss() {
		return yyyyMMddHHmmss;
	}

	public void setYyyyMMddHHmmss(String yyyyMMddHHmmss) {
		this.yyyyMMddHHmmss = yyyyMMddHHmmss;
	}

}
