package com.ithome.autoform.dao;

import com.ithome.autoform.domain.FrmProcessForm;


/**
 * 表基础信息
 * 
 * @author hxx
 * 
 */
public interface FrmProcessFormDaoInter {
	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(FrmProcessForm entity);

	/**
	 * 由id获得一个实体
	 * 
	 * @param id
	 * @return
	 */
	public FrmProcessForm getEntity(String id);

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 */
	public FrmProcessForm insertEntity(FrmProcessForm entity);

	/**
	 * 修改一个记录
	 * 
	 * @param entity
	 */
	public void editEntity(FrmProcessForm entity);
}