package com.ithome.popu.server;

import java.math.BigDecimal;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
import com.ithome.popu.domain.PopuHuInfo;

/**
 * 房屋基础信息
 * 
 * @author itmyhome
 * 
 */
public interface PopuHuInfoManagerInter {
	/**
	 *新增房屋基础信息实体
	 * 
	 * @param entity
	 */
	public PopuHuInfo insertPopuHuInfoEntity(PopuHuInfo entity, AloneUser user);

	/**
	 *修改房屋基础信息实体
	 * 
	 * @param entity
	 */
	public PopuHuInfo editPopuHuInfoEntity(PopuHuInfo entity, AloneUser user);

	/**
	 *删除房屋基础信息实体
	 * 
	 * @param entity
	 */
	public void deletePopuHuInfoEntity(BigDecimal id, AloneUser user);

	/**
	 *删除房屋基础信息实体(逻辑删除)
	 * 
	 * @param entity
	 */
	public void deletePopuHuInfoEntityByLogic(BigDecimal id, AloneUser user);

	/**
	 *获得房屋基础信息实体
	 * 
	 * @param id
	 * @return
	 */
	public PopuHuInfo getPopuHuInfoEntity(BigDecimal id);

	/**
	 * 创建一个基本查询用来查询当前房屋基础信息实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	/**
	 * @param query
	 * @return
	 */
	public DataQuery createPopuHuInfoSimpleQuery(DataQuery query, AloneOrganization org);
	
	/**
	 * 身份证唯一性校验
	 * @param idCard 身份证号码
	 * @param huid 户ID
	 * @return
	 */
	public boolean idCardCheckOnly(String idCard,String huid);
	
}