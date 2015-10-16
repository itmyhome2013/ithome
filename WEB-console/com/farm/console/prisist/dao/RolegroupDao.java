package com.farm.console.prisist.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

public class RolegroupDao implements RolegroupDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from Alone_Rolegroup");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneRolegroup getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneRolegroup) session.get(AloneRolegroup.class, id);
	}

	public void insertEntity(AloneRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneRolegroup entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getRoleAction(String roleId) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("SELECT DISTINCT aa.url,aa.ischeck,me.name FROM Alone_Rolegroup ro LEFT JOIN alone_menu_rolegroup mr ON ro.id=mr.rolegroupid LEFT JOIN alone_menu me ON mr.menuid = me.id LEFT JOIN alone_action aa ON me.action=aa.id WHERE aa.id IS NOT NULL AND ro.id=? AND me.type='0' ORDER BY me.sort ASC");
		sqlquery.setString(0, roleId);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Object[]> listString = (List<Object[]>) sqlquery.list();
		for (Object[] entity : listString) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("URL", (String)entity[0]);
			map.put("ISCHECK", ((Character)entity[1]).toString());
			map.put("NAME", (String)entity[2]);
			list.add(map);
		}
		return list;
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
