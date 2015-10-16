package com.farm.console.prisist.dao;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneMenu;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

public class MenuDao implements MenuDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneMenu entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from Alone_Menu");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public AloneMenu getEntity(String id) {
		Session session= sessionFatory.getCurrentSession();
		return (AloneMenu)session.get(AloneMenu.class, id);
	}
	public AloneMenu insertEntity(AloneMenu entity) {
		Session session= sessionFatory.getCurrentSession();
		entity.setId((String)session.save(entity));
		return entity;
	}
	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}
	
	public void editEntity(AloneMenu entity) {
		Session session= sessionFatory.getCurrentSession();
		session.update(entity);
	}
	@Override
	public AloneMenu getEntityByTreeCode(String treeCode) {
		Session session= sessionFatory.getCurrentSession();
		Query query=session.createQuery("from AloneMenu where treecode=?");
		query.setString(0,treeCode);
		
		AloneMenu menu=(AloneMenu)query.list().get(0);
		return menu;
	}
	@Override
	public DataResult loadTreeNode(DataQuery query) {
		DataResult result = null;
		Session session = sessionFatory.getCurrentSession();
		try {
			result = query.search(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
