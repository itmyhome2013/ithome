package org.bkgd.ehome.util.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	/**
	 * 将用户sessionID存入Cookie中并将session存入公共上下文中
	 * @param response HttpServletResponse
	 * @param request HttpServletRequest
	 * @param userId 用户ID
	 */
	public static void wirteCookieUserId(HttpServletResponse response,
			HttpServletRequest request, String userId) {
		EhomeAuthToolInter ei = new EhomeAuthToolIMPL(request.getRemoteAddr());
		Cookie ck = new Cookie("AUTHINDEX", ei.enCode(userId));
		ck.setPath("/");
		response.addCookie(ck);
	}

	/**将用户sessionID从Cookie中去除(注销用户)
	 * @param response HttpServletResponse
	 */
	public static void clearCookieUserId(HttpServletResponse response) {
		Cookie ck = new Cookie("AUTHINDEX", null);
		ck.setPath("/");
		response.addCookie(ck);
	}
}
