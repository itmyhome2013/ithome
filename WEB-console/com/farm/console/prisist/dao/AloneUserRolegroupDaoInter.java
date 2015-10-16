package com.farm.console.prisist.dao;

import org.hibernate.Session;

import com.farm.console.prisist.domain.AloneUserRolegroup;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
/**rolegroup
 * @author MAC_wd
 *
 */
public interface AloneUserRolegroupDaoInter  {
	/** 删除一个实体
 	 * @param entity 实体
 	 */
 	public void deleteEntity(AloneUserRolegroup entity) ;
	/** 由id获得一个实体
	 * @param id
	 * @return
	 */
	public AloneUserRolegroup getEntity(String id) ;
	/** 插入一条数据
	 * @param entity
	 */
	public AloneUserRolegroup insertEntity(AloneUserRolegroup entity);
	/** 获得记录数量
	 * @return
	 */
	public int getAllListNum();
	/**修改一个记录
	 * @param entity
	 */
	public void editEntity(AloneUserRolegroup entity);
	/**获得一个session
	 */
	public Session getSession();
	/**执行一条查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);
	/**删除用户的所有角色
	 * @param userid 用户id
	 */
	public void removeUserAllRuleGroups(String userid);
}