package com.farm.console.prisist.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.console.prisist.domain.AloneAction;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;

public class ActionDao implements ActionDaoInter {
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneAction entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public DataResult runSqlQuery(DataQuery query){
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}
	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from Alone_Action");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneAction getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneAction) session.get(AloneAction.class, id);
	}

	public AloneAction insertEntity(AloneAction entity) {
		Session session = sessionFatory.getCurrentSession();
		entity.setId((String) session.save(entity));
		return entity;
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneAction entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@SuppressWarnings("unchecked")
	public List<AloneAction> getAllEntity() {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session
				.createQuery("from AloneAction where state='1'");
		List<AloneAction> list = sqlquery.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<AloneAction> getAllcheckEntity() {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session
				.createQuery("from AloneAction where ischeck='1' and state='1'");
		List<AloneAction> list = sqlquery.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getActionResourceByCondition(String rName) {
		Session session = sessionFatory.getCurrentSession();
		String sqlPart = "";
		if (rName != null && !"".equals(rName)) {
			sqlPart = " where " + rName;
		} else {
			sqlPart = "";
		}
		SQLQuery sqlquery = session
				.createSQLQuery("select id,url,name,comments,type,ctime,utime,cuser,muser,state,action,lable,ischeck from alone_action"
						+ sqlPart);

		List list = sqlquery.list();
		List<Map<String, Object>> relist = DataResults
				.getMaps(
						"id,url,name,comments,type,ctime,utime,cuser,muser,state,action,lable,ischeck",
						list);
		return relist;
	}
}
