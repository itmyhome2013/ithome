package com.ithome.autoform.dao;

import com.ithome.autoform.domain.FrmTable;


/**
 * 表基础信息
 * 
 * @author hxx
 * 
 */
public interface FrmTableDaoInter {
	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(FrmTable entity);

	/**
	 * 由id获得一个实体
	 * 
	 * @param id
	 * @return
	 */
	public FrmTable getEntity(String id);

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 */
	public FrmTable insertEntity(FrmTable entity);

	/**
	 * 修改一个记录
	 * 
	 * @param entity
	 */
	public void editEntity(FrmTable entity);
}