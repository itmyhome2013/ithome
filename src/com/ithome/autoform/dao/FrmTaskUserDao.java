package com.ithome.autoform.dao;

import java.util.List;

import com.ithome.autoform.domain.FrmTaskUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.utils.HibernateSQLTools;

/**
 * 任务节点配置
 * 
 * @author huxuxu
 * 
 */
public class FrmTaskUserDao implements FrmTaskUserDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<FrmTaskUser> sqlTools;


	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public HibernateSQLTools<FrmTaskUser> getSqlTools() {
		return sqlTools;
	}

	public void setSqlTools(HibernateSQLTools<FrmTaskUser> sqlTools) {
		this.sqlTools = sqlTools;
	}

	public FrmTaskUser insertEntity(FrmTaskUser entity) {
		// entity.setId(sqlTools.getPrimary());
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	@Override
	public void deleteEntitys(List<DBRule> rules) {
		sqlTools.deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

}
