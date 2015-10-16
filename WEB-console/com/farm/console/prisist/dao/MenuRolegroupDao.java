package com.farm.console.prisist.dao;

import java.math.BigInteger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneMenu;
import com.farm.console.prisist.domain.AloneMenuRolegroup;
import com.farm.console.prisist.domain.AloneRolegroup;

public class MenuRolegroupDao implements MenuRolegroupDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneMenuRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from Alone_Menu_Rolegroup");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneMenuRolegroup getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneMenuRolegroup) session.get(AloneMenuRolegroup.class, id);
	}

	public void insertEntity(AloneMenuRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneMenuRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@Override
	public void deleteAllEntity(AloneRolegroup rolegroup) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from Alone_Menu_Rolegroup  where rolegroupid=?");
		sqlquery.setString(0, rolegroup.getId());
		sqlquery.executeUpdate();
	}

	@Override
	public void deleteAllEntity(AloneMenu menu) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("delete from Alone_Menu_Rolegroup  where menuid=?");
		sqlquery.setString(0, menu.getId());
		sqlquery.executeUpdate();
	}
}
