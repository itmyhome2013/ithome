package com.ithome.autoform.server;

import java.util.List;
import com.ithome.autoform.domain.FrmTaskUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;

/**
 * 表基础信息
 * 
 * 
 */
public interface FrmTaskUserManagerInter {
	
	/**
	 * 查询任务节点 
	 * @param query
	 * @return
	 */
	public DataQuery createFrmTaskUserSimpleQuery(DataQuery query);
	/**
	 * 查询所有人员信息
	 * @param query
	 * @return
	 */
	public DataQuery queryAllUser(DataQuery query);
	
	/**
	 * 查询所有小组
	 * @param query
	 * @return
	 */
	public DataQuery queryAllGroup(DataQuery query);
	
	/**
	 * 查询所有角色
	 * @param query
	 * @return
	 */
	public DataQuery queryAllRole(DataQuery query);
	
	/**
	 * 根据任务key和类型 获得所有人员或角色或组别
	 * @param taskkey
	 * @return
	 */
	public String getUserOrgGroupIdByTaskKey(String taskkey,String selectivetype);
	
	public FrmTaskUser insertFrmTaskUserEntity(FrmTaskUser entity);
	
	public void deleteEntitys(List<DBRule> rules);
	
	
}