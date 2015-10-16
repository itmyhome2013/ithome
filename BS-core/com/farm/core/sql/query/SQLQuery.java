package com.farm.core.sql.query;

import java.sql.SQLException;

import org.hibernate.Session;

import com.farm.core.sql.result.DataResult;
import com.farm.web.spring.HibernateSessionFactory;

public class SQLQuery {
	private String sql;
	private int pagesize;
	private int currentPage;

	private SQLQuery() {
	}

	public static SQLQuery getInstance(String sql, int pagesize, int currentPage) {
		SQLQuery query = new SQLQuery();
		query.setCurrentPage(currentPage);
		query.setPagesize(pagesize);
		query.setSql(sql);
		return query;
	}

	/**
	 * 执行查询
	 * 
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	public DataResult search(Session session) throws SQLException {
		return HibernateQueryHandle.runComplexQuery1(session, sql, pagesize,
				currentPage);
	}

	/**
	 * 执行查询
	 * 
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	public DataResult search() throws SQLException {
		Session session = HibernateSessionFactory.getSession();
		DataResult result = null;
		try {
			result = this.search(session);
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			session.close();
		}
		return result;
	}

	// ---------------------------------------------------------------------
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
