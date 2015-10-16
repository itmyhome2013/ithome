package com.farm.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.farm.core.config.AppConfig;


/**
 * 显示系统名称
 * 
 * @author Administrator
 * 
 */
public class SysTitle extends TagSupport {

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
			jspw.print(AppConfig.getString("config.sys.title"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
