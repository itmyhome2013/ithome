package com.farm.console.prisist.dao;

import java.util.List;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneOrganizationUser;
import com.farm.console.prisist.domain.AloneUser;


public interface OrganizationUserDaoInter {
	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(AloneOrganizationUser entity);

	/**
	 * 由id获得一个实体
	 * 
	 * @param id
	 * @return
	 */
	public AloneOrganizationUser getEntity(String id);

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 */
	public void insertEntity(AloneOrganizationUser entity);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个记录
	 * 
	 * @param entity
	 */
	public void editEntity(AloneOrganizationUser entity);

	/**
	 * 删除一个用户的所有组织关系
	 * 
	 * @param rolegroup
	 */
	public void deleteAllEntity(AloneUser user);
	
	/**删除所有的主要组织机构
	 * @param user
	 */
	public void deleteAllMainEntity(String userid);

	/**
	 * 删除一个组织的所有用户关系
	 * 
	 * @param rolegroup
	 */
	public void deleteAllEntity(AloneOrganization organization);

	/**
	 * 编辑用户组织
	 * 
	 * @param id
	 *            用户id
	 * @param orgList
	 *            组织机构id列表
	 */
	public void editEntity(String id, List<String> orgList);

	/**
	 * 根据组织机构ID和人员ID，查找是否有对应的实体
	 * 
	 * @param orgId
	 * @param userId
	 * @author zhang_hc
	 * @time 2012-9-4 下午04:33:44
	 */
	public List<AloneOrganizationUser> findEntityByOrgUser(String orgId, String userId);

	/**根据组织机构ID和人员ID，删除对应的中间表
	 * @param userId 用户id
	 * @param orgid 组织机构id
	 */
	public void deleteUserOrg(String userId, String orgid);
}
