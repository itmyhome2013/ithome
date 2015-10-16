package com.farm.console.server.contain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import com.farm.console.contain.exception.HaveReferenceException;
import com.farm.console.prisist.dao.ActionDaoInter;
import com.farm.console.prisist.dao.MenuDaoInter;
import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;

public class ActionManagerImpl implements ActionManagerInter {
	private ActionDaoInter actionDao;
	private MenuDaoInter menuDao;
	private static final List<AloneAction> ActionList = new Vector<AloneAction>();

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

	@SuppressWarnings("unchecked")
	public void deleteEntity(String entity) throws HaveReferenceException {
		DataQuery query = DataQuery.getInstance("1", "b.id,'a'",
				"alone_action a left join alone_menu b on a.id=b.action");
		query.addRule(new DBRule("b.action", entity, "="));
		DataResult pagedomain = null;
		try {
			pagedomain = query.search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List listEntity = pagedomain.getResultList();
		if (listEntity != null && listEntity.size() > 0) {
			throw new HaveReferenceException(
					"don't delete the entity because have reference one or many");
		} else {
			actionDao.deleteEntity(actionDao.getEntity(entity));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$editEntity(org.macpro.alone.prisist
	 * .domain.AloneAction)
	 */
	public AloneAction editEntity(AloneAction entity) {
		AloneAction entity2 = actionDao.getEntity(entity.getId());
		entity2.setComments(entity.getComments());
		entity2.setCtime(TimeTool.getTimeDate12());
		entity2.setCuser(entity.getCuser());
		entity2.setIscheck(entity.getIscheck());
		entity2.setMuser(entity.getMuser());
		entity2.setName(entity.getName());
		entity2.setState(entity.getState());
		entity2.setType(entity.getType());
		entity2.setUrl(entity.getUrl());
		entity2.setUtime(TimeTool.getTimeDate12());
		actionDao.editEntity(entity2);
		return entity2;
	}

	@Override
	public AloneAction editEntity(AloneAction entity, AloneUser currentUser) {
		AloneAction entity2 = actionDao.getEntity(entity.getId());
		entity2.setComments(entity.getComments());
		entity2.setIscheck(entity.getIscheck());
		entity2.setMuser(currentUser.getId());
		entity2.setName(entity.getName());
		entity2.setState(entity.getState());
		entity2.setType(entity.getType());
		entity2.setUrl(entity.getUrl());
		entity2.setUtime(TimeTool.getTimeDate12());
		actionDao.editEntity(entity2);
		return entity2;
	}

	@SuppressWarnings("unchecked")
	public Map<String, AloneAction> getAllEntity() {
		// TODO Auto-generated method stub
		List<AloneAction> list = actionDao.getAllEntity();
		Map<String, AloneAction> map = new HashMap<String, AloneAction>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			AloneAction entity = (AloneAction) iterator.next();
			map.put(entity.getUrl(), entity);
		}
		return map;
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
	public AloneAction getEntity(String id) {
		if (id == null) {
			return null;
		}
		return actionDao.getEntity(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$insertEntity(org.macpro.alone.prisist
	 * .domain.AloneAction)
	 */
	public AloneAction insertEntity(AloneAction entity) {
		return actionDao.insertEntity(entity);
	}

	@Override
	public AloneAction insertEntity(AloneAction entity, AloneUser currentUser) {
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setCuser(currentUser.getId());
		entity.setMuser(currentUser.getId());
		entity.setType("N");
		entity.setState("1");
		return actionDao.insertEntity(entity);
	}

	public static List<AloneAction> getactionList() {
		return ActionList;
	}

	public ActionDaoInter getactionDao() {
		return actionDao;
	}

	public void setactionDao(ActionDaoInter actionDao) {
		this.actionDao = actionDao;
	}

	public MenuDaoInter getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDaoInter menuDao) {
		this.menuDao = menuDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, AloneAction> getAllcheckEntity() {
		// TODO Auto-generated method stub
		List<AloneAction> list = actionDao.getAllcheckEntity();
		Map<String, AloneAction> map = new HashMap<String, AloneAction>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			AloneAction entity = (AloneAction) iterator.next();
			map.put(InitUrlString(entity.getUrl()), entity);
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

	/**
	 * 根据条件查询要下载的集合
	 * 
	 * @param rName
	 * @return
	 */
	public List<Map<String, Object>> getActionResourceByCondition(String rName) {
		List<Map<String, Object>> list = actionDao
				.getActionResourceByCondition(rName);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (list.size() > 0) {
			for (Map<String, Object> actionMap : list) {
				Map<String, Object> resultActionMap = new HashMap<String, Object>();
				String actionName = (String) actionMap.get("NAME");
				String[] actionIndex = actionName.split("/");
				if (actionIndex.length > 2) {
					String modelname = actionIndex[0];
					String sonmodelname = actionIndex[1];
					resultActionMap.put("MODELNAME", modelname);
					resultActionMap.put("SONMODELNAME", sonmodelname);
				} else {
					resultActionMap.put("MODELNAME", "无");
					resultActionMap.put("SONMODELNAME", "无");
				}
				resultActionMap.put("ID", actionMap.get("ID"));
				resultActionMap.put("LABLE", actionMap.get("LABLE"));
				resultActionMap.put("CTIME", actionMap.get("CTIME"));
				resultActionMap.put("UTIME", actionMap.get("UTIME"));
				resultActionMap.put("CUSER", actionMap.get("CUSER"));
				resultActionMap.put("MUSER", actionMap.get("MUSER"));
				resultActionMap.put("ISCHECK", actionMap.get("ISCHECK"));
				resultActionMap.put("URL", actionMap.get("URL"));
				resultActionMap.put("STATE", actionMap.get("STATE"));
				if (actionMap.get("COMMENTS") != null
						&& !"".equals(actionMap.get("COMMENTS"))) {
					resultActionMap.put("COMMENTS", actionMap.get("COMMENTS"));
				} else {
					resultActionMap.put("COMMENTS", "无");
				}
				resultList.add(resultActionMap);
			}
		}
		return resultList;
	}
}
