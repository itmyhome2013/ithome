package com.farm.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.bkgd.ehome.util.auth.EhomeAuthToolIMPL;

import com.farm.console.FarmManager;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.auth.Urls;
import com.farm.core.config.AppConfig;
import com.farm.web.filter.auth.ConstanceEHP;

/**
 * 
 * @author WangDong
 * @date Mar 14, 2010
 * 
 */
public class FilterValidateForCookie implements Filter {
	private static final Logger log = Logger.getLogger(FilterValidate.class);

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		Cookie[] ck = request.getCookies();
		String path = ((HttpServletRequest) arg0).getContextPath();
		String basePath = arg0.getScheme() + "://" + arg0.getServerName() + ":"
				+ arg0.getServerPort() + path + "/";
		// 如果cookie中没有权限信息则清空当前权限信息
		if (ck != null) {
			String useridCode = getCookieValue(ConstanceEHP.COOKIE_USERID_KEY,
					ck);
			if (useridCode == null || useridCode.trim().length() <= 0) {
				session.removeAttribute(ConstanceEHP.SESSION_AUTH_KEY);
				session.removeAttribute(ConstanceEHP.SESSION_USER_KEY);
			}
		}
		if (ck != null) {
			String useridCode = getCookieValue(ConstanceEHP.COOKIE_USERID_KEY,
					ck);
			if (useridCode != null && useridCode.trim().length() > 0) {
				String userid = new EhomeAuthToolIMPL(request.getRemoteAddr())
						.deCode(useridCode);
				// 判断如果本session和用户session一致则不作处理
				String severUserId = (String) session
						.getAttribute(ConstanceEHP.SESSION_AUTH_KEY);
				if ((severUserId == null && userid != null)
						|| (severUserId != null && !severUserId.equals(userid))) {
					session.setAttribute(ConstanceEHP.SESSION_AUTH_KEY, userid);
					session.setAttribute(ConstanceEHP.SESSION_USER_KEY,
							FarmManager.instance().getAuthManager()
									.getUserById(userid));
					// 加入session详细初始化信息
				}

			} else {

				((HttpServletResponse) arg1).sendRedirect(basePath
						+ AppConfig.getString("config.index.defaultpage"));
				return;
			}
		}
		// 自由访问
		if (URL_Free_handle(context, session, Urls.formatUrl(request
				.getRequestURL().toString()))) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		// 受检查权限后台管理员功能
		if (URL_Action_handle(context, session, Urls.formatUrl(request
				.getRequestURL().toString()))) {
			// 判断用户是否登录&&是否是管理员
			AloneUser user = (AloneUser) session
					.getAttribute(ConstanceEHP.SESSION_USER_KEY);
			arg2.doFilter(arg0, arg1);
			return;
		}
		// 受检查权限登录用户功能
		if (URL_Logined_handle(context, session, Urls.formatUrl(request
				.getRequestURL().toString()))) {
			// 判断用户是否登录&&是否是管理员
			AloneUser user = (AloneUser) session
					.getAttribute(ConstanceEHP.SESSION_USER_KEY);
			if (user == null) {
				// 不是的话提示没有权限
				// ((HttpServletResponse) arg1).sendError(406, "请登录后访问该资源!");
				((HttpServletResponse) arg1).sendRedirect(basePath
						+ AppConfig.getString("config.index.defaultpage"));
				return;
			}
			arg2.doFilter(arg0, arg1);
			return;
		}

		((HttpServletResponse) arg1).sendError(407, "该页面非法!");
		return;
	}

	/**
	 * 从Cookies数组中获得某个匹配上KEY的Cookie值
	 * 
	 * @param key
	 * @param cookies
	 * @return
	 */
	private String getCookieValue(String key, Cookie[] cookies) {
		for (Cookie ck : cookies) {
			if (ck.getName().equals(key)) {
				return ck.getValue();
			}
		}
		return null;
	}

	private boolean URL_Action_handle(ServletContext context,
			HttpSession session, String formatUrl) {
		// 如果是.do资源就检查是否有权限
		// String actionIndex = Urls.getActionUrl(formatUrl);
		if (formatUrl.indexOf("/Hh") >= 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean URL_Logined_handle(ServletContext context,
			HttpSession session, String formatUrl) {
		// 如果是.do资源就检查是否有权限
		// String actionIndex = Urls.getActionUrl(formatUrl);
		if (formatUrl.indexOf("/User") >= 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean URL_Free_handle(ServletContext context,
			HttpSession session, String formatUrl) {
		// 如果是.do资源就检查是否有权限
		// String actionIndex = Urls.getActionUrl(formatUrl);
		if (formatUrl.indexOf("/Free") >= 0) {
			return true;
		}
		if (formatUrl.indexOf(".do") < 0) {
			return true;
		}
		return false;
	}

	// -----------------------------------------------------------
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}