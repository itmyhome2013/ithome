package com.farm.console.prisist.dao;

import com.farm.console.prisist.domain.AloneMenu;
import com.farm.console.prisist.domain.AloneMenuRolegroup;
import com.farm.console.prisist.domain.AloneRolegroup;


public interface MenuRolegroupDaoInter  {
	/** 删除一个实体
 	 * @param entity 实体
 	 */
 	public void deleteEntity(AloneMenuRolegroup entity) ;
 	
 	/**删除一个角色的所有权限
 	 * @param rolegroup
 	 */
 	public void deleteAllEntity(AloneRolegroup rolegroup);
 	/**删除一个权限菜单的所有角色
 	 * @param rolegroup
 	 */
 	public void deleteAllEntity(AloneMenu menu);
	/** 由id获得一个实体
	 * @param id
	 * @return
	 */
	public AloneMenuRolegroup getEntity(String id) ;
	/** 插入一条数据
	 * @param entity
	 */
	public void insertEntity(AloneMenuRolegroup entity);
	/** 获得记录数量
	 * @return
	 */
	public int getAllListNum();
	/**修改一个记录
	 * @param entity
	 */
	public void editEntity(AloneMenuRolegroup entity);
}
