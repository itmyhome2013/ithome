package com.farm.console.prisist.dao;

import java.util.List;
import java.util.Map;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;


/**
 * userDao接口
 * 
 * @author 王东
 * 
 */
public interface UserDaoInter {
	// 根据用户名取出password
	public AloneUser getPasswordByLoginName(String loginname);

	// 获得所有数据库用户
	public List<AloneUser> getAllUser();

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(AloneUser entity);

	/**
	 * 由id获得一个实体
	 * 
	 * @param id
	 * @return
	 */
	public AloneUser getEntity(String id);

	/**
	 * 由loginName获得一个实体
	 * 
	 * @param id
	 * @return
	 */
	public AloneUser getEntityByLoginName(String LoginName);

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 */
	public String insertEntity(AloneUser entity);

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
	public void editEntity(AloneUser entity);

	/**
	 * 是否有登录名存在
	 * 
	 * @param LoginName
	 *            登录名
	 * @return true存在
	 */
	public boolean isExist(String LoginName);

	/**
	 * 获得用户组织id
	 * 
	 * @param id用户id
	 * @return
	 */
	public List<String> getUserOrg(String id);

	/**
	 * 查找该人员所有的组织机构
	 * 
	 * @param userId
	 * @author zhang_hc
	 * @time 2012-9-6 下午12:15:51
	 */
	public List<AloneUser> findAllOrg(String userId);

	/**
	 * 查找主要组织机构
	 * 
	 * @param userId
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-6 下午12:48:27
	 */
	public AloneOrganization findMainOrg(String userId);

	/**
	 * 查找次要组织机构
	 * 
	 * @param userId
	 * @return 次要组织机构集合
	 * @author zhang_hc
	 * @time 2012-9-6 下午01:34:20
	 */
	public List<AloneOrganization> findUnMianOrgList(String userId);
	/**
	 * 获取所有岗位是队长的人员信息
	 * @return
	 */
	public List<Map<String, Object>> findAllHeader();

	/**
	 * 查找所有瓦检员
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-11-26 上午11:55:45
	 */
	public List<Map<String, Object>> findAllCh4Man();

	/**
	 * 查找所有安全员
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-11-26 上午11:55:30
	 */
	public List<Map<String, Object>> findAllSafeMan();
}
