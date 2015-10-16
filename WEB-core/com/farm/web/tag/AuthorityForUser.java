package com.farm.web.tag;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.farm.web.constant.FarmConstant;

public class AuthorityForUser extends TagSupport {
	private String actionName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) super.pageContext
				.getRequest();
		ServletContext context = request.getSession().getServletContext();
		Set<String> usraction = ((Map<String, String>) request.getSession()
				.getAttribute(FarmConstant.SESSION_USERACTION)).keySet();
		// EVAL_BODY_INCLUDE
		// 则执行自定义标签的标签体；
		// 返回SKIP_BODY则忽略自定义标签的标签体，直接解释执行自定义标签的结果标记。
		String urlId = ((Map<String, String>) context
				.getAttribute(FarmConstant.CONTEXT_ALLACTION)).get(actionName);
		if (urlId == null || usraction.contains(actionName)) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}
