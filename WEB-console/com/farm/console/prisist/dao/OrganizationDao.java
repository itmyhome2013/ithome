package com.farm.console.prisist.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.core.sql.result.DataResults;

public class OrganizationDao implements OrganizationDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneOrganization entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from Alone_Organization");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneOrganization getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneOrganization) session.get(AloneOrganization.class, id);
	}

	public AloneOrganization insertEntity(AloneOrganization entity) {
		Session session = sessionFatory.getCurrentSession();
		// session.save(entity);
		entity = (AloneOrganization) session.load(AloneOrganization.class,
				session.save(entity));
		return entity;
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneOrganization entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@SuppressWarnings("unchecked")
	public List<AloneOrganization> getSecondOrgList() {
		Session session = sessionFatory.getCurrentSession();
		return (List<AloneOrganization>) session.createQuery(
				"From AloneOrganization as ao where ao.isorg='1'").list();
	}

	@Override
	public AloneOrganization getMainOrgByUserId(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneOrganization) session
				.createSQLQuery(
						"SELECT * FROM alone_organization  a " +
						"left join alone_organization_user  b on a.id = b.organizationid " +
						"left join alone_user  c on c.id = b.userid " +
						"where a.state = '1' and c.state = '1' and b.type='1' and c.id=?")
				.addEntity(AloneOrganization.class).setString(0, id)
				.uniqueResult();
	}

	@Override
	public void deleteEntityByTreecode(String entityId) {
		String hql = "update AloneOrganization a set a.state = '2' "
				+ "where a.treecode like ?";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, "%" + entityId + "%");
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganization> getNotMainOrgByUserId(String userid) {
		Session session = sessionFatory.getCurrentSession();
		return session
				.createSQLQuery(
						"SELECT * FROM alone_organization  a " +
						"left join alone_organization_user  b on a.id = b.organizationid " +
						"left join alone_user  c on c.id = b.userid " +
						"where a.state = '1' and c.state = '1' and b.type='0' and c.id=?")
				.addEntity(AloneOrganization.class).setString(0, userid).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganization> findListByOrgName(String orgName) {
		String sql = "from AloneOrganization a where a.name = ? ";
		Query query = sessionFatory.getCurrentSession().createQuery(sql);
		query.setString(0, orgName.trim());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganization> findListByOrgNameAndExcludeOrgId(String orgName,
			String excludeOrgId) {
		String sql = "from AloneOrganization a where a.name = ?  and a.id != ?";
		Query query = sessionFatory.getCurrentSession().createQuery(sql);
		query.setString(0, orgName.trim()).setString(1, excludeOrgId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganization> findListByParentId(String parentId) {
		String hql = "from AloneOrganization a where a.state = '1' and a.parentid = ? order by a.sort asc";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, parentId);
		return query.list();
	}

	@Override
	public AloneOrganization findTopEntity(String orgId) {
		String hql = "from AloneOrganization a where a.state = '1' and a.parentid = 'NONE' and a.id = ? ";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, orgId);
		return (AloneOrganization) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganization> findListByTreecode(String orgId) {
		String hql = "from AloneOrganization a where a.treecode like ?";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, orgId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getOrgByType(String type) {
		Session session=sessionFatory.getCurrentSession();
		String sql="select NAME,ID from ALONE_ORGANIZATION where TYPE="+type;
		SQLQuery query=session.createSQLQuery(sql);
		return DataResults.getMaps("NAME,ID", query.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganization> findTopList() {
		Session session=sessionFatory.getCurrentSession();
		String hql = "from AloneOrganization a where a.state = '1' and a.parentid = 'NONE' order by a.sort asc";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public void editTypep(String parentId, String typep) {
		String hql = "update AloneOrganization a set a.typep = ? where a.state = '1' and a.treecode like ?";
		Session session=sessionFatory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, typep).setString(1, "%"+parentId+"%");
		query.executeUpdate();
	}
}
