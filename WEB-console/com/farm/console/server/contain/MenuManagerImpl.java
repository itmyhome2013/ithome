package com.farm.console.server.contain;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.farm.console.prisist.dao.MenuDaoInter;
import com.farm.console.prisist.dao.MenuRolegroupDaoInter;
import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.prisist.domain.AloneMenu;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;
import com.farm.core.web.ParameterUtils;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class MenuManagerImpl implements MenuManagerInter {
	private static final Logger log = Logger.getLogger(MenuManagerImpl.class);
	private MenuDaoInter menuDao;
	private MenuRolegroupDaoInter menuRoleDao;
	private ActionManagerInter actionManager;

	private static final List<AloneMenu> MenuList = new Vector<AloneMenu>();

	public String getConfigValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean initConfig() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean refreshConfigMap() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.macpro.alone.server.contain.dd$deleteEntity(java.lang.String)
	 */
	public void deleteEntity(String entity) {
		// 删除对应角色
		AloneMenu menu = menuDao.getEntity(entity);
		// 删子节点
		DataQuery query = DataQuery.getInstance("1", "id,treecode",
				"alone_menu");
		query.addSort(new DBSort("id", "asc"));
		query.addRule(new DBRule("treecode", menu.getTreecode(), "like%"));
		List<Map<String, Object>> listEntity = null;
		try {
			listEntity = query.search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Iterator<Map<String, Object>> iterator = listEntity.iterator(); iterator
				.hasNext();) {
			Map<String, Object> object = (Map<String, Object>) iterator.next();
			AloneMenu treeMenu = menuDao.getEntity(object.get("ID").toString());
			menuRoleDao.deleteAllEntity(treeMenu);
			menuDao.deleteEntity(treeMenu);
		}
	}

	@Override
	public void deleteEntity(String name, AloneUser currentUser) {
		// 删除对应角色
		AloneMenu menu = menuDao.getEntity(name);
		DataQuery query = DataQuery.getInstance("1", "id,treecode",
				"alone_menu");
		query.addSort(new DBSort("id", "asc"));
		query.addRule(new DBRule("treecode", menu.getTreecode(), "like%"));
		List<Map<String, Object>> listEntity = null;
		try {
			listEntity = query.search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 删子节点
		for (Iterator<Map<String, Object>> iterator = listEntity.iterator(); iterator
				.hasNext();) {
			Map<String, Object> object = (Map<String, Object>) iterator.next();
			AloneMenu treeMenu = menuDao.getEntity(object.get("ID").toString());
			menuRoleDao.deleteAllEntity(treeMenu);
			menuDao.deleteEntity(treeMenu);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$editEntity(org.macpro.alone.prisist
	 * .domain.AloneMenu)
	 */
	public void editEntity(AloneMenu entity) {
		AloneMenu entity2 = menuDao.getEntity(entity.getId());
		entity2.setAction(entity.getAction());
		entity2.setComments(entity.getComments());
		entity2.setImg(entity.getImg());
		entity2.setName(entity.getName());
		entity2.setParentid(entity.getParentid());
		entity2.setSort(entity.getSort());
		entity2.setState(entity.getState());
		entity2.setType(entity.getType());
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setUuser(entity.getUuser());
		menuDao.editEntity(entity2);
	}

	@Override
	public AloneMenu editEntity(AloneMenu entity, AloneUser currentUser) {
		AloneMenu entity2 = menuDao.getEntity(entity.getId());
		entity2.setAction(entity.getAction());
		entity2.setComments(entity.getComments());
		entity2.setImg(entity.getImg());
		entity2.setName(entity.getName());
		entity2.setSort(entity.getSort());
		entity2.setState(entity.getState());
		entity2.setType(entity.getType());
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setUuser(currentUser.getId());
		menuDao.editEntity(entity2);
		return entity2;
	}

	public List<AloneMenu> getAllEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAllListNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.macpro.alone.server.contain.dd$getEntity(java.lang.String)
	 */
	public AloneMenu getEntity(String id) {
		if (id == null) {
			return null;
		}
		return menuDao.getEntity(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$insertEntity(org.macpro.alone.prisist
	 * .domain.AloneMenu)
	 */
	public void insertEntity(AloneMenu entity) {
		menuDao.insertEntity(entity);
	}

	@Override
	public AloneMenu insertEntity(AloneMenu entity, AloneUser currentUser,
			AloneAction action) {
		entity.setCuser(currentUser.getId());
		entity.setUuser(currentUser.getId());
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setUtime(TimeTool.getTimeDate12());
		if (entity.getParentid() == null
				|| entity.getParentid().trim().length() <= 0) {
			entity.setParentid(ROOT_PAREANT);
		}
		// 类型为结构菜单则不设置权限
		if (entity.getType().equals(MENU_TYPE.STRUCT.getValue())) {
			entity.setAction(null);
		} else {
			// 如果传入了id标准用户选择了一个已有的权限
			if (action.getId() == null || action.getId().trim().length() <= 0) {
				// 如果没有id则为手填的
				// 新建立action
				String actionName = "";
				if (!entity.getParentid().equals(ROOT_PAREANT)) {
					actionName = menuDao.getEntity(entity.getParentid())
							.getName()
							+ '_' + entity.getName();
				} else {
					actionName = entity.getName();
				}
				action.setName(actionName);
				action.setIscheck("1");
				entity.setAction(actionManager
						.insertEntity(action, currentUser).getId());
			} else {
				// 如果action有id则为选择的
				entity.setAction(action.getId());
			}
		}
		// 临时存入
		entity.setTreecode("none");
		entity.setDomain("alone");
		entity = menuDao.insertEntity(entity);
		if (entity.getParentid().equals(ROOT_PAREANT)) {
			entity.setTreecode(entity.getId());
		} else {
			entity.setTreecode(menuDao.getEntity(entity.getParentid())
					.getTreecode()
					+ entity.getId());
		}
		return entity;
	}

	public DataResult addTreeNode(DataQuery query, DBSort sort,
			String aloneContext, String tableName, String rowName) {
		log.debug("动态添加对象树的子节点");
		query = DataQuery.init(query, tableName, rowName);
		query.addSort(sort);
		query.addRule(new DBRule("PARENTID", aloneContext, "="));
		query.addRule(new DBRule("a.STATE", "2", "!="));
		query.setPagesize(1000);
		DataResult result = menuDao.loadTreeNode(query);
		return result;
	}

	public DataResult initTree(DataQuery query, DBSort sort, String tableName,
			String rowName) {
		log.debug("初始对象树");
		return this.addTreeNode(query, sort, "none", tableName, rowName);
	}

	@Override
	public String getMenuParentNamePath(String menuId) {
		List<AloneMenu> list = getAloneMenuPath(menuId);
		String path = null;
		for (AloneMenu menu : list) {
			if (!menu.getId().equals(menuId)) {
				if (path != null) {
					path = path + "/";
					path = path + menu.getName();
				} else {
					path = menu.getName();
				}
			}
		}
		return path;
	}

	/**
	 * 根据菜单id获取菜单上级节点
	 * 
	 * @param menuId
	 * @return
	 */
	private List<AloneMenu> getAloneMenuPath(String menuId) {
		List<AloneMenu> list = new ArrayList<AloneMenu>();
		while (menuId != null && menuId.trim().length() > 0) {
			AloneMenu node = menuDao.getEntity(menuId);
			if (node == null) {
				break;
			}
			menuId = node.getParentid();
			list.add(node);
		}
		return list;
	}

	@Override
	public void copyMenuTo(String ids, String oids, AloneUser cuser) {
		AloneMenu omenu = menuDao.getEntity(oids);
		for (String id : ParameterUtils.expandDomainPara(ids)) {
			// 得到选择的菜单
			AloneMenu menuNew = new AloneMenu();
			AloneMenu menu = menuDao.getEntity(ids);
			try {
				menuNew=(AloneMenu) BeanUtils.cloneBean(menu);
			} catch (Exception e) {
				log.error(e+e.getMessage());
				e.printStackTrace();
			}
			// 建立菜单对象组并插入
			menuNew.setParentid(oids);
			menuNew.setTreecode(omenu.getTreecode() + id);
			menuNew.setCtime(TimeTool.getTimeDate12());
			menuNew.setUtime(TimeTool.getTimeDate12());
			menuNew.setCuser(cuser.getId());
			menuNew.setUuser(cuser.getId());
			menuNew.setId(null);
			menuDao.insertEntity(menuNew);
		}
	}

	public static void main(String[] args) {
		System.out.println("123456".substring("123456".indexOf("123")));
	}

	public static List<AloneMenu> getmenuList() {
		return MenuList;
	}

	public MenuDaoInter getmenuDao() {
		return menuDao;
	}

	public void setmenuDao(MenuDaoInter menuDao) {
		this.menuDao = menuDao;
	}

	public MenuRolegroupDaoInter getMenuRoleDao() {
		return menuRoleDao;
	}

	public void setMenuRoleDao(MenuRolegroupDaoInter menuRoleDao) {
		this.menuRoleDao = menuRoleDao;
	}

	public ActionManagerInter getActionManager() {
		return actionManager;
	}

	public void setActionManager(ActionManagerInter actionManager) {
		this.actionManager = actionManager;
	}

}
