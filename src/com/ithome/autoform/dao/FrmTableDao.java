package com.ithome.autoform.dao;

import com.ithome.autoform.domain.FrmTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.farm.core.sql.utils.HibernateSQLTools;

/**
 * 表基础信息
 * 
 * @author hxx
 * 
 */
public class FrmTableDao implements FrmTableDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<FrmTable> sqlTools;

	public void deleteEntity(FrmTable entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);  
	}

	public FrmTable getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (FrmTable) session.get(FrmTable.class, id);
	}

	public FrmTable insertEntity(FrmTable entity) {
		// entity.setId(sqlTools.getPrimary());
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	public void editEntity(FrmTable entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public HibernateSQLTools<FrmTable> getSqlTools() {
		return sqlTools;
	}

	public void setSqlTools(HibernateSQLTools<FrmTable> sqlTools) {
		this.sqlTools = sqlTools;
	}

}
