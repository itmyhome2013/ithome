package com.ithome.autoform.dao;

import com.ithome.autoform.domain.FrmProcessForm;
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
public class FrmProcessFormDao implements FrmProcessFormDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<FrmProcessForm> sqlTools;

	public void deleteEntity(FrmProcessForm entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);  
	}

	public FrmProcessForm getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (FrmProcessForm) session.get(FrmProcessForm.class, id);
	}

	public FrmProcessForm insertEntity(FrmProcessForm entity) {
		// entity.setId(sqlTools.getPrimary());
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	public void editEntity(FrmProcessForm entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public HibernateSQLTools<FrmProcessForm> getSqlTools() {
		return sqlTools;
	}

	public void setSqlTools(HibernateSQLTools<FrmProcessForm> sqlTools) {
		this.sqlTools = sqlTools;
	}

}
