package com.ithome.autoform.web.action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.ithome.autoform.domain.FrmTable;
import com.ithome.autoform.server.FrmTableManagerInter;
import com.farm.console.FarmManager;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;

/**
 * 表基础信息
 * 
 */
public class ActionFrmTableQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private List<Map<String, Object>> resultList; // 导出结果集
	private DataQuery query;// 条件查询
	private FrmTable entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private Map<String, Object> node;
	private InputStream inputStream;
	
	private String tableid;  //表单ID
	private String state;  //删除状态   0：禁用，1：正常，2：删除

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			
			DataResult result = aloneIMP.createFrmTableSimpleQuery(query).search();
			resultList = result.getResultList();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("STATE"), "STATE"); //状态
			result.runformatTime("CRETIME","yyyy-MM-dd HH:mm:ss");// 
			
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
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
			if ("".equals(entity.getId())) {
				entity = aloneIMP.insertFrmTableEntity(entity, getCurrentUser());
			} else {
				entity = aloneIMP.editFrmTableEntity(entity, getCurrentUser());
			}

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
			entity = aloneIMP.insertFrmTableEntity(entity, getCurrentUser());
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
				aloneIMP.deleteFrmTableEntity(id, state, getCurrentUser());
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
				entity = new FrmTable();
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getFrmTableEntity(ids);

				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getFrmTableEntity(ids);

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

	public String allView() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getFrmTableEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getFrmTableEntity(ids);
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

	private final static FrmTableManagerInter aloneIMP = (FrmTableManagerInter) BeanFactory
			.getBean("FrmTableProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public FrmTable getEntity() {
		return entity;
	}

	public void setEntity(FrmTable entity) {
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

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}

	public Map<String, Object> getNode() {
		return node;
	}

	public void setNode(Map<String, Object> node) {
		this.node = node;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}
	
	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private static final Logger log = Logger
			.getLogger(ActionFrmTableQuery.class);
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
