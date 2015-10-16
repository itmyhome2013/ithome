package com.farm.console.server.contain;

import java.util.List;
import java.util.Vector;


import com.farm.console.prisist.dao.MenuRolegroupDaoInter;
import com.farm.console.prisist.domain.AloneMenuRolegroup;

public class MenuRolegroupManagerImpl implements MenuRolegroupManagerInter{
	private MenuRolegroupDaoInter  menurolegroupDao;
	private static final List<AloneMenuRolegroup> MenuRolegroupList=new Vector<AloneMenuRolegroup>();

	/* (non-Javadoc)
	 * @see org.macpro.alone.server.contain.dd$deleteEntity(java.lang.String)
	 */
	public void deleteEntity(String entity) {
		// TODO Auto-generated method stubFd
		menurolegroupDao.deleteEntity(menurolegroupDao.getEntity(entity));
	}

	/* (non-Javadoc)
	 * @see org.macpro.alone.server.contain.dd$editEntity(org.macpro.alone.prisist.domain.AloneMenuRolegroup)
	 */
	public void editEntity(AloneMenuRolegroup entity) {
		// TODO Auto-generated method stub
		menurolegroupDao.editEntity(entity);
	}

	public List<AloneMenuRolegroup> getAllEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAllListNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.macpro.alone.server.contain.dd$getEntity(java.lang.String)
	 */
	public AloneMenuRolegroup getEntity(String id) {
		// TODO Auto-generated method stub
		return menurolegroupDao.getEntity(id);
	}

	/* (non-Javadoc)
	 * @see org.macpro.alone.server.contain.dd$insertEntity(org.macpro.alone.prisist.domain.AloneMenuRolegroup)
	 */
	public void insertEntity(AloneMenuRolegroup entity) {
		// TODO Auto-generated method stub
		menurolegroupDao.insertEntity(entity);
	}

	

	public static List<AloneMenuRolegroup> getmenurolegroupList() {
		return MenuRolegroupList;
	}

	public MenuRolegroupDaoInter getmenurolegroupDao() {
		return menurolegroupDao;
	}

	public void setmenurolegroupDao(MenuRolegroupDaoInter menurolegroupDao) {
		this.menurolegroupDao = menurolegroupDao;
	}

}
