package com.ithome.autoform.web.action;

import com.ithome.autoform.domain.FrmSqu;
import com.ithome.autoform.server.FrmSquManagerInter;

import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
import org.apache.log4j.Logger;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

 /**
 * 实体管理类功能说明
 *
 * @author 作者:自动生成
 * @version 日期(用日期代替版本号)+版本备注
 */ 
public class ActionFrmSquQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private FrmSqu entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			DataResult result=aloneIMP.createFrmSquSimpleQuery(query).search();
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
			entity = aloneIMP.editFrmSquEntity(entity, getCurrentUser());
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.UPDATE, CommitType.FALSE, e);
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
			entity = aloneIMP.insertFrmSquEntity(entity, getCurrentUser());
			pageset = new PageSet(PageType.ADD,CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.ADD, CommitType.FALSE, e);
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
				aloneIMP.deleteFrmSquEntity(id,getCurrentUser());
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.DEL, CommitType.FALSE, e);
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
				entity = aloneIMP.getFrmSquEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getFrmSquEntity(ids);
				return SUCCESS;
			}
			default:
				break;
			}
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.OTHER, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	private final static FrmSquManagerInter aloneIMP = (FrmSquManagerInter) BeanFactory
			.getBean("FRM_SQUProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public FrmSqu getEntity() {
		return entity;
	}

	public void setEntity(FrmSqu entity) {
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
	
	private static final Logger log = Logger.getLogger(ActionFrmSquQuery.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 加载树节点 public String loadTreeNode() { treeNodes =
	 * EasyUiTreeNode.formatAjaxTree(EasyUiTreeNode .queryTreeNodeOne(id,
	 * "SORT", "alone_menu", "ID", "PARENTID", "NAME").getResultList(),
	 * EasyUiTreeNode .queryTreeNodeTow(id, "SORT", "alone_menu", "ID",
	 * "PARENTID", "NAME").getResultList(), "PARENTID", "ID", "NAME"); return
	 * SUCCESS; }
	 * @return
	 */
}
