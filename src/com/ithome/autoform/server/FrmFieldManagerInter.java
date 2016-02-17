package com.ithome.autoform.server;

import java.util.List;
import java.util.Map;

import com.ithome.autoform.domain.CompletedForm;
import com.ithome.autoform.domain.FrmField;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
 /**
 * 实体管理类功能说明
 *
 * @author 作者:自动生成
 * @version 日期(用日期代替版本号)+版本备注
 */ 
public interface FrmFieldManagerInter {
	/**
	 *新增实体管理实体
	 * 
	 * @param entity
	 */
	public FrmField insertFrmFieldEntity(FrmField entity,AloneUser user);
	
	/**
	 * 新增操作表单
	 * @param map  表单集合
	 * @param completedForm  已填业务表单实体
	 */
	public FrmField insertFrmOperateFieldEntity(Map<String,String> map,CompletedForm completedForm, AloneUser user);
	
	/**
	 * 修改操作表单
	 * @param map 表单集合
	 * @param completedformid 已填业务表单主键
	 */
	public FrmField editFrmOperateFieldEntity(Map<String,String> map,String completedformid,AloneUser user);
	
	/**
	 *修改实体管理实体
	 * 
	 * @param entity
	 */
	public FrmField editFrmFieldEntity(FrmField entity,AloneUser user);
	/**
	 *删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteFrmFieldEntity(String entity,AloneUser user);
	
	/**
	 * 删除操作表单
	 * @param tableid 表单ID
	 * @param id  主键ID
	 */
	public void deleteFrmOperateFieldEntity(String tableid,String id);
	/**
	 *获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public FrmField getFrmFieldEntity(String id);
	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createFrmFieldSimpleQuery(DataQuery query,String tableid);
	
	
	/**
	 * 创建一个查询自定义表单基本查询
	 * @param query
	 * @param tableName 表名
	 * @param fields  查询的字段
	 * @return
	 */
	public DataQuery createFrmOperateFormSimpleQuery(DataQuery query,String tableName,String fields);
	
	/**
	 * 根据表ID查询所有字段信息
	 * @param tableid
	 * @return
	 */
	public List<FrmField> querFrmFieldsByTableId(String tableid);
}