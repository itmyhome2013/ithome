package com.farm.console.server.contain;

import java.util.List;
import java.util.Vector;


import com.farm.console.prisist.dao.OrganizationUserDaoInter;
import com.farm.console.prisist.domain.AloneOrganizationUser;

public class OrganizationUserManagerImpl implements
		OrganizationUserManagerInter {
	private OrganizationUserDaoInter organizationuserDao;
	private static final List<AloneOrganizationUser> OrganizationUserList = new Vector<AloneOrganizationUser>();

	public String getConfigValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean initConfig() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean refreshConfigMap() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.macpro.alone.server.contain.dd$deleteEntity(java.lang.String)
	 */
	public void deleteEntity(String entity) {
		// TODO Auto-generated method stubFd
		organizationuserDao.deleteEntity(organizationuserDao.getEntity(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$editEntity(org.macpro.alone.prisist
	 * .domain.AloneOrganizationUser)
	 */
	public void editEntity(AloneOrganizationUser entity) {
		// TODO Auto-generated method stub
		organizationuserDao.editEntity(entity);
	}

	public List<AloneOrganizationUser> getAllEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAllListNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.macpro.alone.server.contain.dd$getEntity(java.lang.String)
	 */
	public AloneOrganizationUser getEntity(String id) {
		// TODO Auto-generated method stub
		return organizationuserDao.getEntity(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.macpro.alone.server.contain.dd$insertEntity(org.macpro.alone.prisist
	 * .domain.AloneOrganizationUser)
	 */
	public void insertEntity(AloneOrganizationUser entity) {
		organizationuserDao.insertEntity(entity);
	}

	@Override
	public boolean isExist(String orgId, String userId) {
		List<AloneOrganizationUser> orgUserList = organizationuserDao
				.findEntityByOrgUser(orgId, userId);
		return orgUserList.size() > 0;
	}

	public static List<AloneOrganizationUser> getorganizationuserList() {
		return OrganizationUserList;
	}

	public OrganizationUserDaoInter getorganizationuserDao() {
		return organizationuserDao;
	}

	public void setorganizationuserDao(
			OrganizationUserDaoInter organizationuserDao) {
		this.organizationuserDao = organizationuserDao;
	}
}
