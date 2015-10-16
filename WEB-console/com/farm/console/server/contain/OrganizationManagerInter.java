package com.farm.console.server.contain;

import java.util.List;
import java.util.Map;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;


public interface OrganizationManagerInter {

	/**
	 * 强制刷新配置集合
	 */
	public boolean refreshConfigMap();

	public void deleteEntity(String entity);

	/**
	 * 逻辑删除组织机构和它的子组织机构，和它的中间表
	 * 
	 * @param entity
	 * @param user
	 * @author zhang_hc
	 * @time 2012-9-1 上午11:29:48
	 */
	public void deleteEntity(String entity, AloneUser user);

	/**
	 * 修改实体和组织机构扩展
	 * 
	 * @param id
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-4 上午09:09:57
	 */
	public AloneOrganization editEntity(AloneOrganization entity,
			AloneUser user, String contact);

	public AloneOrganization getEntity(String id);

	/**
	 * 新增实体和组织机构扩展
	 * 
	 * @param entity
	 * @param user
	 * @param contact
	 *            通讯方式
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-3 下午06:18:02
	 */
	public AloneOrganization insertEntity(AloneOrganization entity,
			AloneUser user, String contact);

	/**
	 * 为组织添加角色，并删除之前的角色
	 * 
	 * @param rolegroupList
	 *            角色列表
	 * @param organizationId
	 *            组织机构
	 */
	public void reSetMenu(List<String> rolegroupList, String organizationId);

	/**
	 * 获取二级单位
	 * 
	 * @return
	 */
	public List<AloneOrganization> getSencondOrgList();

	/**
	 * 根据用户id查询所属组织机构
	 * 
	 * @param id
	 * @return
	 */
	public AloneOrganization getMainOrgByUserId(String userid);
	/**
	 * 根据用户id查询关联（非所属）组织机构
	 * 
	 * @param userid
	 * @return
	 */
	public List<AloneOrganization>  getNotMainOrgByUserId(String userid);

	/**
	 * 验证组织机构名称是否唯一
	 * 
	 * @param orgName ：“组织机构”名称
	 * @param excludeOrgId  ：要排除的组织机构ID
	 * @return 唯一：true；不唯一：false
	 * @author zhang_hc
	 * @time 2012-9-7 下午01:00:14
	 */
	public boolean isUniqName(String orgName, String excludeOrgId);

	/**
	 * 把源组织机构A移动到，目的组织机构B下
	 * 
	 * @param fromOrgId ： 源组织机构
	 * @param toOrgId ：目的组织机构
	 * @author zhang_hc
	 * @time 2012-9-9 下午06:31:14
	 */
	public void moveOrg(String fromOrgId, String toOrgId);

	/**
	 * 是否是顶级节点
	 * 
	 * @param orgId ：组织机构ID
	 * @return 是：true；不是：false
	 * @author zhang_hc
	 * @time 2012-9-9 下午07:56:13
	 */
	public boolean isTopNode(String orgId);
	/**
	 * 根据组织机构类型查询组织机构集合
	 * @param type（组织类型：1科室、2班组、3队组、0其他）
	 * @return
	 */
	public List<Map<String, Object>> getOrgByType(String type);
	
	/**获得组织机构的所有的层级名称组合
	 * @param orgId 组织机构id
	 * @return
	 */
	public String getOrgPathString(String orgId,String splitSign);
	
	/**
	 * 查找树形的组织机构
	 * ┣生产科
	 * 		┣掘进一队
	 * 			┣...班组
	 * 		┣开一队
	 * 			┣机掘组
	 * 				┣...班组
	 * 			┣炮掘组
	 * 				┣...班组
	 * ┣通风科
	 * 		┣通风队
	 * 			┣...班组
	 * 
	 * @param topList 顶级组织机构
	 * @return
	 * @author zhang_hc
	 * @time 2012-11-16 下午05:01:19
	 */
	public List<AloneOrganization> getTreeList();
	
	public List<AloneOrganization> getTopList();
}
