package com.ithome.pcs.business.server;

import java.util.List;

import com.ithome.pcs.comm.entity.ActExTaskusercfg;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 *
 */
public interface ActExTaskusercfgManagerInter {
	/**
	 *新增实体管理实体
	 * 
	 * @param entity
	 */
	public ActExTaskusercfg insertActExTaskusercfgEntity(ActExTaskusercfg entity,AloneUser user);
	/**
	 *修改实体管理实体
	 * 
	 * @param entity
	 */
	public ActExTaskusercfg editActExTaskusercfgEntity(ActExTaskusercfg entity,AloneUser user);
	/**
	 *删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteActExTaskusercfgEntity(String id,AloneUser user);
	/**
	 *获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ActExTaskusercfg getActExTaskusercfgEntity(String id);
	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createActExTaskusercfgSimpleQuery(DataQuery query);
	
	/**
	 * 查询任务下的参与人员信息
	 * 
	 * @param taskKey
	 *            任务定义KEY
	 * @return
	 */
	public List<String> getActExTaskurgBytaskKey(String taskKey);
}