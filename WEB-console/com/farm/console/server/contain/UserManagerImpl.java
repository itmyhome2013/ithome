package com.farm.console.server.contain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.farm.console.FarmManager;
import com.farm.console.contain.exception.UserLoginNameIsExistException;
import com.farm.console.contain.exception.UserNoExistException;
import com.farm.console.prisist.dao.AloneUserRolegroupDaoInter;
import com.farm.console.prisist.dao.OrganizationDaoInter;
import com.farm.console.prisist.dao.OrganizationUserDaoInter;
import com.farm.console.prisist.dao.UserDaoInter;
import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneOrganizationUser;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.auth.AuthenticateProvider;
import com.farm.core.time.TimeTool;
import com.farm.core.web.ParameterUtils;
import com.farm.core.web.PinyinTool;
import com.farm.web.constant.FarmConstant;

public class UserManagerImpl implements UserManagerInter {
	private UserDaoInter userDao;

	private AuthenticateProvider authprovider;
	private OrganizationUserDaoInter organizationuserDao;
	private OrganizationDaoInter organizationDao;
	private AloneUserRolegroupDaoInter aloneUserRolegroupDao;

	public void deleteEntity(String id) {
		// 删除组织
		organizationuserDao.deleteAllEntity(userDao.getEntity(id));
		// 删除用户
		userDao.deleteEntity(userDao.getEntity(id));
	}

	public int getAllListNum() {
		return userDao.getAllListNum();
	}

	public UserDaoInter getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoInter userDao) {
		this.userDao = userDao;
	}

	public void editEntity(AloneUser entity) {
		AloneUser usertemp = userDao.getEntity(entity.getId());
		usertemp.setComments(entity.getComments());
		usertemp.setName(entity.getName());
		usertemp.setState(entity.getState());
		userDao.editEntity(usertemp);
	}

	public boolean editPassword(String id, String oldPassword,
			String newpassword) {
		AloneUser user = userDao.getEntity(id);
		try {
			if (isLegality(user.getLoginname(), oldPassword)) {
				newpassword = authprovider.encodeMd5(newpassword);
				user.setPassword(newpassword);
				userDao.editEntity(user);
				return true;
			}
		} catch (UserNoExistException e) {
			return false;
		}
		return false;
	}

	public String getPassword(String id) {
		AloneUser aloneuser = this.getEntity(id);
		String password = userDao.getPasswordByLoginName(
				aloneuser.getLoginname()).getPassword();
		return password;
	}

	public boolean isLegality(String loginname, String password)
			throws UserNoExistException {
		String enPassword = "";
		if (password.length() == 32) {
			enPassword = password.toUpperCase();
		} else {
			enPassword = authprovider.encodeMd5(password);
		}
		String dataPassword = null;
		AloneUser user = userDao.getPasswordByLoginName(loginname);
		if (user != null && user.getState().equals("1")) {
			dataPassword = user.getPassword();
		} else {
			throw new UserNoExistException("");
		}
		return (enPassword.equals(dataPassword));
	}

	public boolean initPassword(String id) {
		AloneUser aloneuser = userDao.getEntity(id);
		FarmManager abImpl = FarmManager.instance();
		aloneuser.setPassword(abImpl.getAuthUtil().encodeMd5(
				abImpl.getConfigValue(FarmConstant.PARA_DEFULT_PASSWORD)));
		userDao.editEntity(aloneuser);
		return true;
	}

	public List<AloneUser> getAllEntity() {
		return userDao.getAllUser();
	}

	public AloneUser getEntity(String id) {
		if (id == null) {
			return null;
		}
		return userDao.getEntity(id);
	}

	@Override
	public void reSetOrganization(List<String> Organizations, String userid) {
		// 删除之前的组织机构
		AloneUser arolegroup = userDao.getEntity(userid);
		organizationuserDao.deleteAllEntity(arolegroup);
		// 添加现在的组织机构
		for (Iterator<String> iterator = Organizations.iterator(); iterator
				.hasNext();) {
			String name = (String) iterator.next();
			AloneOrganizationUser amr = new AloneOrganizationUser(userid, name,
					"0");
			organizationuserDao.insertEntity(amr);
		}
	}

	public OrganizationDaoInter getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDaoInter organizationDao) {
		this.organizationDao = organizationDao;
	}

	public OrganizationUserDaoInter getOrganizationuserDao() {
		return organizationuserDao;
	}

	public void setOrganizationuserDao(
			OrganizationUserDaoInter organizationuserDao) {
		this.organizationuserDao = organizationuserDao;
	}

	@Override
	public List<Map<String, Object>> getUserMenus(String userid) {
		// // 从组织取菜单
		// String sortTitle = SqlDomain
		// .initSortTitle("length(e.TREECODE),e.sort,e.treecode");
		// String currentPage = PageDomain.initCurrentPage("1");
		// String sortType = SqlDomain.initsortType("asc");
		// PageDomain page = new PageDomain(Integer.valueOf(currentPage), 1000);
		// List<LimitDomain> limitList = new ArrayList<LimitDomain>();
		// limitList.add(new LimitDomain("a.id", userid, "="));
		// limitList.add(new LimitDomain("e.type", "2", "!="));
		// limitList.add(new LimitDomain("e.type", "3", "!="));
		// limitList.add(new LimitDomain("e.id", "null", "is not"));
		// SqlFactoryInter sqldomain = new SqlDomain(
		// "alone_user a left join alone_organization_user b on a.id=b.userid left join alone_organization_rolegroup c on b.organizationid=c.organizationid left join  alone_menu_rolegroup d on c.rolegroupid=d.rolegroupid left join alone_menu e on e.id=d.menuid left join alone_action f on e.action=f.id ",
		// sortTitle, sortType, limitList);
		// PageDomain pagedomain = dqm
		// .runComplexQuery(
		// page,
		// " distinct e.name  as name,e.sort,e.parentid,e.treecode,e.id,e.type,f.url,f.lable ",
		// sqldomain);
		// // 将菜单放入map
		// // 遍历找出没有的菜单
		// // 将菜单都取出来，加入list
		// // 将list排序（存的时候操作吧，不然效率太低了）
		return null;
	}

	@Override
	public Map<String, AloneAction> getUserActions(String userid) {
		Map<String, AloneAction> map = new HashMap<String, AloneAction>();
		{
			// // 从组织取权限------------------------------------------------------
			// String sortTitle = SqlDomain.initSortTitle("id");
			// String currentPage = PageDomain.initCurrentPage("1");
			// String sortType = SqlDomain.initsortType("asc");
			// PageDomain page = new PageDomain(Integer.valueOf(currentPage),
			// 1000);
			// List<LimitDomain> limitList = new ArrayList<LimitDomain>();
			// limitList.add(new LimitDomain("ischeck", "1", "="));
			// SqlFactoryInter sqldomain = new SqlDomain(
			// "(select f.*  from alone_user a left join alone_organization_user b on a.id=b.userid left join alone_organization_rolegroup c on b.organizationid=c.organizationid left join  alone_menu_rolegroup d on c.rolegroupid=d.rolegroupid left join alone_menu e on e.id=d.menuid left join alone_action f on e.action=f.id   where 1=1   and  a.id = '"
			// + userid
			// + "'   and  e.id is not null    and  F.URL is not null ) a ",
			// sortTitle, sortType, limitList);
			// PageDomain pagedomain = dqm.runComplexQuery(page,
			// new AloneAction(), sqldomain);
			// @SuppressWarnings("unchecked")
			// List<AloneAction> Actions = pagedomain.getResourtList();
			// for (Iterator<AloneAction> iterator = Actions.iterator();
			// iterator
			// .hasNext();) {
			// AloneAction entity = (AloneAction) iterator.next();
			// map.put(entity.getUrl(), entity);
			// }
		}
		return map;
	}

	@Override
	public AloneUser getEntityByLoginName(String loginName) {
		return userDao.getEntityByLoginName(loginName);
	}

	@Override
	public void doLogin(String id) {
		AloneUser user = userDao.getEntity(id);
		user.setLogintime(TimeTool.getTimeDate12());
		userDao.editEntity(user);
	}

	// @Override
	// public void insertEntity(AloneUser entity, String password,
	// String organizationId, String positionId)
	// throws UserLoginNameIsExistException {
	// // 增加用户实体
	// entity.setCtime(TimeTool.getTimeDate12());
	// entity.setUtime(TimeTool.getTimeDate12());
	// this.insertEntity(entity, password);
	// // 生成组织关系
	// AloneOrganizationUser orgUser = new AloneOrganizationUser(entity
	// .getId(), organizationId);
	// organizationuserDao.insertEntity(orgUser);
	// if (positionId != null && positionId.trim().length() > 1) {
	// // 生成岗位关系
	// AlonePositionUser posuser = new AlonePositionUser(positionId,
	// entity.getId());
	// positionUserDao.insertEntity(posuser);
	// }
	// }

	// @Override
	// public void insertEntity(AloneUser entity, String password,
	// String organizationId, String positionId, AloneUser currentUser)
	// throws UserLoginNameIsExistException {
	// // 增加用户实体
	// entity.setCuser(currentUser.getId());
	// entity.setMuser(currentUser.getId());
	// entity.setCtime(TimeTool.getTimeDate12());
	// entity.setUtime(TimeTool.getTimeDate12());
	// this.insertEntity(entity, password);
	// // 生成组织关系
	// AloneOrganizationUser orgUser = new AloneOrganizationUser(entity
	// .getId(), organizationId);
	// organizationuserDao.insertEntity(orgUser);
	// if (positionId != null && positionId.trim().length() > 1) {
	// // 生成岗位关系
	// String[] userPositions = positionId.split("@");
	// for (int i = 0; i < userPositions.length; i++) {
	// AlonePositionUser posuser = new AlonePositionUser(
	// userPositions[i], entity.getId());
	// positionUserDao.insertEntity(posuser);
	// }
	// }
	// }

	@Override
	public List<String> getUserOrg(String id) {
		List<String> list = userDao.getUserOrg(id);
		List<String> list2 = new ArrayList<String>();
		list2.addAll(list);
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			int n = 0;
			if (name == null || name.trim().length() < 1) {
				list2.remove(n);
				n++;
			}
		}
		return list2;
	}

	@Override
	public void editEntity(AloneUser entity, String orgIds) {
		// 修改用户
		editEntity(entity);
		List<String> orgList = ParameterUtils.expandDomainPara(orgIds);
		// 修改用户组织
		organizationuserDao.editEntity(entity.getId(), orgList);
	}

	@Override
	public Map<String, Map<String, String>> getUserAllPopedom(String userid) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		// {
		// // 从岗位取权限------------------------------------------------------
		// String sortTitle = SqlDomain.initSortTitle("e.id");
		// String currentPage = PageDomain.initCurrentPage("1");
		// String sortType = SqlDomain.initsortType("asc");
		// PageDomain page = new PageDomain(Integer.valueOf(currentPage), 2000);
		// List<LimitDomain> limitList = new ArrayList<LimitDomain>();
		// limitList.add(new LimitDomain("e.id", "null", "is not"));
		//
		// // limitList.add(new LimitDomain("G.name", "%" + filterKey + "%",
		// // "like"));
		//
		// SqlFactoryInter sqldomain = new SqlDomain(
		// " alone_position_user a left join alone_positon_rolegroup b on a.position=b.position and a.tuser='"
		// + userid
		// +
		// "' left join alone_menu_rolegroup c on b.rolegroup=c.rolegroupid left join alone_menu e on c.menuid=e.id left join alone_action f on e.action=f.id left join alone_rolegroup as G on G.id=C.rolegroupid ",
		// sortTitle, sortType, limitList);
		// PageDomain pagedomain = dqm
		// .runComplexQuery(
		// page,
		// "e.name  as name,e.sort,e.parentid,e.treecode,e.id,e.type,f.url,f.lable,e.img",
		// sqldomain);
		// @SuppressWarnings("unchecked")
		// List<Map<String, String>> Actions = pagedomain.getResourtList();
		// for (Iterator<Map<String, String>> iterator = Actions.iterator();
		// iterator
		// .hasNext();) {
		// Map<String, String> entity = (Map<String, String>) iterator
		// .next();
		// map.put(entity.get("e.treecode".toUpperCase()), entity);
		// }
		// }
		// {
		// // 从组织取权限------------------------------------------------------
		// String sortTitle = SqlDomain.initSortTitle("e.id");
		// String currentPage = PageDomain.initCurrentPage("1");
		// String sortType = SqlDomain.initsortType("asc");
		// PageDomain page = new PageDomain(Integer.valueOf(currentPage), 2000);
		// List<LimitDomain> limitList = new ArrayList<LimitDomain>();
		// limitList.add(new LimitDomain("e.id", "null", "is not"));
		// SqlFactoryInter sqldomain = new SqlDomain(
		// " alone_organization_user A LEFT JOIN alone_organization_rolegroup B ON a.organizationid=b.organizationid AND A.userid='"
		// + userid
		// +
		// "' LEFT JOIN ALONE_MENU_ROLEGROUP C ON B.rolegroupid=C.ROLEGROUPID LEFT JOIN ALONE_MENU E ON C.MENUID=E.ID LEFT JOIN ALONE_ACTION F ON E.ACTION=F.ID left join alone_rolegroup as G on G.id=C.rolegroupid ",
		// sortTitle, sortType, limitList);
		// sqldomain.setUserWhere("");
		// PageDomain pagedomain = dqm
		// .runComplexQuery(
		// page,
		// "e.name  as name,e.sort,e.parentid,e.treecode,e.id,e.type,f.url,f.lable",
		// sqldomain);
		// @SuppressWarnings("unchecked")
		// List<Map<String, String>> Actions = pagedomain.getResourtList();
		// for (Iterator<Map<String, String>> iterator = Actions.iterator();
		// iterator
		// .hasNext();) {
		// Map<String, String> entity = (Map<String, String>) iterator
		// .next();
		// map.put(entity.get("e.treecode".toUpperCase()), entity);
		// }
		// }
		return map;
	}

	@Override
	public Map<String, Map<String, String>> getAction(
			Map<String, Map<String, String>> allPop) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Set<String> set = allPop.keySet();
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			Map<String, String> action = allPop.get(name);
			if (action.get("F.URL") != null) {
				map.put(InitUrlString((String) action.get("F.URL")), action);
			}
		}
		return map;
	}

	private String InitUrlString(String requestUrl) {
		// 截去url参数
		int num = requestUrl.indexOf("?");
		if (num > 0) {
			requestUrl = requestUrl.substring(0, num);
		}
		// 截去url前缀
		int num2 = requestUrl.replace("\\", "/").lastIndexOf("/");
		requestUrl = requestUrl.substring(num2);
		return requestUrl.replace("/", "").trim();
	}

	@Override
	public List<Map<String, Object>> getMenus(
			Map<String, Map<String, String>> allPop) {
		// PageDomain page = new PageDomain(1);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Set<String> set = allPop.keySet();
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			Map<String, String> action = allPop.get(name);
			list.add(action);
		}
		Collections.sort(list, new Comparator<Map<String, String>>() {
			public int compare(Map<String, String> arg0,
					Map<String, String> arg1) {
				// length(e.TREECODE),e.sort,e.treecode
				// 先比较树编码长度
				if (arg0.get("E.TREECODE").length() != arg1.get("E.TREECODE")
						.length()) {
					return arg0.get("E.TREECODE").length()
							- arg1.get("E.TREECODE").length();
				} else {
					// 再比较排序号
					try {
						return Integer.valueOf(arg0.get("E.SORT").trim())
								- Integer.valueOf(arg1.get("E.SORT").trim());
					} catch (Exception e) {
						return 0;
					}
				}
			}
		});
		// page.setResourtList(list);
		// return page;
		return null;
	}

	@Override
	public void deleteLogicEntity(String id, AloneUser user) {
		// 删除组织
		organizationuserDao.deleteAllEntity(userDao.getEntity(id));
		// 删除用户
		AloneUser entity = userDao.getEntity(id);
		entity.setState("2");
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setMuser(user.getId());
		userDao.editEntity(entity);
	}

	@Override
	public AloneUser editEntity(AloneUser entity, AloneUser currentUser) {
		AloneUser usertemp = userDao.getEntity(entity.getId());
		usertemp.setName(entity.getName());
		usertemp.setState(entity.getState());
		usertemp.setUtime(TimeTool.getTimeDate12());
		usertemp.setMuser(currentUser.getId());
		userDao.editEntity(usertemp);
		return usertemp;
	}

	@Override
	public AloneUser editEntity(AloneUser entity, AloneUser currentUser,
			String optionsId) {
		AloneUser usertemp = userDao.getEntity(entity.getId());
		usertemp.setComments(entity.getComments());
		usertemp.setName(entity.getName());
		usertemp.setLoginname(entity.getLoginname());
		usertemp.setState(entity.getState());
		userDao.editEntity(usertemp);
		return usertemp;
	}

	public AloneUser insertEntity(AloneUser entity,
			AloneOrganization orgEntity, AloneUser currentUser)
			throws UserLoginNameIsExistException {
		entity = insertUser(entity, currentUser);
		ArrayList<String> Organizations = new ArrayList<String>();
		if (orgEntity != null && orgEntity.getId().trim().length() > 0) {
			Organizations.add(orgEntity.getId());
			this.reSetOrganization(Organizations, entity.getId());
		}
		return entity;
	}

	@Override
	public AloneUser insertUser(AloneUser entity, AloneUser currentUser)
			throws UserLoginNameIsExistException {
		if (entity.getLoginname() == null
				|| entity.getLoginname().trim().length() <= 0) {
			String loginName = PinyinTool.turnPinyin(entity.getName());
			String finalloginName = loginName;
			int i = 1;
			while (userDao.isExist(finalloginName)) {
				finalloginName = loginName + i;
				i++;
			}
			entity.setLoginname(finalloginName);
		}
		// 是否登录名重复
		if (userDao.isExist(entity.getLoginname())) {
			throw new UserLoginNameIsExistException("");
		} else {
			String passwordPub = null;
			if (entity.getPassword() == null
					|| entity.getPassword().trim().length() <= 0) {
				passwordPub = FarmManager.instance().getConfigValue(
						PARAMETER_PASSWORD);
			}
			if (entity.getPassword() == null
					|| entity.getPassword().trim().length() <= 0) {
				passwordPub = DEFAULT_PASSWORD;
			}
			if (passwordPub == null && entity.getPassword() != null) {
				entity.setPassword(entity.getPassword());
			} else {
				entity.setPassword(authprovider.encodeMd5(passwordPub));
			}

			entity.setCtime(TimeTool.getTimeDate12());
			if (currentUser != null) {
				entity.setCuser(currentUser.getId());
				entity.setMuser(currentUser.getId());
			} else {
				entity.setCuser("NONE");
				entity.setMuser("NONE");
			}
			entity.setLogintime("");
			entity.setType("0");
			entity.setUtime(TimeTool.getTimeDate12());
			return this.getEntity(userDao.insertEntity(entity));
		}
	}

	@Override
	public boolean getUserListByLoginName(String userId, String loginName,
			String pageType) {
		// 当前用户
		AloneUser currentUser = userDao.getEntity(userId);
		// 根据登录名查询到的用户
		AloneUser user = userDao.getEntityByLoginName(loginName);
		if (currentUser != null) {
			if (loginName.equals(currentUser.getLoginname())) {
				if ("1".equals(pageType)) {
					return false;
				} else {
					return true;
				}
			} else {
				if (user != null) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			if (user != null) {
				return false;
			} else {
				return true;
			}
		}
	}

	public AuthenticateProvider getAuthprovider() {
		return authprovider;
	}

	public void setAuthprovider(AuthenticateProvider authprovider) {
		this.authprovider = authprovider;
	}

	@Override
	public void editEntity(AloneUser entity, AloneOrganization orgEntity,
			AloneUser currentUser) {
		entity = editEntity(entity, currentUser);
		ArrayList<String> Organizations = new ArrayList<String>();
		if (orgEntity != null && orgEntity.getId().trim().length() > 0) {
			Organizations.add(orgEntity.getId());
			this.reSetOrganization(Organizations, entity.getId());
		}
	}

	@Override
	public AloneUser register(AloneUser user, AloneOrganization orgEntity,
			String state) throws UserLoginNameIsExistException {
		// 是否登录名重复
		if (userDao.isExist(user.getLoginname())) {
			throw new UserLoginNameIsExistException("");
		} else {
			String passwordPub = null;
			if (user.getPassword() == null
					|| user.getPassword().trim().length() <= 0) {
				if (user.getPassword() == null
						|| user.getPassword().trim().length() <= 0) {
					passwordPub = FarmManager.instance().getConfigValue(
							PARAMETER_PASSWORD);
				}
				if (user.getPassword() == null
						|| user.getPassword().trim().length() <= 0) {
					passwordPub = DEFAULT_PASSWORD;
				}
				user.setPassword(authprovider.encodeMd5(passwordPub));
			}
			user.setPassword(user.getPassword().toUpperCase());
			user.setCtime(TimeTool.getTimeDate12());
			user.setCuser("self");
			user.setMuser("self");
			user.setLogintime("");
			user.setType("1");
			user.setState(state);
			user.setUtime(TimeTool.getTimeDate12());
			return insertEntity(user, orgEntity, null);
		}

	}

	@Override
	public boolean updateToAdminFromUser(String id, AloneUser currentuser) {
		AloneUser usr = userDao.getEntity(id);
		usr.setType("1");
		usr.setUtime(TimeTool.getTimeDate12());
		usr.setMuser(currentuser.getId());
		userDao.editEntity(usr);
		return true;
	}

	@Override
	public boolean updateToRemoveAdmin(String id, AloneUser currentuser) {
		AloneUser usr = userDao.getEntity(id);
		usr.setType("0");
		usr.setUtime(TimeTool.getTimeDate12());
		usr.setMuser(currentuser.getId());
		userDao.editEntity(usr);
		aloneUserRolegroupDao.removeUserAllRuleGroups(id);
		return true;
	}

	@Override
	public void addOrg(String userId, String orgid, boolean isMain) {// 添加现在的菜单
		if (isMain) {
			organizationuserDao.deleteAllMainEntity(userId);
			organizationuserDao.deleteUserOrg(userId, orgid);
			organizationuserDao.insertEntity(new AloneOrganizationUser(userId,
					orgid, "1"));
		} else {
			organizationuserDao.deleteUserOrg(userId, orgid);
			organizationuserDao.insertEntity(new AloneOrganizationUser(userId,
					orgid, "0"));
		}
	}

	@Override
	public Map<String, Object> findIsMainOrg(String userId, String orgId) {
		// 通过人员ID查找是否存在主要组织机构。
		Map<String, Object> infoMap = new HashMap<String, Object>();
		AloneOrganization orgEntity = userDao.findMainOrg(userId);
		// 如果存在，并且和参数orgId相等...
		if (orgEntity != null) {
			if (orgId.equals(orgEntity.getId())) {
				infoMap.put("infoState", "1");
				infoMap.put("orgInfo", "该人员在\"" + orgEntity.getName()
						+ "\"下已为主要组织机构！");
				// 否则...
			} else {
				infoMap.put("infoState", "2");
				infoMap.put("orgInfo", "该人员在\"" + orgEntity.getName()
						+ "\"下已为主要组织机构，确定更改？");
			}
			// 如果不存在
		} else {
			infoMap.put("infoState", "0");
		}
		return infoMap;
	}

	@Override
	public void deleteOrg(String userId, String orgId) {
		organizationuserDao.deleteUserOrg(userId, orgId);
	}

	@Override
	public List<AloneOrganization> findUnMianOrgList(String userId) {
		return userDao.findUnMianOrgList(userId);
	}

	@Override
	public AloneOrganization findMianOrg(String userId) {
		return userDao.findMainOrg(userId);
	}

	public AloneUserRolegroupDaoInter getAloneUserRolegroupDao() {
		return aloneUserRolegroupDao;
	}

	public void setAloneUserRolegroupDao(
			AloneUserRolegroupDaoInter aloneUserRolegroupDao) {
		this.aloneUserRolegroupDao = aloneUserRolegroupDao;
	}

}
