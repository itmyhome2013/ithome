package com.ithome.popu.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import com.ithome.popu.domain.PopuCitizenInfo;

import java.util.List;
import java.util.Map;

/**
 * 人口基础信息
 * 
 * @author MAC_wd
 * 
 */
public class PopuCitizenInfoDao implements PopuCitizenInfoDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<PopuCitizenInfo> sqlTools;

	public void deleteEntity(PopuCitizenInfo entity) {
		Session session = sessionFatory.getCurrentSession();
	    if(entity  != null){  
	    	session.delete(entity);
	    }  
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from POPU_CITIZEN_INFO");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public PopuCitizenInfo getEntity(BigDecimal id) {
		Session session = sessionFatory.getCurrentSession();
		return (PopuCitizenInfo) session.get(PopuCitizenInfo.class, id);
	}

	public PopuCitizenInfo insertEntity(PopuCitizenInfo entity) {
		entity.setCitizenid(sqlTools.getPrimary());
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	public void editEntity(PopuCitizenInfo entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
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
	public void deleteEntitys(List<DBRule> rules) {
		sqlTools
				.deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<PopuCitizenInfo> selectEntitys(List<DBRule> rules) {
		return sqlTools.selectSqlFromFunction(
				sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		sqlTools.updataSqlFromFunction(sessionFatory.getCurrentSession(),
				values, rules);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public HibernateSQLTools<PopuCitizenInfo> getSqlTools() {
		return sqlTools;
	}

	public void setSqlTools(HibernateSQLTools<PopuCitizenInfo> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
