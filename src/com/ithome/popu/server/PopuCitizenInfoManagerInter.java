package com.ithome.popu.server;

import java.math.BigDecimal;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
import com.ithome.popu.domain.PopuCitizenInfo;
/**人口基础信息
 * @author MAC_wd
 *
 */
public interface PopuCitizenInfoManagerInter {
	/**
	 *新增人口基础信息实体
	 * 
	 * @param entity
	 */
	public PopuCitizenInfo insertPopuCitizenInfoEntity(PopuCitizenInfo entity,AloneUser user);
	/**
	 *修改人口基础信息实体
	 * 
	 * @param entity
	 */
	public PopuCitizenInfo editPopuCitizenInfoEntity(PopuCitizenInfo entity,AloneUser user);
	/**
	 *删除人口基础信息实体
	 * 
	 * @param entity
	 */
	public void deletePopuCitizenInfoEntity(BigDecimal id,AloneUser user);
	/**
	 *删除人口基础信息实体(逻辑删除)
	 * 
	 * @param entity
	 */
	public void deletePopuCitizenInfoEntityByLogic(BigDecimal id,AloneUser user);
	/**
	 *获得人口基础信息实体
	 * 
	 * @param id
	 * @return
	 */
	public PopuCitizenInfo getPopuCitizenInfoEntity(BigDecimal id);
	/**
	 * 创建一个基本查询用来查询当前人口基础信息实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPopuCitizenInfoSimpleQuery(DataQuery query);
	/**
	 * 创建一个基本查询用来查询当前人口基础信息实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPopuCitizenInfoSimpleQuery(DataQuery query,AloneOrganization org);
	
	/**
	 * 创建一个基本查询用来查询当前社区党员基础信息实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPopuPartymemberInfoSimpleQuery(DataQuery query,AloneOrganization org);
	/**
	 * 创建一个基本查询用来查询当前低保人员基础信息实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPopuLowSafeInfoSimpleQuery(DataQuery query,AloneOrganization org);
	
	/**
	 * 身份证唯一性校验
	 * @param idCard 身份证号码
	 * @param citizenid 用户ID
	 * @return
	 */
	public boolean idCardCheckOnly(String idCard,String citizenid,BigDecimal huid);
	
}