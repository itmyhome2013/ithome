package com.ithome.autoform.server;

import java.io.File;

import com.ithome.autoform.domain.FrmProcessForm;
import com.ithome.autoform.domain.FrmTable;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;

/**
 * 表基础信息
 * 
 * 
 */
public interface FrmProcessFormManagerInter {
	/**
	 *新增表基础信息实体
	 * 
	 * @param entity
	 */
	public FrmProcessForm insertFrmProcessFormEntity(FrmProcessForm entity, AloneUser user);

	/**
	 *修改表基础信息实体
	 * 
	 * @param entity
	 */
	public FrmProcessForm editFrmProcessFormEntity(FrmProcessForm entity, AloneUser user);

	/**
	 *删除表基础信息实体
	 * 
	 * @param entity
	 */
	public void deleteFrmProcessFormEntity(String id);
	
	/**
	 *删除表单基础信息实体(逻辑删除)
	 * 
	 * @param entity
	 */
	public void deleteFrmProcessFormEntityByLogic(String id);

	/**
	 *获得表基础信息实体
	 * 
	 * @param id
	 * @return
	 */
	public FrmProcessForm getFrmProcessFormEntity(String id);
	
	/**
	 * 创建一个基本查询用来查询表单基础信息实体
	 * @param query
	 * @param org
	 * @return
	 */
	public DataQuery createFrmProcessFormSimpleQuery(DataQuery query);
	

}