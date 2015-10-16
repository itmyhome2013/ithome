package com.farm.console.prisist.dao;

import java.util.List;
import java.util.Map;

import com.farm.console.prisist.domain.AloneOrganization;


public interface OrganizationDaoInter {
	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(AloneOrganization entity);

	/**
	 * 由id获得一个实体
	 * 
	 * @param id
	 * @return
	 */
	public AloneOrganization getEntity(String id);

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 */
	public AloneOrganization insertEntity(AloneOrganization entity);

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
	public void editEntity(AloneOrganization entity);

	/**
	 * 获取二级单位
	 * 
	 * @return
	 */
	public List<AloneOrganization> getSecondOrgList();

	/**
	 * 根据用户id查询组织机构
	 * 
	 * @param id
	 * @return
	 */
	public AloneOrganization getMainOrgByUserId(String id);

	/**
	 * 根据树索引码逻辑删除组织机构。 逻辑删除 ：修改状态为2
	 * 
	 * @param entityId
	 * @author zhang_hc
	 * @time 2012-9-1 上午11:15:41
	 */
	public void deleteEntityByTreecode(String entityId);
	/**
	 * 根据用户id查询关联（非所属）组织机构
	 * 
	 * @param userid
	 * @return
	 */
	public List<AloneOrganization> getNotMainOrgByUserId(String userid);

	/**
	 * 根据组织机构名称查找实体集合
	 * 
	 * @param orgName ：组织机构名称
	 * @author zhang_hc
	 * @time 2012-9-7 下午05:00:31
	 */
	public List<AloneOrganization> findListByOrgName(String orgName);
	
	/**
	 * 根据组织机构名称和要排除的ID，查找实体集合
	 * 
	 * @param orgName ： 组织机构名称
	 * @param excludeOrgId ：要排除的组织机构ID 
	 * @author zhang_hc
	 * @time 2012-9-7 下午05:01:25
	 */
	public List<AloneOrganization> findListByOrgNameAndExcludeOrgId(String orgName, String excludeOrgId);

	/**
	 * 根据父组织机构ID查找实体集合
	 * 
	 * @param parentId
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-9 下午04:06:26
	 */
	public List<AloneOrganization> findListByParentId(String parentId);

	/**
	 * 查找顶级实体
	 * 
	 * @param orgId ：组织机构ID
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-9 下午07:57:58
	 */
	public AloneOrganization findTopEntity(String orgId);

	/**
	 * 根据树索引查找实体
	 * 
	 * @param orgId ：组织机构ID
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-11 上午10:49:23
	 */
	public List<AloneOrganization> findListByTreecode(String orgId);
	/**
	 * 根据组织机构类型查询组织机构集合
	 * @param type（组织类型：1科室、2班组、3队组、0其他）
	 * @return
	 */
	public List<Map<String, Object>> getOrgByType(String type);

	/**
	 * 查找顶级组织机构
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-11-19 上午09:24:53
	 */
	public List<AloneOrganization> findTopList();

	/**
	 * 修改所有子组织机构的”专业类型，为父组织机构的”专业类型“
	 * 
	 * @param parentId 父组织机构ID
	 * @param typep 专业类型（数据字典：	ORG_TYPEP）
	 * @author zhang_hc
	 * @time 2012-12-17 下午07:47:35
	 */
	public void editTypep(String parentId, String typep);

}
