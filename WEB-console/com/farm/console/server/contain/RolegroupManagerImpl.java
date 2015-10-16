package com.farm.console.server.contain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.farm.console.prisist.dao.AloneUserRolegroupDaoInter;
import com.farm.console.prisist.dao.MenuDaoInter;
import com.farm.console.prisist.dao.MenuRolegroupDaoInter;
import com.farm.console.prisist.dao.RolegroupDaoInter;
import com.farm.console.prisist.dao.UserDaoInter;
import com.farm.console.prisist.domain.AloneMenu;
import com.farm.console.prisist.domain.AloneMenuRolegroup;
import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.console.prisist.domain.AloneUserRolegroup;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;
import com.farm.core.web.ParameterUtils;
import com.farm.web.constant.FarmConstant;

public class RolegroupManagerImpl implements RolegroupManagerInter {
	private static final Logger log = Logger
			.getLogger(RolegroupManagerImpl.class);
	private RolegroupDaoInter rolegroupDao;
	private MenuRolegroupDaoInter menuRoleDao;
	private MenuDaoInter menuDao;
	private AloneUserRolegroupDaoInter aloneUserRolegroupDao;
	private UserDaoInter userDao;
	private static final List<AloneRolegroup> RolegroupList = new Vector<AloneRolegroup>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.macpro.alone.server.contain.dd$deleteEntity(java.lang.String)
	 */
	public void deleteEntity(String entity) {
		AloneRolegroup rolegroup = rolegroupDao.getEntity(entity);
		menuRoleDao.deleteAllEntity(rolegroup);
		rolegroupDao.deleteEntity(rolegroupDao.getEntity(entity));
	}

	@Override
	public void deleteEntity(String name, AloneUser currentUser) {
		AloneRolegroup rolegroup = rolegroupDao.getEntity(name);
		menuRoleDao.deleteAllEntity(rolegroup);
		rolegroupDao.deleteEntity(rolegroupDao.getEntity(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$editEntity(org.macpro.alone.prisist
	 * .domain.AloneRolegroup)
	 */
	public void editEntity(AloneRolegroup entity) {
		rolegroupDao.editEntity(entity);
	}

	@Override
	public AloneRolegroup editEntity(AloneRolegroup entity,
			AloneUser currentUser) {
		AloneRolegroup entity2 = rolegroupDao.getEntity(entity.getId());
		entity2.setComments(entity.getComments());
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setName(entity.getName());
		entity2.setState(entity.getState());
		entity2.setMuser(currentUser.getId());
		rolegroupDao.editEntity(entity2);
		return entity2;
	}

	public List<AloneRolegroup> getAllEntity() {
		return null;
	}

	public int getAllListNum() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.macpro.alone.server.contain.dd$getEntity(java.lang.String)
	 */
	public AloneRolegroup getEntity(String id) {
		if (id == null) {
			return null;
		}
		return rolegroupDao.getEntity(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$insertEntity(org.macpro.alone.prisist
	 * .domain.AloneRolegroup)
	 */
	public void insertEntity(AloneRolegroup entity) {
		rolegroupDao.insertEntity(entity);
	}

	@Override
	public AloneRolegroup insertEntity(AloneRolegroup entity,
			AloneUser currentUser) {
		entity.setCuser(currentUser.getId());
		entity.setMuser(currentUser.getId());
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setUtime(TimeTool.getTimeDate12());
		rolegroupDao.insertEntity(entity);
		return entity;
	}

	public static List<AloneRolegroup> getrolegroupList() {
		return RolegroupList;
	}

	public RolegroupDaoInter getrolegroupDao() {
		return rolegroupDao;
	}

	public void setrolegroupDao(RolegroupDaoInter rolegroupDao) {
		this.rolegroupDao = rolegroupDao;
	}

	@Override
	public void reSetMenu(List<String> menuInitList, String rolegroup) {
		if (rolegroup == null || rolegroup.trim().length() <= 0) {
			throw new IllegalArgumentException("rolegroupId不能够为空");
		}
		List<String> menuList = new ArrayList<String>();
		if (menuInitList.size() > 0) {
			for (String str : menuInitList) {
				if (!str.contains("img") && !str.contains("none")) {
					menuList.add(str);
				}
			}
		}
		// 删除之前的菜单
		AloneRolegroup arolegroup = rolegroupDao.getEntity(rolegroup);
		menuRoleDao.deleteAllEntity(arolegroup);
		Set<String> MenuSet = new HashSet<String>();
		// 第一次把每一个menu取出来，放入map
		for (Iterator<String> iterator = menuList.iterator(); iterator
				.hasNext();) {
			String id = (String) iterator.next();
			MenuSet.add(id);
		}
		// 第二次，把每一个和其父节点取出来查看是否有，没有就加入序列和map
		for (Iterator<String> iterator = menuList.iterator(); iterator
				.hasNext();) {
			String name = (String) iterator.next();
			AloneMenu menu = menuDao.getEntity(name);
			String treeCoder = menu.getTreecode();
			List<String> list = splitTreeCoderForParentID(treeCoder);
			for (String treecoderId : list) {
				if (!MenuSet.contains(treecoderId)) {
					AloneMenu menutemp = menuDao.getEntity(treecoderId);
					// 没有就加入序列和map
					if (menutemp == null) {
						throw new RuntimeException("tree Not Have");
					}
					MenuSet.add(treecoderId);
				}
			}
		}
		menuList.addAll(MenuSet);
		// 添加现在的菜单
		for (Iterator<String> iterator = menuList.iterator(); iterator
				.hasNext();) {
			String name = (String) iterator.next();
			AloneMenuRolegroup amr = new AloneMenuRolegroup(rolegroup, name);
			menuRoleDao.insertEntity(amr);
		}
	}

	/**
	 * 对一个树编码计算出它所有的父亲树ID传出
	 * 
	 * @param treecoder
	 * @return
	 */
	private static List<String> splitTreeCoderForParentID(String treecoder) {
		if (treecoder.length() % FarmConstant.MENU_TREECODE_UNIT_LENGTH != 0) {
			throw new RuntimeException("menu treecoder Is Error");
		}
		String treeCoder = new String(treecoder);
		List<String> list = new ArrayList<String>();
		list.add(treeCoder.substring(treeCoder.length()
				- FarmConstant.MENU_TREECODE_UNIT_LENGTH));
		while (treeCoder.length() > FarmConstant.MENU_TREECODE_UNIT_LENGTH) {
			treeCoder = treeCoder.substring(0, treeCoder.length()
					- FarmConstant.MENU_TREECODE_UNIT_LENGTH);
			list.add(treeCoder.substring(treeCoder.length()
					- FarmConstant.MENU_TREECODE_UNIT_LENGTH));
		}
		return list;
	}

	public MenuRolegroupDaoInter getMenuRoleDao() {
		return menuRoleDao;
	}

	public void setMenuRoleDao(MenuRolegroupDaoInter menuRoleDao) {
		this.menuRoleDao = menuRoleDao;
	}

	public MenuDaoInter getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDaoInter menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Map<String, String>> getRoleAction(String roleId) {
		return rolegroupDao.getRoleAction(roleId);
	}

	public DataResult addTreeNode(DataQuery query, DBSort sort,
			String aloneContext, String tableName, String rowName) {
		log.debug("动态添加对象树的子节点");
		query = DataQuery.init(query, tableName, rowName);
		query.addSort(sort);
		query.addRule(new DBRule("PARENTID", aloneContext, "="));
		query.addRule(new DBRule("a.STATE", "2", "!="));
		query.setPagesize(1000);
		DataResult result = rolegroupDao.loadTreeNode(query);
		return result;
	}

	public DataResult initTree(DataQuery query, DBSort sort, String tableName,
			String rowName) {
		log.debug("初始对象树");
		return this.addTreeNode(query, sort, "none", tableName, rowName);
	}

	@Override
	public void setRuleGroupForUser(String ruleGroupIds, String userid) {
		List<String> ruleIdsArray = ParameterUtils
				.expandDomainPara(ruleGroupIds);
		// 删除用户的所有角色
		aloneUserRolegroupDao.removeUserAllRuleGroups(userid);
		// 为用户赋予指定角色
		for (String id : ruleIdsArray) {
			AloneUserRolegroup entity = new AloneUserRolegroup();
			entity.setRolegroup(id);
			entity.setUserid(userid);
			aloneUserRolegroupDao.insertEntity(entity);
		}
		// 为用户设置类型设置为管理员
		AloneUser user = userDao.getEntity(userid);
		if (ruleIdsArray.size() > 0) {
			user.setType("1");// 设置为管理员
		} else {
			user.setType("0");// 设置为普通用户
		}
		userDao.editEntity(user);
	}

	public AloneUserRolegroupDaoInter getAloneUserRolegroupDao() {
		return aloneUserRolegroupDao;
	}

	public UserDaoInter getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoInter userDao) {
		this.userDao = userDao;
	}

	public void setAloneUserRolegroupDao(
			AloneUserRolegroupDaoInter aloneUserRolegroupDao) {
		this.aloneUserRolegroupDao = aloneUserRolegroupDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneRolegroup> getRoles(String userid) {
		DataQuery query = DataQuery
				.getInstance(
						"1",
						"a.id AS ID,a.NAME AS NAME,a.COMMENTS AS COMMENTS,a.CTIME AS CTIME,a.CUSER AS CUSER,a.MUSER AS MUSER,a.STATE AS STATE,a.UTIME AS UTIME ",
						"alone_rolegroup a LEFT JOIN  alone_user_rolegroup b ON a.ID=b.ROLEGROUP");
		query.addRule(new DBRule("b.USERID", userid, "="));
		query.setPagesize(10);
		List<AloneRolegroup> list=null;
		try {
			DataResult result = query.search();
			list=(List)result.getObjectList(AloneRolegroup.class);
		} catch (SQLException e) {
			log.error(e);
		}
		return list;
		
	}

}
