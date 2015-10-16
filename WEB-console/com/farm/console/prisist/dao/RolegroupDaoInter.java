package com.farm.console.prisist.dao;

import java.util.List;
import java.util.Map;


import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;


public interface RolegroupDaoInter  {
	/** 删除一个实体
 	 * @param entity 实体
 	 */
 	public void deleteEntity(AloneRolegroup entity) ;
	/** 由id获得一个实体
	 * @param id
	 * @return
	 */
	public AloneRolegroup getEntity(String id) ;
	/** 插入一条数据
	 * @param entity
	 */
	public void insertEntity(AloneRolegroup entity);
	/** 获得记录数量
	 * @return
	 */
	public int getAllListNum();
	/**修改一个记录
	 * @param entity
	 */
	public void editEntity(AloneRolegroup entity);
	/**获得角色的所有权限
	 * @param roleId
	 * @return
	 */
	public List<Map<String, String>> getRoleAction(String roleId);
	/**
	 * 加载角色权限树
	 * @param query
	 * @return
	 */
	public DataResult loadTreeNode(DataQuery query);
}
