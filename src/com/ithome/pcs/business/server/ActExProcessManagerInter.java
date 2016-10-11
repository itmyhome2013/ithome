package com.ithome.pcs.business.server;

import java.math.BigDecimal;

import com.ithome.pcs.comm.entity.ActExProcess;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 *
 */
public interface ActExProcessManagerInter {
	/**
	 *新增实体管理实体
	 * 
	 * @param entity
	 */
	public ActExProcess insertActExProcessEntity(ActExProcess entity,AloneUser user);
	/**
	 *修改实体管理实体
	 * 
	 * @param entity
	 */
	public ActExProcess editActExProcessEntity(ActExProcess entity,AloneUser user);
	/**
	 *删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteActExProcessEntity(String id,AloneUser user);
	/**
	 *获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ActExProcess getActExProcessEntity(String id);
	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createActExProcessSimpleQuery(DataQuery query);
}