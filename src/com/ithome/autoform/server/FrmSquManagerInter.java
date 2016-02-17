package com.ithome.autoform.server;

import com.ithome.autoform.domain.FrmSqu;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
 /**
 * 实体管理类功能说明
 *
 * @author 作者:自动生成
 * @version 日期(用日期代替版本号)+版本备注
 */ 
public interface FrmSquManagerInter {
	/**
	 *新增实体管理实体
	 * 
	 * @param entity
	 */
	public FrmSqu insertFrmSquEntity(FrmSqu entity,AloneUser user);
	/**
	 *修改实体管理实体
	 * 
	 * @param entity
	 */
	public FrmSqu editFrmSquEntity(FrmSqu entity,AloneUser user);
	/**
	 *删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteFrmSquEntity(String entity,AloneUser user);
	/**
	 *获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public FrmSqu getFrmSquEntity(String id);
	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createFrmSquSimpleQuery(DataQuery query);
}