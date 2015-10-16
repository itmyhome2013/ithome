package com.farm.web.filter;

import java.io.IOException;
import java.util.Map;

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
import com.farm.util.online.OnlineUserOpImpl;
import com.farm.util.online.OnlineUserOpInter;
import com.farm.web.constant.FarmConstant;
import com.farm.web.filter.auth.ConstanceEHP;

/**
 * 
 * @author WangDong
 * @date Mar 14, 2010
 * 
 */
public class FilterValidate implements Filter {
	private static final Logger log = Logger.getLogger(FilterValidate.class);
	// 对URL进行验证
	private static final String is_check_url = AppConfig.getString(
			"config.auth.check.url").toUpperCase();
	// 一个用户登录多次
	private static final String is_multi_user = AppConfig.getString(
			"config.auth.multi.user").toUpperCase();

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		String path = ((HttpServletRequest) arg0).getContextPath();
		String basePath = arg0.getScheme() + "://" + arg0.getServerName() + ":"
				+ arg0.getServerPort() + path + "/";
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String requestUrl = request.getRequestURL().toString();
		String formatUrl = Urls.formatUrl(requestUrl);
		Cookie[] ck = request.getCookies();
		{// 判断是否需要用户权限认证
			String prefix = AppConfig.getString("config.url.free.path.prefix");
			if (prefix != null && formatUrl.indexOf("/" + prefix) == 0) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}

		{// 判断是否是登录即可访问
			String prefix = AppConfig.getString("config.url.login.path.prefix");
			if (prefix != null && formatUrl.indexOf("/" + prefix) == 0) {
				if (Urls.isActionByUrl(formatUrl, "do")
						|| Urls.isActionByUrl(formatUrl, "htm")) {
					AloneUser currentUser = (AloneUser) session
							.getAttribute(FarmConstant.SESSION_USEROBJ);
					if (currentUser == null) {
						response.sendRedirect(basePath
								+ AppConfig
										.getString("config.index.defaultpage"));
						return;
					} else {
						arg2.doFilter(arg0, arg1);
						return;
					}
				}

			}
		}
		{// 处理登录操作
			if (Urls.isActionByUrl(formatUrl, "do")
					|| Urls.isActionByUrl(formatUrl, "htm")) {
				// 如果是用户执行登录操作登录就放过
				if (Urls.getActionUrl(formatUrl)
						.equals("ALONEFRAME_LOGIN_PAGE")
						|| Urls.getActionUrl(formatUrl).equals(
								"ALONEFRAME_LOGIN_COMMIT")
						|| Urls.getActionUrl(formatUrl).equals(
								"ALONEFRAME_NOPOP")
						|| Urls.isNoLoginUrl(formatUrl)) {
					arg2.doFilter(arg0, arg1);
					return;
				}
			} else {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		// 处理cookie用户
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
					session.setAttribute(FarmConstant.SESSION_USEROBJ, session
							.getAttribute(ConstanceEHP.SESSION_USER_KEY));
					session.setAttribute(FarmConstant.SESSION_ORG, FarmManager
							.instance().getAuthManager().getMainOrgByUserId(
									userid));
					// 加入session详细初始化信息
				}
			}
		}
		{// 处理未登录用户---让其登录
			if (Urls.isActionByUrl(formatUrl, "do")
					|| Urls.isActionByUrl(formatUrl, "htm")) {
				AloneUser currentUser = (AloneUser) session
						.getAttribute(FarmConstant.SESSION_USEROBJ);
				if (currentUser == null) {
					response.sendRedirect(basePath
							+ AppConfig.getString("config.index.defaultpage"));
					return;
				}
			}
		}
		{// 处理--online--用户在线
			if (Urls.isActionByUrl(formatUrl, "do")
					|| Urls.isActionByUrl(formatUrl, "htm")) {
				AloneUser currentUser = (AloneUser) session
						.getAttribute(FarmConstant.SESSION_USEROBJ);
				OnlineUserOpInter ouop = OnlineUserOpImpl.getInstance(request
						.getRemoteAddr(), currentUser.getLoginname(), session);
				if (is_multi_user.equals("FALSE")) {
					ouop.userVisitHandle();
				}
			}
		}
		if (is_check_url.equals("FALSE")) {
			// 放开就是对权限不做验证
			arg2.doFilter(arg0, arg1);
			return;
		}
		{// 处理Action资源
			if (Urls.isActionByUrl(formatUrl, "do")
					|| Urls.isActionByUrl(formatUrl, "htm")) {
				if (URL_Action_handle(context, session, formatUrl)) {
					// 有权限
					arg2.doFilter(arg0, arg1);
					return;
				} else {
					// 无权限
					response.sendError(405, "当前用户没有权限请求该资源!");
					return;
				}
			}
		}
		arg2.doFilter(arg0, arg1);
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
			System.out.println("========= cook name========="+ck.getName());
		}
		return null;
	}

	/**
	 * 处理Action资源 如果是TRUE就提交请求， 是FALSE就不做处理
	 * 
	 * @param context
	 * @param formatUrl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean URL_Action_handle(ServletContext context,
			HttpSession session, String formatUrl) {
		// 如果是.do资源就检查是否有权限
		String actionIndex = Urls.getActionUrl(formatUrl);

		String urlId = ((Map<String, String>) context
				.getAttribute(FarmConstant.CONTEXT_ALLACTION)).get(actionIndex);
		if (urlId != null) {
			// 该权限被控制访问if(是否用户拥护权限)有：通过，没有：返回登录页
			Map<String, String> usraction = (Map<String, String>) session
					.getAttribute(FarmConstant.SESSION_USERACTION);
			if (usraction.get(actionIndex) != null) {
				log.debug("server_receive_URL: TRUE:" + actionIndex);
				return true;
			}
		} else {
			// log.info("server_receive_URL: TRUE:" + actionIndex);
			return true;
		}
		log.debug("server_receive_URL:FALSE:" + actionIndex);
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