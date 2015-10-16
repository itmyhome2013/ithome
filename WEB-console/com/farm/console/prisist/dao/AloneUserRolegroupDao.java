package com.farm.console.prisist.dao;

import java.math.BigInteger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneUserRolegroup;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

/**
 * rolegroup
 * 
 * @author MAC_wd
 * 
 */
public class AloneUserRolegroupDao implements AloneUserRolegroupDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneUserRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from ALONE_USER_ROLEGROUP");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneUserRolegroup getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneUserRolegroup) session.get(AloneUserRolegroup.class, id);
	}

	public AloneUserRolegroup insertEntity(AloneUserRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	public void editEntity(AloneUserRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	@Override
	public Session getSession() {
		return sessionFatory.getCurrentSession();
	}

	public DataResult runSqlQuery(DataQuery query) {
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void removeUserAllRuleGroups(String userid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from ALONE_USER_ROLEGROUP where userid=?");
		sqlquery.setString(0, userid);
		sqlquery.executeUpdate();
	}
}
