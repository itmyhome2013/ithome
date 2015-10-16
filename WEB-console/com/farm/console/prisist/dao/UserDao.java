package com.farm.console.prisist.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.result.DataResults;

public class UserDao implements UserDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneUser entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from alone_user");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneUser getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneUser) session.get(AloneUser.class, id);
	}

	public String insertEntity(AloneUser entity) {
		Session session = sessionFatory.getCurrentSession();
		return (String) session.save(entity);

	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneUser entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@SuppressWarnings("unchecked")
	public AloneUser getPasswordByLoginName(String loginname) {
		Session session = sessionFatory.getCurrentSession();
		List<AloneUser> userlist = (List<AloneUser>) session.createQuery(
				"from AloneUser where loginname=?").setString(0, loginname)
				.list();
		if (userlist != null && userlist.size() > 0)
			return userlist.get(0);
		else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AloneUser> getAllUser() {
		Session session = sessionFatory.getCurrentSession();
		return session.createQuery("from AloneUser").list();
	}

	@Override
	public boolean isExist(String LoginName) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from alone_user where loginname=?");
		sqlquery.setString(0, LoginName);
		Integer num = Integer.valueOf(sqlquery.list().get(0).toString());
		if (num.intValue() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public AloneUser getEntityByLoginName(String LoginName) {
		Session session = sessionFatory.getCurrentSession();
		List<AloneUser> userlist = (List<AloneUser>) session.createQuery(
				"from AloneUser where loginname=?").setString(0, LoginName)
				.list();
		if (userlist == null || userlist.size() <= 0) {
			return null;
		}
		return userlist.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserOrg(String id) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select distinct b.organizationid from alone_user a left join alone_organization_user b on a.id=b.userid where a.id=?");
		sqlquery.setString(0, id);
		List<String> num = sqlquery.list();

		return num;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneUser> findAllOrg(String userId) {
		String sql = "select b.* " + "from alone_organization_user a "
				+ "inner join alone_organization b on a.organizationid = b.id "
				+ "where a.userid = ?";
		SQLQuery sqlQuery = sessionFatory.getCurrentSession().createSQLQuery(
				sql);
		sqlQuery.setString(0, userId);
		sqlQuery.addEntity(AloneUser.class);
		return sqlQuery.list();
	}

	@Override
	public AloneOrganization findMainOrg(String userId) {
		String sql = "select a.* "
				+ "from ALONE_ORGANIZATION a "
				+ "inner join ALONE_ORGANIZATION_USER b on a.id = b.organizationid and b.type = '1' "
				+ "where b.userid = ?";
		SQLQuery sqlQuery = sessionFatory.getCurrentSession().createSQLQuery(
				sql);
		sqlQuery.setString(0, userId);
		sqlQuery.addEntity(AloneOrganization.class);
		return (AloneOrganization) sqlQuery.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneOrganization> findUnMianOrgList(String userId) {
		String sql = "select a.* "
				+ "from ALONE_ORGANIZATION a "
				+ "inner join ALONE_ORGANIZATION_USER b on a.id = b.organizationid and b.type = '0' /* 查询非主要组织机构 */"
				+ "where b.userid = ? and a.state != '2'";
		SQLQuery sqlQuery = sessionFatory.getCurrentSession().createSQLQuery(
				sql);
		sqlQuery.setString(0, userId);
		sqlQuery.addEntity(AloneOrganization.class);
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllHeader() {
		String sql = "select a.ID as ID,a.NAME as NAME,e.NAME as ORG "
				+ "from ALONE_USER a "
				+ "left join CCS_USEREX b on a.ID=b.USERID "
				+ "left join CCS_WORKTYPE c on b.POSITION=c.ID "
				+ "left join ALONE_ORGANIZATION_USER d on a.id=d.USERID "
				+ "left join ALONE_ORGANIZATION e on d.ORGANIZATIONID=e.id "
				+ "where c.NAME='队长' and a.STATE='1'"
				+ " and d.TYPE='1' and e.STATE='1'";
		SQLQuery query = sessionFatory.getCurrentSession().createSQLQuery(sql);
		return DataResults.getMaps("a.ID as ID,a.NAME as NAME,e.NAME as ORG",
				query.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllCh4Man() {
		String sql = "select a.ID as ID,a.NAME as NAME,e.NAME as ORG "
				+ "from ALONE_USER a "
				+ "left join CCS_USEREX b on a.ID=b.USERID "
				+ "left join CCS_WORKTYPE c on b.POSITION=c.ID "
				+ "left join ALONE_ORGANIZATION_USER d on a.id=d.USERID "
				+ "left join ALONE_ORGANIZATION e on d.ORGANIZATIONID=e.id "
				+ "where c.NAME='瓦检员' and a.STATE='1' and d.TYPE='1' "
				+ "and e.STATE='1'";
		SQLQuery query = sessionFatory.getCurrentSession().createSQLQuery(sql);
		return DataResults.getMaps("a.ID as ID,a.NAME as NAME,e.NAME as ORG",
				query.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllSafeMan() {
		String sql = "select a.ID as ID,a.NAME as NAME,e.NAME as ORG "
				+ "from ALONE_USER a "
				+ "left join CCS_USEREX b on a.ID=b.USERID "
				+ "left join CCS_WORKTYPE c on b.POSITION=c.ID "
				+ "left join ALONE_ORGANIZATION_USER d on a.id=d.USERID "
				+ "left join ALONE_ORGANIZATION e on d.ORGANIZATIONID=e.id "
				+ "where c.NAME='安全员' and a.STATE='1' and d.TYPE='1' "
				+ "and e.STATE='1'";
		SQLQuery query = sessionFatory.getCurrentSession().createSQLQuery(sql);
		return DataResults.getMaps("a.ID as ID,a.NAME as NAME,e.NAME as ORG",
				query.list());
	}
}
