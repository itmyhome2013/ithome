package com.farm.console.prisist.dao;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneOrganizationUser;
import com.farm.console.prisist.domain.AloneUser;

public class OrganizationUserDao implements OrganizationUserDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneOrganizationUser entity) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from Alone_Organization_User");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneOrganizationUser getEntity(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		return (AloneOrganizationUser) session.get(AloneOrganizationUser.class,
				id);
	}

	public void insertEntity(AloneOrganizationUser entity) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneOrganizationUser entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@Override
	public void deleteAllEntity(AloneUser user) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from alone_organization_user  where userid=?");
		sqlquery.setString(0, user.getId());
		sqlquery.executeUpdate();
	}

	@Override
	public void deleteAllEntity(AloneOrganization organization) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from alone_organization_user  where organizationid=?");
		sqlquery.setString(0, organization.getId());
		sqlquery.executeUpdate();
	}

	@Override
	public void editEntity(String id, List<String> orgList) {
		// 删除所有用户组织对应
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from alone_organization_user  where userid=?");
		sqlquery.setString(0, id);
		sqlquery.executeUpdate();
		// 加入现有对应
		for (Iterator<String> iterator = orgList.iterator(); iterator.hasNext();) {
			String orgId = (String) iterator.next();
			AloneOrganizationUser orgUser = new AloneOrganizationUser(id,
					orgId, "0");
			session.save(orgUser);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganizationUser> findEntityByOrgUser(String orgId,
			String userId) {
		String hql = "from AloneOrganizationUser a where a.organizationid = ? and a.userid = ?";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, orgId).setString(1, userId);
		return query.list();
	}

	@Override
	public void deleteAllMainEntity(String userid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from alone_organization_user  where type='1' and userid=?");
		sqlquery.setString(0, userid);
		sqlquery.executeUpdate();
	}

	@Override
	public void deleteUserOrg(String userId, String orgid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from alone_organization_user  where organizationid=? and userid=?");
		sqlquery.setString(0, orgid);
		sqlquery.setString(1, userId);
		sqlquery.executeUpdate();
	}
}
