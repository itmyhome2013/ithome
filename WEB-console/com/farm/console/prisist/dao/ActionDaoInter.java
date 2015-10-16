package com.farm.console.prisist.dao;

import java.util.List;
import java.util.Map;


import com.farm.console.prisist.domain.AloneAction;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;


public interface ActionDaoInter  {
	/** 删除一个实体
 	 * @param entity 实体
 	 */
 	public void deleteEntity(AloneAction entity) ;
	/** 由id获得一个实体
	 * @param id
	 * @return
	 */
 	public List<AloneAction> getAllEntity();
 	public List<AloneAction> getAllcheckEntity();
 	public DataResult runSqlQuery(DataQuery query);
	public AloneAction getEntity(String id) ;
	/** 插入一条数据
	 * @param entity
	 */
	public AloneAction insertEntity(AloneAction entity);
	/** 获得记录数量
	 * @return
	 */
	public int getAllListNum();
	/**修改一个记录
	 * @param entity
	 */
	public void editEntity(AloneAction entity);
	/**
	 * 根据名称和时间查询下载集合
	 * @param rName
	 * @param rTime
	 * @return
	 */
	public List<Map<String, Object>> getActionResourceByCondition(String rName);
}
