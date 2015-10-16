package com.farm.console.server.contain;

import java.util.List;
import java.util.Map;


import com.farm.console.contain.exception.UserLoginNameIsExistException;
import com.farm.console.contain.exception.UserNoExistException;
import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;


public interface UserManagerInter {
	public static final String PARAMETER_PASSWORD = "DEFAULT_PASSWORD";
	public static final String DEFAULT_PASSWORD = "111111";

	/**
	 * 根据用户获得密码
	 */
	public String getPassword(String id);

	/**
	 * 根据用户登录名获得用户对象
	 */
	public AloneUser getEntityByLoginName(String loginName);

	/**
	 * 根据用户名密码验证用户合法性
	 * 
	 * @throws UserNoExistException
	 */
	public boolean isLegality(String loginname, String password)
			throws UserNoExistException;

	/**
	 * 将一个用户密码初始化
	 */
	public boolean initPassword(String id);

	/**
	 * 修改用户密码
	 */
	public boolean editPassword(String id, String oldPassword,
			String newpassword);

	/**
	 * 用户设置为管理员
	 * @param id 被操作用户
	 * @param currentuser 当前用户
	 * @return
	 */
	public boolean updateToAdminFromUser(String id, AloneUser currentuser);
	/**
	 * 从管理员中删除用户
	 * @param id 被操作用户
	 * @param currentuser 当前用户
	 * @return
	 */
	public boolean updateToRemoveAdmin(String id, AloneUser currentuser);

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void deleteEntity(String id);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @param user
	 */
	public void deleteLogicEntity(String id, AloneUser user);

	/**
	 * 查询用户
	 * 
	 * @param id
	 * @return
	 */
	public AloneUser getEntity(String id);

	/**
	 * 查询用户组织
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getUserOrg(String id);

	/**
	 * 为用户分配组织（去掉之前已有的组织）
	 * 
	 * @param Organizations
	 * @param userid
	 */
	public void reSetOrganization(List<String> Organizations, String userid);

	/**
	 * 修改用户
	 * 
	 * @param entity
	 */
	public void editEntity(AloneUser entity);

	/**
	 * 修改用户
	 * 
	 * @param entity
	 */
	public void editEntity(AloneUser entity, String orgIds);

	/**
	 * 获得所用用户
	 * 
	 * @return
	 */
	public List<AloneUser> getAllEntity();

	/**
	 * 获得用户的所有菜单权限数据
	 * 
	 * @param userid
	 *            用户id
	 * @return key<e.name as
	 *         name,e.sort,e.parentid,e.treecode,e.id,e.type,f.url,f.lable>
	 */
	public Map<String, Map<String, String>> getUserAllPopedom(String userid);

	/**
	 * 获得用户的所有权限，
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	public Map<String, AloneAction> getUserActions(String userid);

	/**
	 * 获得用户的所有菜单
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	public List<Map<String, Object>> getUserMenus(String userid);

	/**
	 * 获得菜单
	 * 
	 * @param allPop
	 *            从本类方法获得 所有权限集合
	 * @return
	 */
	public List<Map<String, Object>> getMenus(
			Map<String, Map<String, String>> allPop);

	/**
	 * 获得权限
	 * 
	 * @param allPop
	 *            从本类方法获得 所有权限集合
	 * @return
	 */
	public Map<String, Map<String, String>> getAction(
			Map<String, Map<String, String>> allPop);

	/**
	 * 用户登录
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	public void doLogin(String id);

	/**
	 * 修改用户
	 * 
	 * @param entity
	 * @param currentUser
	 */
	public AloneUser editEntity(AloneUser entity, AloneUser currentUser);

	/**
	 * @param userId
	 * @param loginName
	 * @return
	 */
	public boolean getUserListByLoginName(String userId, String loginName,
			String pageType);

	/**
	 * 插入用户
	 * 
	 * @param entity
	 * @param currentUser
	 */
	public AloneUser insertUser(AloneUser entity, AloneUser currentUser)
			throws UserLoginNameIsExistException;

	

	/**
	 * 修改用户
	 * 
	 * @param entity
	 * @param currentUser
	 * @param optionsId
	 */
	public AloneUser editEntity(AloneUser entity, AloneUser currentUser,
			String optionsId);

	/**
	 * 修改用户信息
	 * 
	 * @param entity
	 * @param orgEntity
	 * @param posEntity
	 * @param currentUser
	 */
	public void editEntity(AloneUser entity, AloneOrganization orgEntity,
			AloneUser currentUser);

	/**
	 * 用户申请账号
	 * 
	 * @param user
	 *            用户对象
	 * @param orgEntity
	 *            组织机构对象
	 * @param posEntity
	 *            岗位对象
	 * @param state
	 *            对象默认状态
	 * @return
	 * @throws UserLoginNameIsExistException
	 */
	public AloneUser register(AloneUser user, AloneOrganization orgEntity,
			String state) throws UserLoginNameIsExistException;

	/**为用户设置组织机构。如果是主要组织机构，只能设置一个，其他主要组织机构都会被删除。如果是非主要组织机构可以设置多个
	 * @param userId 用户id
	 * @param orgid 组织机构id
	 * @param isMain 是否是主要组织机构
	 */
	public void addOrg(String  userId, String orgid,boolean isMain);


	/**
	 * 查找是否是主要组织机构
	 * 
	 * @param userId
	 * @param orgId 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-5 下午06:34:41
	 */
	public Map<String, Object> findIsMainOrg(String userId, String orgId);

	/**
	 * 查找主要组织机构
	 * 
	 * @param userId
	 * @return 
	 * @author zhang_hc
	 * @time 2012-9-6 下午12:45:55
	 */
	public AloneOrganization findMianOrg(String userId);
	
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
	 * 删除组织机构和人员的关系
	 * 
	 * @param userId
	 * @param orgId
	 * @author zhang_hc
	 * @time 2012-9-6 下午03:17:48
	 */
	public void deleteOrg(String userId, String orgId);

}
