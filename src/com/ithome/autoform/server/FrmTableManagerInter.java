package com.ithome.autoform.server;

import com.ithome.autoform.domain.FrmTable;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;

/**
 * 表基础信息
 * 
 * 
 */
public interface FrmTableManagerInter {
	/**
	 *新增表基础信息实体
	 * 
	 * @param entity
	 */
	public FrmTable insertFrmTableEntity(FrmTable entity, AloneUser user);

	/**
	 *修改表基础信息实体
	 * 
	 * @param entity
	 */
	public FrmTable editFrmTableEntity(FrmTable entity, AloneUser user);

	/**
	 *删除表基础信息实体
	 * 
	 * @param entity
	 */
	public void deleteFrmTableEntity(String id,String state, AloneUser user);
	
	/**
	 *删除表单基础信息实体(逻辑删除)
	 * 
	 * @param entity
	 */
	public void deleteFrmTableEntityByLogic(String id);

	/**
	 *获得表基础信息实体
	 * 
	 * @param id
	 * @return
	 */
	public FrmTable getFrmTableEntity(String id);
	
	/**
	 * 创建一个基本查询用来查询表单基础信息实体
	 * @param query
	 * @param org
	 * @return
	 */
	public DataQuery createFrmTableSimpleQuery(DataQuery query);

}