package com.farm.console.server.contain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.farm.console.prisist.dao.OrganizationDaoInter;
import com.farm.console.prisist.dao.OrganizationUserDaoInter;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;

public class OrganizationManagerImpl implements OrganizationManagerInter {
	private OrganizationDaoInter organizationDao;
	private OrganizationUserDaoInter organizationuserDao;
	private OrganizationUserManagerInter orgUserManager;// 组织机构人员中间表Manager
	private UserManagerInter userManager;// 用户Manager
	/**
	 * 缓存组织机构，因为获取组织机构的全路径时占用资源太多
	 */
	private static final Map<String, AloneOrganization> orgCache = new HashMap<String, AloneOrganization>();

	@Override
	public String getOrgPathString(String orgId, String splitSign) {
		String path = "";
		String parent = orgId;
		while (parent != null && !parent.trim().toUpperCase().equals("NONE")) {
			AloneOrganization entity = null;
			if (orgCache.get(parent) != null) {
				entity = orgCache.get(parent);
			} else {
				orgCache.put(parent, getEntity(parent));
				entity = orgCache.get(parent);
			}
			if (entity == null) {
				break;
			}
			if (path.equals("")) {
				path = entity.getName();
			} else {
				path = entity.getName() + splitSign + path;
			}

			parent = entity.getParentid();
		}
		if (path == null || path.trim().length() <= 0) {
			path = "ERROR";
		}
		return path;
	}

	@Override
	public AloneOrganization insertEntity(AloneOrganization entity,
			AloneUser user, String contact) {
		// 如果没有父组织机构，设置父节点ID为”NONE“
		if (entity.getParentid() == null
				|| entity.getParentid().trim().length() <= 0) {
			entity.setParentid("NONE");
		}

		// 新增实体
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setCuser(user.getId());
		entity.setMuser(user.getId());
		entity.setTreecode("waiting....");
		entity.setType("1");
		entity.setName(entity.getName().trim());// 去组织机构名称前后空格
		entity = organizationDao.insertEntity(entity);

		// 获取新增后的实体ID，并修改树索引码
		AloneOrganization fatherEntity = getEntity(entity.getParentid());
		if (fatherEntity == null) {
			entity.setTreecode(entity.getId());
		} else {
			entity.setTreecode(fatherEntity.getTreecode() + entity.getId());
		}
		organizationDao.editEntity(entity);

		// 刷新缓存
		orgCache.clear();
		return entity;
	}

	@Override
	public AloneOrganization editEntity(AloneOrganization entity,
			AloneUser user, String contact) {

		AloneOrganization entity2 = getEntity(entity.getId());

		// 修改实体
		entity2.setName(entity.getName().trim());
		entity2.setType(entity.getType());
		entity2.setSort(entity.getSort());
		entity2.setState(entity.getState());
		entity2.setComments(entity.getComments());
		entity2.setMuser(user.getId());
		entity2.setType("1");
		entity2.setUtime(TimeTool.getTimeDate12());
		// entity2.setParentid(entity.getParentid());//普通修改时不需要
		// entity2.setTreecode(entity.getTreecode());//普通修改时不需要
		organizationDao.editEntity(entity2);

		// 刷新缓存
		orgCache.clear();
		return entity2;
	}

	public String getConfigValue(String key) {
		return null;
	}

	public boolean initConfig() {
		return false;
	}

	public boolean refreshConfigMap() {
		return false;
	}

	@Override
	public void deleteEntity(String entity, AloneUser user) {
		// 删除组织机构和它的子组织机构
		AloneOrganization orgEntity = getEntity(entity);
		organizationDao.deleteEntityByTreecode(orgEntity.getId());// org和它的子org在树索引码字段都有体现，所以根据它删除

		// 删除人员组织机构关系表
		List<AloneOrganization> orgList = organizationDao
				.findListByTreecode(orgEntity.getId());
		for (AloneOrganization orgEntity1 : orgList) {
			organizationuserDao.deleteAllEntity(orgEntity1);
		}
		// 刷新缓存
		orgCache.clear();
		// 要先删除 角色和用户的中间表
		// organizationuserDao.deleteAllEntity(org);
		// organizationDao.deleteEntity(org);
	}

	public void deleteEntity(String entity) {
		deleteEntity(entity, null);
	}

	@Override
	@SuppressWarnings("unused")
	public void reSetMenu(List<String> rolegroupList, String organizationId) {
		// 删除之前的菜单
		AloneOrganization arolegroup = organizationDao
				.getEntity(organizationId);
		// organizationRolegroupDao.deleteAllEntity(arolegroup);
		// 添加现在的菜单
		for (Iterator<String> iterator = rolegroupList.iterator(); iterator
				.hasNext();) {
			String name = (String) iterator.next();
			// AloneOrganizationRolegroup amr = new AloneOrganizationRolegroup(
			// organizationId, name);
			// organizationRolegroupDao.insertEntity(amr);
		}
	}

	@Override
	public boolean isUniqName(String orgName, String excludeOrgId) {
		List<AloneOrganization> orgList = null;
		if (excludeOrgId == null || excludeOrgId.equals("")) {
			orgList = organizationDao.findListByOrgName(orgName.trim());
		} else {
			orgList = organizationDao.findListByOrgNameAndExcludeOrgId(orgName
					.trim(), excludeOrgId);
		}
		return orgList.size() == 0;
	}

	@Override
	public void moveOrg(String fromOrgId, String toOrgId) {
		AloneOrganization fromOrgEntity;
		AloneOrganization toOrgEntity;
		toOrgId = toOrgId.trim();

		// 如果要移动到根节点
		if (toOrgId == null || toOrgId.equals("")
				|| toOrgId.toUpperCase().equals("NONE")) {
			fromOrgEntity = organizationDao.getEntity(fromOrgId);
			fromOrgEntity.setParentid("NONE");
			fromOrgEntity.setTreecode(fromOrgId);
			organizationDao.editEntity(fromOrgEntity);
			// 如果要移动到子节点
		} else {
			fromOrgEntity = organizationDao.getEntity(fromOrgId);
			toOrgEntity = organizationDao.getEntity(toOrgId);
			fromOrgEntity.setParentid(toOrgEntity.getId());
			fromOrgEntity.setTreecode(toOrgEntity.getTreecode()
					+ fromOrgEntity.getId());
			organizationDao.editEntity(fromOrgEntity);
		}

		// 移动“A”的子组织机构“C”到“A”下
		List<AloneOrganization> subOrgList = organizationDao
				.findListByParentId(fromOrgEntity.getId());
		if (subOrgList.size() > 0) {
			moveSubOrg(fromOrgEntity, subOrgList);
		}
		// 刷新缓存
		orgCache.clear();
	}

	/**
	 * 移动子节点的组织机构到父组织机构下
	 * 
	 * @param parentEntity
	 *            ：父组织机构实体
	 * @param subOrgList
	 *            ：子组织机构集合
	 * @author zhang_hc
	 * @time 2012-9-9 下午06:14:16
	 */
	public void moveSubOrg(AloneOrganization parentEntity,
			List<AloneOrganization> subOrgList) {
		for (AloneOrganization subEntity : subOrgList) {
			subEntity.setTreecode(parentEntity.getTreecode()
					+ subEntity.getId());
			// subEntity.setParentid(parentEntity.getId());//废话
			organizationDao.editEntity(subEntity);
			List<AloneOrganization> subSubenList = organizationDao
					.findListByParentId(subEntity.getId());
			if (subOrgList.size() > 0) {
				moveSubOrg(subEntity, subSubenList);
			}
		}
		// 刷新缓存
		orgCache.clear();
	}

	@Override
	public boolean isTopNode(String orgId) {
		AloneOrganization orgEntity = organizationDao.findTopEntity(orgId);
		return orgEntity != null;
	}

	@Override
	public List<Map<String, Object>> getOrgByType(String type) {
		return organizationDao.getOrgByType(type);
	}

	@Override
	public List<AloneOrganization> getTreeList() {
		List<AloneOrganization> treeList = new ArrayList<AloneOrganization>();
		walkTree(getTopList(), "┣", treeList);
		return treeList;
	}

	/**
	 * 遍历树
	 * 
	 * @param topList
	 * @param prefix
	 * @param list
	 * @author zhang_hc
	 * @time 2012-11-16 下午05:28:18
	 */
	private void walkTree(List<AloneOrganization> topList, String prefix,
			List<AloneOrganization> treeList) {
		prefix = "　" + prefix;
		for (AloneOrganization org : topList) {
			List<AloneOrganization> subList = organizationDao
					.findListByParentId(org.getId());
			AloneOrganization tempOrg = new AloneOrganization();
			tempOrg.setId(org.getId());
			tempOrg.setName(prefix + org.getName());
			treeList.add(tempOrg);
			walkTree(subList, prefix, treeList);
		}
	}

	@Override
	public List<AloneOrganization> getTopList() {
		return organizationDao.findTopList();
	}

	public List<AloneOrganization> getAllEntity() {
		return null;
	}

	public int getAllListNum() {
		return 0;
	}

	public AloneOrganization getEntity(String id) {
		if (id == null) {
			return null;
		}
		return organizationDao.getEntity(id);
	}

	public OrganizationDaoInter getorganizationDao() {
		return organizationDao;
	}

	public void setorganizationDao(OrganizationDaoInter organizationDao) {
		this.organizationDao = organizationDao;
	}

	@Override
	public AloneOrganization getMainOrgByUserId(String id) {
		AloneOrganization org = organizationDao.getMainOrgByUserId(id);
		return org;
	}

	public List<AloneOrganization> getSencondOrgList() {
		return organizationDao.getSecondOrgList();
	}

	public OrganizationUserDaoInter getOrganizationuserDao() {
		return organizationuserDao;
	}

	public void setOrganizationuserDao(
			OrganizationUserDaoInter organizationuserDao) {
		this.organizationuserDao = organizationuserDao;
	}

	public OrganizationUserManagerInter getOrgUserManager() {
		return orgUserManager;
	}

	public void setOrgUserManager(OrganizationUserManagerInter orgUserManager) {
		this.orgUserManager = orgUserManager;
	}

	public UserManagerInter getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManagerInter userManager) {
		this.userManager = userManager;
	}

	@Override
	public List<AloneOrganization> getNotMainOrgByUserId(String userid) {
		return organizationDao.getNotMainOrgByUserId(userid);
	}
}
