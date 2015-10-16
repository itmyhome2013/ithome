package com.farm.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.struts2.dispatcher.Dispatcher;

import com.farm.console.FarmManager;
import com.farm.console.contain.exception.UserNoExistException;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageType;
import com.farm.util.online.OnlineUserOpImpl;
import com.farm.util.online.OnlineUserOpInter;
import com.farm.web.adapter.AuthAdapterInter;
import com.farm.web.constant.FarmConstant;
import com.farm.web.spring.BeanFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;

/**
 * 权限资源
 * 
 * @author MAC_alone
 * 
 */
public class ActionPlatformAction extends WebSupport {
	private String name;
	private String password;
	private String autoLogin;
	private HttpSession httpSession;
	private HttpServletRequest httprequest;
	private HttpServletResponse httprespons;
	private PageSet page;
	private List<Map<String, Object>> result;
	private String menuId;
	private static final Logger log = Logger
			.getLogger(ActionPlatformAction.class);

	/**
	 * 进入登录页
	 * 
	 * @return
	 */
	public String loginPage() {
		return SUCCESS;
	}

	/**
	 * 进入首页
	 * 
	 * @return
	 */
	public String index() {
		try {
			menuId = "NONE";
			findMenu();
		} catch (Exception e) {
			PageSet.setError(page, e, e.getMessage() + "用户验证失败");
			return "FAIL";
		}
		return SUCCESS;
	}

	/**
	 * 用户退出
	 * 
	 * @return
	 */
	public String logoutCommit() {
		clearCurrentUser();
		log.info("用户退出");
		return SUCCESS;
	}

	/**
	 * 获得menu
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadMenu() {
		result = new ArrayList<Map<String, Object>>();
		Configuration configuration = Dispatcher.getInstance()
				.getConfigurationManager().getConfiguration();
		Set<String> packageConfigNames = configuration.getPackageConfigNames();
		for (String key : packageConfigNames) {
			Map<String, Object> node = new HashMap();
			PackageConfig packageConfig = configuration.getPackageConfigs()
					.get(key);
			if (packageConfig.getName().toUpperCase().indexOf(
					"ajax".toUpperCase()) < 0) {
				if (packageConfig.getName() == null) {
					break;
				}
				node.put("PACK", packageConfig.getName());
				List<String> action = new ArrayList<String>();
				for (String actionKey : packageConfig.getActionConfigs()
						.keySet()) {
					ActionConfig actionConfig = packageConfig
							.getActionConfigs().get(actionKey);
					action.add(actionConfig.getName());
				}
				node.put("ACTION", action);
			}
			result.add(node);
		}

		Collections.sort(result, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				try {
					return o1.get("PACK").toString().compareTo(
							o2.get("PACK").toString());
				} catch (Exception e) {
					return 0;
				}
			}
		});

		return SUCCESS;
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String loginCommit() throws UserNoExistException {
		AuthAdapterInter AuthAdapter = FarmManager.instance().getAuthManager();
		page = new PageSet(PageType.OTHER, CommitType.NONE);
		try {
			if (!AuthAdapter.isLegality(name, password)) {
				page.setCommitType(CommitType.FALSE.value);
				page.setMessage("密码错误");
				log.error("登录失败：密码错误");
				return "FAIL";
			} else {
			}
		} catch (Exception e) {
			page.setCommitType(CommitType.FALSE.value);
			page.setMessage(e.getMessage() + "用户验证失败");
			log.error("登录失败：用户验证失败");
			return "FAIL";
		}
		try {
			{// 登录成功
				// 开始写入session用户信息
				if (httpSession == null) {
					setCurrentUser(AuthAdapter.getEntityByLoginName(name));
					setLoginTime();
				} else {
					setCurrentUser(AuthAdapter.getEntityByLoginName(name),
							httpSession);
					setLoginTime(httpSession);
				}

				// 开始写入session用户权限
				if (httpSession == null) {
					setCurrentUserAction(AuthAdapter.getUserAction(
							getCurrentUser().getId(), password));
					setCurrentUserRole(AuthAdapter.getRoles(getCurrentUser()
							.getId()));
				} else {
					setCurrentUserAction(AuthAdapter.getUserAction(
							getCurrentUser(httpSession).getId(), password),
							httpSession);
					setCurrentUserRole(AuthAdapter.getRoles(getCurrentUser()
							.getId()), httpSession);
				}
				// 开始写入session用户菜单
				if (httpSession == null) {
					setCurrentUserMenu(AuthAdapter.getUserMenu(getCurrentUser()
							.getId(), password));
				} else {
					setCurrentUserMenu(AuthAdapter.getUserMenu(getCurrentUser(
							httpSession).getId(), password), httpSession);
				}
				// 写入Session用户组织机构
				if (httpSession == null) {
					setCurrentUserOrg(AuthAdapter
							.getMainOrgByUserId(getCurrentUser().getId()));
				} else {
					setCurrentUserOrg(AuthAdapter
							.getMainOrgByUserId(getCurrentUser(httpSession)
									.getId()), httpSession);
				}
				// 写入用户上线信息
				OnlineUserOpInter ouop = null;
				if (httpSession == null) {
					ouop = OnlineUserOpImpl.getInstance(getCurrentIp(), name,
							getSession());
				} else {
					ouop = OnlineUserOpImpl.getInstance(httprequest
							.getRemoteAddr(), name, getSession(httpSession));
				}
				ouop.userLoginHandle(AuthAdapter.getEntityByLoginName(name));
				// 记录用户登录时间
				if (httpSession == null) {
					AuthAdapter.doLogin(getCurrentUser().getId());
				} else {
					AuthAdapter.doLogin(getCurrentUser(httpSession).getId());
				}
			}
			page.setCommitType(CommitType.TRUE.value);
			MDC.put("USERID", getCurrentUser().getId());
			log.info("登录成功");
		} catch (Exception e) {
			page.setCommitType(CommitType.FALSE.value);
			page.setMessage(e + e.getMessage());
			log.error("登录失败：" + e.getMessage());
			return "FAIL";
		}
		return SUCCESS;

	}

	/**
	 * 获得menu
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findMenu() {
		List<Map<String, Object>> menuList = (List<Map<String, Object>>) getSession()
				.get(FarmConstant.SESSION_USERMENU);
		result = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> node : menuList) {
			result.add(node);
		}
		Collections.sort(result, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("SORT").toString().compareTo(
						o2.get("SORT").toString());
			}
		});
		return SUCCESS;
	}

	// ----------------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public PageSet getPage() {
		return page;
	}

	public void setPage(PageSet page) {
		this.page = page;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public String getAutoLogin() {
		return autoLogin;
	}

	public HttpServletRequest getHttprequest() {
		return httprequest;
	}

	public void setHttprequest(HttpServletRequest httprequest) {
		this.httprequest = httprequest;
	}

	public HttpServletResponse getHttprespons() {
		return httprespons;
	}

	public void setHttprespons(HttpServletResponse httprespons) {
		this.httprespons = httprespons;
	}

	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}
}
