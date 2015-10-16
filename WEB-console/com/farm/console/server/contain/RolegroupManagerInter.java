package com.farm.console.server.contain;

import java.util.List;
import java.util.Map;


import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;


public interface RolegroupManagerInter {
	

	public void deleteEntity(String entity);

	public void editEntity(AloneRolegroup entity);

	public AloneRolegroup getEntity(String id);

	public void insertEntity(AloneRolegroup entity);
	
	/** 为角色添加菜单权限，并删除之前的菜单权限
	 * @param menuList 菜单主键集合
	 */
	public void reSetMenu(List<String> menuList,String rolegroup);
	/**获得用户的权限组序列
	 * @param userid 用户id
	 * @return
	 */
	public List<AloneRolegroup> getRoles(String userid);
	/**获得角色的所有权限
	 * @param roleId
	 * @return
	 */
	public List<Map<String, String>> getRoleAction(String roleId);

	/**
	 * 删除实体
	 * @param name
	 * @param currentUser
	 */
	public void deleteEntity(String name, AloneUser currentUser);

	/**
	 * 修改实体
	 * @param entity
	 * @param currentUser
	 * @return
	 */
	public AloneRolegroup editEntity(AloneRolegroup entity,
			AloneUser currentUser);

	/**
	 * 新增实体
	 * @param entity
	 * @param currentUser
	 * @return
	 */
	public AloneRolegroup insertEntity(AloneRolegroup entity,
			AloneUser currentUser);

	public DataResult initTree(DataQuery query, DBSort sort, String string,
			String string2);

	public DataResult addTreeNode(DataQuery query, DBSort sort,
			String aloneContext, String string, String string2);

	/**为用户设置角色权限
	 * @param ruleGroupIds 权限角色主键序列，逗号分隔
	 * @param userid 设置用户id
	 */
	public void setRuleGroupForUser(String ruleGroupIds, String userid);
}
