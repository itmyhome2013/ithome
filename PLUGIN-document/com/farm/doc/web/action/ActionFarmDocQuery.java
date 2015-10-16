package com.farm.doc.web.action;

import java.util.ArrayList;
import java.util.List;

import com.farm.doc.domain.FarmDoc;
import com.farm.doc.domain.FarmDoctext;
import com.farm.doc.domain.FarmDoctype;
import com.farm.doc.server.FarmDocManagerInter;

import org.apache.log4j.Logger;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.core.time.TimeTool;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

/**
 * 文档管理
 * 
 * @author autoCode
 * 
 */
public class ActionFarmDocQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private FarmDoc entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private String content;// 超文本
	private FarmDoctype doctype;// 分类

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			result = aloneIMP.createSimpleDocQuery(query).search();
			result.runDictionary("1:开放,0:禁用,2:待审核", "STATE");
			result.runDictionary("1:HTML,2:TXT", "DOMTYPE");

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
			entity.setTexts(new FarmDoctext(content, TimeTool.getTimeDate14(),
					TimeTool.getTimeDate14(), getCurrentUser().getName(),
					getCurrentUser().getId(), getCurrentUser().getName(),
					getCurrentUser().getId(), "1"));
			entity = aloneIMP.editDoc(entity,"系统管理员修改", getCurrentUser());
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.UPDATE,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}
	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	public String rightEditCommit() {
		try {
			entity = aloneIMP.editDocRight(entity, getCurrentUser());
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
			// 构造文档text
			entity.setTexts(new FarmDoctext(content, TimeTool.getTimeDate14(),
					TimeTool.getTimeDate14(), getCurrentUser().getName(),
					getCurrentUser().getId(), getCurrentUser().getName(),
					getCurrentUser().getId(), "1"));
			// 构造分类
			List<FarmDoctype> types = new ArrayList<FarmDoctype>();
			types.add(aloneIMP.getType(doctype.getId()));
			entity.setTypes(types);
			entity = aloneIMP.createDoc(entity, getCurrentUser());
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
				aloneIMP.deleteDoc(id, getCurrentUser());
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
	 * （修改文档权限）
	 * 
	 * @return
	 */
	public String rightEdit() {
		try {
			String typeid = null;
			if (doctype != null && doctype.getId() != null
					&& doctype.getId().trim().length() > 0) {
				typeid = doctype.getId();
			}
			// entity = aloneIMP.getEntity(ids);
			entity = aloneIMP.getDoc(ids);
			List<FarmDoctype> types = entity.getTypes();
			if (types != null && types.size() > 0) {
				doctype = types.get(0);
			}
			PageSets.initPageSet(pageset, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.OTHER,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	public String view() {
		try {
			String typeid = null;
			if (doctype != null && doctype.getId() != null
					&& doctype.getId().trim().length() > 0) {
				typeid = doctype.getId();
			}
			switch (pageset.getPageType()) {
			case (1): {// 新增
				doctype = aloneIMP.getType(typeid);
				entity = new FarmDoc();
				entity.setPubtime(TimeTool.getFormatTimeDate12(TimeTool
						.getTimeDate14(), "yyyy-MM-dd HH:mm:ss"));
				return SUCCESS;
			}
			case (0): {// 展示
				// entity = aloneIMP.getEntity(ids);
				entity = aloneIMP.getDoc(ids);
				doctype = entity.getTypes().get(0);
				return SUCCESS;
			}
			case (2): {// 修改
				// entity = aloneIMP.getEntity(ids);
				entity = aloneIMP.getDoc(ids);
				List<FarmDoctype> types = entity.getTypes();
				if (types != null && types.size() > 0) {
					doctype = types.get(0);
				}
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

	private final static FarmDocManagerInter aloneIMP = (FarmDocManagerInter) BeanFactory
			.getBean("farm_docProxyId");

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

	public FarmDoc getEntity() {
		return entity;
	}

	public void setEntity(FarmDoc entity) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public FarmDoctype getDoctype() {
		return doctype;
	}

	public void setDoctype(FarmDoctype doctype) {
		this.doctype = doctype;
	}

	private static final Logger log = Logger
			.getLogger(ActionFarmDocQuery.class);
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
