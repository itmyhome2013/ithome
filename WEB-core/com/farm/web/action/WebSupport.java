package com.farm.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;
import com.farm.web.constant.FarmConstant;
import com.farm.web.spring.BeanFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WebSupport extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 通过Spring获得对象
	 * 
	 * @param beanIndex
	 * @return
	 */
	protected static Object BEAN(String beanIndex) {
		return BeanFactory.getBean(beanIndex);
	}

	/**
	 * 获得当前登录用户对象
	 * 
	 * @return
	 */
	public AloneUser getCurrentUser() {
		AloneUser user = (AloneUser) getSession().get(
				FarmConstant.SESSION_USEROBJ);
		if (user == null) {
			user = new AloneUser("noLogin", "noLogin", "noLogin", "1",
					"noLogin", "noLogin", "noLogin", "noLogin", "1", "noLogin",
					"noLogin");
			user.setId("noLogin");
		}
		return user;
	}

	/**
	 * 获得当前登录用户对象
	 * 
	 * @return
	 */
	public AloneUser getCurrentUser(HttpSession session) {
		AloneUser user = (AloneUser) session
				.getAttribute(FarmConstant.SESSION_USEROBJ);
		return user;
	}

	/**
	 * 获得当前用户的组织机构
	 * 
	 * @return
	 */
	public AloneOrganization getCurrentUserOrg() {
		return (AloneOrganization) getSession().get(FarmConstant.SESSION_ORG);

	}

	/**
	 * 设置当前用户的组织机构
	 * 
	 * @param org
	 * @return
	 */
	public AloneOrganization setCurrentUserOrg(AloneOrganization org) {
		setCurrentUserOrg(org, null);
		return org;
	}

	/**
	 * 使用httpSession设置当前用户的组织机构
	 * 
	 * @param org
	 * @return
	 */
	public AloneOrganization setCurrentUserOrg(AloneOrganization org,
			HttpSession session) {
		if (session == null) {
			getSession().put(FarmConstant.SESSION_ORG, org);
		} else {
			session.setAttribute(FarmConstant.SESSION_ORG, org);
		}
		return org;
	}

	/**
	 * 获得当前用户的角色
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AloneRolegroup> getCurrentUserRole() {
		return (List<AloneRolegroup>) getSession().get(
				FarmConstant.SESSION_ROLES);

	}

	/**
	 * 设置当前用户的角色
	 * 
	 * @param org
	 * @return
	 */
	public List<AloneRolegroup> setCurrentUserRole(List<AloneRolegroup> roles) {
		setCurrentUserRole(roles, null);
		return roles;
	}

	/**
	 * 设置当前用户的角色
	 * 
	 * @param org
	 * @return
	 */
	public List<AloneRolegroup> setCurrentUserRole(List<AloneRolegroup> roles,
			HttpSession session) {
		if (session == null) {
			getSession().put(FarmConstant.SESSION_ROLES, roles);
		} else {
			session.setAttribute(FarmConstant.SESSION_ROLES, roles);
		}
		return roles;
	}

	/**
	 * 设置当前登录用户
	 * 
	 * @param user
	 * @return
	 */
	public AloneUser setCurrentUser(AloneUser user) {
		return setCurrentUser(user, null);
	}

	/**
	 * 使用httpSession设置当前登录用户
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	public AloneUser setCurrentUser(AloneUser user, HttpSession session) {
		if (session == null) {
			getSession().put(FarmConstant.SESSION_USEROBJ, user);
		} else {
			session.setAttribute(FarmConstant.SESSION_USEROBJ, user);
		}
		String photoid = null;
		if (photoid != null && photoid.trim().length() > 0) {
			// if (session == null) {
			// getSession().put(AloneConstant.SESSION_USERPHOTO,
			// EkpFileFaceImpl.getInstance().getFileUrl(photoid));
			// } else {
			// session.setAttribute(AloneConstant.SESSION_USERPHOTO,
			// EkpFileFaceImpl.getInstance().getFileUrl(photoid));
			// }
		}
		return user;
	}

	public void setUserPhoto(String fileId) {
		// getSession().put(AloneConstant.SESSION_USERPHOTO,
		// EkpFileFaceImpl.getInstance().getFileUrl(fileId));
	}

	/**
	 * 清除当前登录用户
	 * 
	 * @param user
	 * @return
	 */
	public void clearCurrentUser() {
		getSession().put(FarmConstant.SESSION_USEROBJ, null);
	}

	/**
	 * 设置当前登录用户权限
	 * 
	 * @param user
	 * @return
	 */
	public void setCurrentUserAction(Map<String, String> userAction) {
		setCurrentUserAction(userAction, null);
	}

	/**
	 * 使用httpSession设置当前登录用户权限
	 * 
	 * @param user
	 * @return
	 */
	public void setCurrentUserAction(Map<String, String> userAction,
			HttpSession session) {
		if (session == null) {
			getSession().put(FarmConstant.SESSION_USERACTION, userAction);
		} else {
			session.setAttribute(FarmConstant.SESSION_USERACTION, userAction);
		}
	}

	/**
	 * 使用httpSession设置当前登录时间
	 * 
	 * @param user
	 * @return
	 */
	public void setLoginTime(HttpSession session) {
		if (session == null) {
			getSession().put(FarmConstant.SESSION_LOGINTIME,
					TimeTool.getTimeDate14());
		} else {
			session.setAttribute(FarmConstant.SESSION_LOGINTIME, TimeTool
					.getTimeDate14());
		}
	}

	/**
	 * 设置当前登录时间
	 */
	public void setLoginTime() {
		getSession().put(FarmConstant.SESSION_LOGINTIME,
				TimeTool.getTimeDate14());
	}

	/**
	 * 获得当前登录时间
	 */
	public String getLoginTime() {
		return (String) getSession().get(FarmConstant.SESSION_LOGINTIME);
	}

	/**
	 * 设置当前登录用户菜单
	 * 
	 * @param user
	 * @return
	 */
	public void setCurrentUserMenu(List<Map<String, Object>> userMenu) {
		setCurrentUserMenu(userMenu, null);
	}

	/**
	 * 使用httpSession设置当前登录用户菜单
	 * 
	 * @param user
	 * @return
	 */
	public void setCurrentUserMenu(List<Map<String, Object>> userMenu,
			HttpSession session) {
		if (session == null) {
			getSession().put(FarmConstant.SESSION_USERMENU, userMenu);
		} else {
			session.setAttribute(FarmConstant.SESSION_USERMENU, userMenu);
		}
	}

	public Map<String, Object> getSession() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * 如果httpsession有就返回httpsession没有就返回strutsSession
	 * 
	 * @param httpSession
	 * @return
	 */
	public HttpSession getSession(HttpSession httpSession) {
		return httpSession;
	}

	/**
	 * 获得用户ip地址
	 * 
	 * @return
	 */
	public String getCurrentIp() {
		return ServletActionContext.getRequest().getRemoteAddr();
	}

	/**
	 * 获得Response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得Response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse(HttpServletResponse httpResponse) {
		return httpResponse;
	}

	/**
	 * 获得request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 设置一个保存30天的cookie
	 * 
	 * @param cookieName
	 * @param value
	 */
	public void setCookie(String cookieName, String value) {
		HttpServletResponse response = getResponse();
		Cookie cookie = new Cookie(cookieName, value);
		int expireday = 60 * 60 * 24 * 30; // 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
		cookie.setMaxAge(expireday);
		response.addCookie(cookie);
	}

	/**
	 * 删除一个cookie
	 * 
	 * @param cookieName
	 * @param value
	 */
	public void delCookie(String cookieName) {
		if (cookieName == null || cookieName.equals("")) {
			return;
		}
		HttpServletRequest request = getRequest();
		Cookie[] cookies = request.getCookies();
		int length = 0;

		if (cookies != null && cookies.length > 0) {
			length = cookies.length;
			for (int i = 0; i < length; i++) {
				String cname = cookies[i].getName();
				if (cname != null && cname.equals(cookieName)) {
					String cValue = cookies[i].getValue();
					setCookie(cname, cValue);
				} else {
					continue;
				}
			}
		}
	}

	public String getCookieValue(String cookieName) {
		if (cookieName == null || cookieName.equals("")) {
			return null;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookies = request.getCookies();
		int length = 0;

		if (cookies != null && cookies.length > 0) {
			length = cookies.length;
			for (int i = 0; i < length; i++) {
				String cname = cookies[i].getName();
				if (cname != null && cname.equals(cookieName)) {
					String cValue = cookies[i].getValue();
					return cValue;
				} else {
					continue;
				}
			}
			return null;
		} else {
			return null;
		}
	}
}
