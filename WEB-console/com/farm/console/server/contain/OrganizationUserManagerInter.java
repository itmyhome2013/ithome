package com.farm.console.server.contain;

import com.farm.console.prisist.domain.AloneOrganizationUser;


public interface OrganizationUserManagerInter {
	/**
	 * 通过一个键获得一个应用配置字符串值
	 */
	public String getConfigValue(String key);

	/**
	 * 初始化应用配置集合
	 */
	public boolean initConfig();

	/**
	 * 强制刷新配置集合
	 */
	public boolean refreshConfigMap();

	public void deleteEntity(String entity);

	public void editEntity(AloneOrganizationUser entity);

	public AloneOrganizationUser getEntity(String id);

	public void insertEntity(AloneOrganizationUser entity);

	/**
	 * 查找组织机构和人员是否已有对应关系
	 * 
	 * @param orgId ：组织机构ID
	 * @param userId ：人员ID
	 * @return 存在：true；不存在：false
	 * @author zhang_hc
	 * @time 2012-9-4 下午04:30:19
	 */
	public boolean isExist(String orgId, String userId);

}
