package com.farm.report.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.plugin.jxls.ReportManagerInter;
import com.farm.report.FarmReport;
import com.farm.web.action.WebSupport;

/**
 * 系统日志
 * 
 * @author autoCode
 * 
 */
public class reportDemoQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private InputStream inputStream;

	public String report() {
		try {
			query = DataQuery
					.init(
							query,
							"alone_applog a left join alone_user b on b.id=a.APPUSER",
							"a.id as id,a.CTIME as ctime,a.DESCRIBES as describes,b.name as APPUSER,a.LEVEL as level,a.METHOD as method,a.CLASSNAME as classname,a.IP as ip");
			query.setPagesize(1000);
			query.addDefaultSort(new DBSort("a.CTIME", "DESC"));
			result = query.search();
			result.runformatTime("CTIME", "yyyy-MM-dd_HH/mm/ss");
			log.info("查询结果集合");
			// ------------------------开始报表
			inputStream = FarmReport.newInstance("basictags.xls").addParameter(
					"result", result.getResultList()).addParameter(
					"department", "JISH").generate();
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	private final static ReportManagerInter reportIMP = (ReportManagerInter) BEAN("excelReportId");

	// ----------------------------------------------------------------------------------

	private static final Logger log = Logger.getLogger(reportDemoQuery.class);

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 加载树节点 public String loadTreeNode() { treeNodes =
	 * EasyUiTreeNode.formatAjaxTree(EasyUiTreeNode .queryTreeNodeOne(id,
	 * "SORT", "alone_menu", "ID", "PARENTID", "NAME").getResultList(),
	 * EasyUiTreeNode .queryTreeNodeTow(id, "SORT", "alone_menu", "ID",
	 * "PARENTID", "NAME").getResultList(), "PARENTID", "ID", "NAME"); return
	 * SUCCESS; }
	 * 
	 * @return
	 */
}
