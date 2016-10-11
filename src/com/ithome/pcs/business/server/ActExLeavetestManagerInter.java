package com.ithome.pcs.business.server;

import com.ithome.pcs.comm.entity.ActExCompletedform;
import com.ithome.pcs.comm.entity.ActExLeavetest;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 *
 */
public interface ActExLeavetestManagerInter {
	
	/**
	 * 
	 * @方法名称: insertLeavetestandCompletedform
	 * @描述：TODO(新增实体管理实体并把信息填写到业务流程已填表单) 
	 * @返回值类型： void  
	 * @param actExLeavetest
	 * @param actExCompletedform
	 * @param user
	 */
	public void insertLeavetestandCompletedform(ActExLeavetest actExLeavetest,ActExCompletedform actExCompletedform,AloneUser user);

	/**
	 *新增实体管理实体
	 * 
	 * @param entity
	 */
	public ActExLeavetest insertActExLeavetestEntity(ActExLeavetest entity,AloneUser user);
	/**
	 *修改实体管理实体
	 * 
	 * @param entity
	 */
	public ActExLeavetest editActExLeavetestEntity(ActExLeavetest entity,AloneUser user);
	/**
	 *删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteActExLeavetestEntity(String id,AloneUser user);
	/**
	 *获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ActExLeavetest getActExLeavetestEntity(String id);
	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createActExLeavetestSimpleQuery(DataQuery query);
}