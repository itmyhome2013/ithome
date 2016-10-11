package com.ithome.pcs.business.dao;

import java.util.List;
import java.util.Map;

import com.ithome.pcs.comm.entity.ActExTask;

/**
 * 实体管理
 * @author 赵克俭(新增)
 *
 */
public interface ActExTaskDaoInter {

	public void insertEntity(ActExTask entity);

	public void deleteAllEntity();

	/**
	 * 根据条件查询数据(单个条件)
	 * @param string
	 * @param deploymentId
	 * @return
	 */
	public List<ActExTask> findByCondition(String string, String deploymentId);
	
	/**
	 * 多条件查询数据 条件为空，查询所有
	 * @param conditions
	 * @return
	 */
	public List<ActExTask> findByConditions(Map<String, String> conditions);

	/**
	 * 条件删除数据 （conditions为null删除所有的数据）
	 * @param conditions
	 */
	public void deleteByConditions(Map<String, String> conditions);

	/**
	 * 更新数据
	 * @param task
	 */
	public void updateEntity(ActExTask task);

	
}
