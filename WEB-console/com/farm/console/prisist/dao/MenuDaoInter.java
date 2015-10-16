package com.farm.console.prisist.dao;


import com.farm.console.prisist.domain.AloneMenu;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;


public interface MenuDaoInter  {
	/** 删除一个实体
 	 * @param entity 实体
 	 */
 	public void deleteEntity(AloneMenu entity) ;
	/** 由id获得一个实体
	 * @param id
	 * @return
	 */
	public AloneMenu getEntity(String id) ;
	/** 由treeCode获得一个实体
	 * @param id
	 * @return
	 */
	public AloneMenu getEntityByTreeCode(String treeCode) ;
	/** 插入一条数据
	 * @param entity
	 */
	public AloneMenu insertEntity(AloneMenu entity);
	/** 获得记录数量
	 * @return
	 */
	public int getAllListNum();
	/**修改一个记录
	 * @param entity
	 */
	public void editEntity(AloneMenu entity);
	/**
	 * 加载菜单权限动态树
	 * @param query
	 * @return
	 */
	public DataResult loadTreeNode(DataQuery query);
}
