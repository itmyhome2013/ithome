package com.farm.web.adapter;

import java.util.List;
import java.util.Map;

import com.farm.console.contain.exception.UserNoExistException;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.console.prisist.domain.AloneUser;

/**
 * 权限系统适配器、提供权限、菜单、用户、组织机构的管理功能
 * 
 * @author 王东
 * @date 2013-7-2
 */
public interface AuthAdapterInter {
	/**
	 * 根据用户名密码验证用户合法性
	 * 
	 * @throws UserNoExistException
	 */
	public boolean isLegality(String loginname, String password)
			throws UserNoExistException;

	/**
	 * 根据用户登录名获得用户对象
	 */
	public AloneUser getEntityByLoginName(String loginname);

	/**
	 * 根据用户id获得用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public AloneUser getUserById(String userId);

	/**
	 * 获得用户所有受检权限
	 * 
	 * @return id(权限id),url（权限地址）
	 */
	public Map<String, String> getUserAction(String userid,String password);

	/**
	 * 获得用户的权限组序列
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	public List<AloneRolegroup> getRoles(String userid);

	/**
	 * 获得用户菜单
	 * 
	 * @param UserId
	 * @return TREECODE(树索引值),TYPE（类型）, URL（链接地址）,ID（主键）, IMG（头像地址）,
	 *         SORT（排序）,PARENTID（父组织机构）, NAME（名称）
	 */
	public List<Map<String, Object>> getUserMenu(String userid,String password);

	/**
	 * 根据用户id查询所属组织机构
	 * 
	 * @param id
	 * @return
	 */
	public AloneOrganization getMainOrgByUserId(String userid);

	/**
	 * 用户登录(回调方法)
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	public void doLogin(String userid);

	/**
	 * 获得系统中所有组织机构
	 * 
	 * @return ID主键, TREECODE索引码, COMMENTS备注, NAME名称, PARENTID父节点, SORT排序,
	 *         TYPE类型
	 */
	public List<Map<String, Object>> getAllOrg();

	/**
	 * 获得某个组织机构下的全部人(用于流程配置时选择本单位下所有人员)
	 * 
	 * @param orgId
	 *            组织机构id
	 * @return ID主键, NAME姓名
	 */
	public List<Map<String, Object>> getUserByOrg(String orgId);
	
	
	/**
	 * 修改用户密码
	 * @param id 用户id
	 * @param oldPassword 旧密码
	 * @param newpassword 新密码
	 * @return
	 */
	public boolean editPassword(String id, String oldPassword,
			String newpassword);
	
}
