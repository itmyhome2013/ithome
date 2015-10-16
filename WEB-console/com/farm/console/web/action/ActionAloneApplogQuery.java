package com.farm.console.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.prisist.domain.AloneApplog;
import com.farm.console.server.contain.AloneApplogManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.core.web.ParameterUtils;
import com.farm.plugin.jxls.ReportManagerInter;
import com.farm.web.action.WebSupport;

/**
 * 系统日志
 * 
 * @author autoCode
 * 
 */
public class ActionAloneApplogQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneApplog entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
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
			//------------------------开始报表
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("department", "asdf");
			para.put("result", result.getResultList());
			reportIMP.generate("basictags.xls", para);
			inputStream = new FileInputStream(new File(reportIMP
					.getReportPath("basictags.xls")));
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery
					.init(
							query,
							"alone_applog a left join alone_user b on b.id=a.APPUSER",
							"a.id as id,a.CTIME as ctime,a.DESCRIBES as describes,b.name as APPUSER,a.LEVEL as level,a.METHOD as method,a.CLASSNAME as classname,a.IP as ip");
			query.addDefaultSort(new DBSort("a.CTIME", "DESC"));
			result = query.search();
			result.LoadListArray();
			result.runformatTime("CTIME", "yyyy-MM-dd_HH/mm/ss");
			log.info("查询结果集合");
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	public String editSubmit() {
		try {
			entity = aloneIMP.editEntity(entity, getCurrentUser());
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.UPDATE,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	public String addSubmit() {
		try {
			entity = aloneIMP.insertEntity(entity, getCurrentUser());
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.ADD,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	public String delSubmit() {
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				aloneIMP.deleteEntity(id, getCurrentUser());
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.DEL,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 跳转
	 * 
	 * @return
	 */
	public String forSend() {
		return SUCCESS;
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getEntity(ids);
				return SUCCESS;
			}
			default:
				break;
			}
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.OTHER,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	private final static AloneApplogManagerInter aloneIMP = (AloneApplogManagerInter) BEAN("alone_applogProxyId");
	private final static ReportManagerInter reportIMP = (ReportManagerInter) BEAN("excelReportId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public AloneApplog getEntity() {
		return entity;
	}

	public void setEntity(AloneApplog entity) {
		this.entity = entity;
	}

	public PageSet getPageset() {
		return pageset;
	}

	public void setPageset(PageSet pageset) {
		this.pageset = pageset;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	private static final Logger log = Logger
			.getLogger(ActionAloneApplogQuery.class);
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
