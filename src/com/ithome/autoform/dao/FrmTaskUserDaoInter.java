package com.ithome.autoform.dao;

import java.util.List;

import com.ithome.autoform.domain.FrmTaskUser;

import com.farm.core.sql.query.DBRule;



/**
 * 任务节点配置
 * 
 * @author huxuxu
 * 
 */
public interface FrmTaskUserDaoInter {
	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 */
	public FrmTaskUser insertEntity(FrmTaskUser entity);
	
	/**
	 * 条件删除实体，依据对象字段值
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);
}