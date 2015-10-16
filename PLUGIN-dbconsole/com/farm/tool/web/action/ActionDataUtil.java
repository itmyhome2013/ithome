package com.farm.tool.web.action;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单商品
 * 
 * @author autoCode
 * 
 */
public class ActionDataUtil extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private String currentPage;
	private String titles;
	private String tables;
	private int pagesize;
	private String wheres;
	private String logs;
	private String sql;
	private String type;
	private String path;
	private String[] files;
	private boolean diris;
	private String parent;
	private String filename;
	private long len;
	private List list;
	private boolean unitonlyone;

	public String console() {
		logs = "数据管理器，v0.1,请对该应用地址保密！";
		pagesize = 20;
		currentPage = "1";
		type = "1";
		return SUCCESS;
	}

	public String download() {
		java.io.File file = new java.io.File(path);
		filename = file.getName();
		return SUCCESS;
	}

	public InputStream getInputStream() throws Exception {
		return new FileInputStream(path);
	}

	public String fileMng() {
		if (path == null || path.trim().length() <= 0) {
			path = ServletActionContext.getServletContext().getRealPath("")
					.replaceAll("\\\\", "/");
		}
		java.io.File file = new java.io.File(path);
		len = file.length();
		if (diris = file.isDirectory()) {
			files = file.list();
		}
		parent = file.getParent().replaceAll("\\\\", "/");
		type = "3";
		return SUCCESS;
	}

	public String sqlRun() {
		logs = "数据管理器，v0.1,请对该应用地址保密！";
		pagesize = 20;
		currentPage = "1";
		Session session = sessionFactory.openSession();
		list = null;
		try {
			SQLQuery query = session.createSQLQuery(sql);
			if (sql.trim().toUpperCase().indexOf("SELECT") == 0) {
				list = query.list();
				if (list.size() > 0) {
					if (list.get(0) instanceof Object[]) {
						unitonlyone = false;
					} else {
						unitonlyone = true;
					}
				}
			} else {
				query.executeUpdate();
			}
			logs = "SQL执行成功：" + sql + query.getQueryString();
		} catch (Exception e) {
			logs = sql + ":" + e.getMessage() + e.toString();
		} finally {
			session.close();
		}
		type = "2";
		return SUCCESS;
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String doquery() {
		query = DataQuery.getInstance(currentPage, titles, tables);
		query.setPagesize(pagesize);
		query.addUserWhere(wheres);
		try {
			result = query.search();
			result.LoadListArray();
			logs = "success!";
		} catch (Exception e) {
			logs = e.getMessage() + e.toString();
		}
		type = "1";
		return SUCCESS;
	}

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public DataQuery getQuery() {
		return query;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getLen() {
		return len;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public boolean isUnitonlyone() {
		return unitonlyone;
	}

	public void setUnitonlyone(boolean unitonlyone) {
		this.unitonlyone = unitonlyone;
	}

	public void setLen(long len) {
		this.len = len;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getTables() {
		return tables;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}

	public boolean isDiris() {
		return diris;
	}

	public void setDiris(boolean diris) {
		this.diris = diris;
	}

	public String getWheres() {
		return wheres;
	}

	public void setWheres(String wheres) {
		this.wheres = wheres;
	}

	public String getSql() {
		return sql;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	private final static SessionFactory sessionFactory = (SessionFactory) BeanFactory
			.getBean("sessionFactory");
	private static final Logger log = Logger.getLogger(ActionDataUtil.class);
	private static final long serialVersionUID = 1L;
}
