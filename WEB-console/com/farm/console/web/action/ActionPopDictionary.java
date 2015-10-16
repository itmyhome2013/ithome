package com.farm.console.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.prisist.domain.AloneDictionaryEntity;
import com.farm.console.server.contain.DictionaryEntityManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.spring.BeanFactory;

/**
 * 数据字典
 * 
 * @author MAC_alone
 * 
 */
public class ActionPopDictionary extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneDictionaryEntity entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private String id;
	private String index;
	private List<EasyUiTreeNode> treeNodes;// 异步树的封装

	/* 页面参数 */
	private String key;// 用于验证字段（ENTITYINDEX）是否重复

	/* 回显数据 */
	private boolean isRepeatKey;// key;//用于验证字段（ENTITYINDEX）是否重复

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery.init(query, "alone_dictionary_entity",
					"id,name,entityindex,type,type as types,comments");
			query.addDefaultSort(new DBSort("utime", "DESC"));
			result = query.search();
			result.runDictionary("1:序列,0:树", "TYPE");
			result.LoadListArray();
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
			log.error(result.getMessage());
		}
		return SUCCESS;
	}
	public String loadTree() {
		if(id==null){
			id="NONE";
		}
		treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(EasyUiTreeNode
				.queryTreeNodeOne(id, "SORT", " (SELECT a.id AS ID,a.treecode AS TREECODE,a.entitytype AS ENTITYTYPE,b.ENTITYINDEX as ENTITYINDEX,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM ALONE_DICTIONARY_TYPE a LEFT JOIN ALONE_DICTIONARY_ENTITY b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and ENTITYINDEX='"+index+"' and a.state!=2").getResultList(), EasyUiTreeNode
				.queryTreeNodeTow(id, "SORT", " (SELECT a.id AS ID,a.treecode AS TREECODE,a.entitytype AS ENTITYTYPE,b.ENTITYINDEX as ENTITYINDEX,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM ALONE_DICTIONARY_TYPE a LEFT JOIN ALONE_DICTIONARY_ENTITY b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and a.ENTITYINDEX='"+index+"' and a.state!=2").getResultList(), "PARENTID", "ID",
				"NAME","CTIME");
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
			e.printStackTrace();
			pageset = new PageSet(PageType.UPDATE, CommitType.FALSE, e
					.toString()
					+ e.getMessage());
			log.error(pageset.getMessage());
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
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e
					.getMessage());
			log.error(pageset.getMessage());
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
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e.toString()
					+ e.getMessage());
			log.error(pageset.getMessage());
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
			e.printStackTrace();
			if (pageset == null) {
				pageset = new PageSet(PageType.OTHER, CommitType.FALSE, e
						.toString()
						+ e.getMessage());
			} else {
				pageset.setCommitType(CommitType.FALSE.value);
			}
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 验证key是否重复
	 * 
	 * @return
	 * @author liuchao
	 * @time 2012-12-20 下午05:49:33
	 */
	public String validateIsRepeatKey() {
		isRepeatKey = aloneIMP.validateIsRepeatKey(key, ids);
		return SUCCESS;
	}

	private final static DictionaryEntityManagerInter aloneIMP = (DictionaryEntityManagerInter) BeanFactory
			.getBean("ALONE_DictionaryEntity_DAO_PROXY");

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

	public AloneDictionaryEntity getEntity() {
		return entity;
	}

	public void setEntity(AloneDictionaryEntity entity) {
		this.entity = entity;
	}

	public boolean isIsRepeatKey() {
		return isRepeatKey;
	}

	public void setIsRepeatKey(boolean isRepeatKey) {
		this.isRepeatKey = isRepeatKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}
	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	private static final Logger log = Logger
			.getLogger(ActionPopDictionary.class);
	private static final long serialVersionUID = 1L;
}
