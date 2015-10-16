package com.farm.console.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.server.contain.DictionaryTypeManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.spring.BeanFactory;

/**
 * 字典类型
 * 
 * @author MAC_alone
 * 
 */
public class ActionPopDictionaryType extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneDictionaryType entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合

	/* 页面参数 */
	private String dicId;//用于关联字典类型
	private String id;// 异步树传入的id
	private String parentId;  //父ID
	private String parentName;// 父组织机构名称
	private String type; //类型
	private List<EasyUiTreeNode> treeNodes;// 异步树的封装
	
	private String treeCode;
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery.init(query, "alone_dictionary_type a LEFT JOIN alone_dictionary_type b ON a.parentid = b.id",
					"a.id AS ID,a.sort AS SORT,a.name AS NAME,a.entitytype AS ENTITYTYPE,a.state AS STATE,b.name AS PNAME");
			query.addUserWhere(" and (a.state = '0' or a.state = '1') ");// 查询非删除的组织机构
			query.addDefaultSort(new DBSort("a.sort", "asc"));
			if(ids!=null&&ids.trim().length()>0){
				query.addRule(new DBRule("a.entity", ids, "="));
			}
			result = query.search();
			result.runDictionary("1:可用,0:禁用", "STATE");
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
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e.toString()
					+ e.getMessage());
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

	public String byIdGetTreecode(){
		AloneDictionaryType aloneDictionaryType = aloneIMP.getEntity(ids);
		treeCode = aloneDictionaryType.getTreecode();
		return SUCCESS;
	}
	
	/**
	 * 跳转
	 * 
	 * @return
	 */
	public String forSend() {
		if("1".equals(type)){
			return "sequence";
		}else{
			return "tree";
		}
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
				if(dicId==null||dicId.equals("")){
					throw new RuntimeException("找不到关联的数据字典！");
				}
				
				entity = new AloneDictionaryType();
				entity.setEntity(dicId);
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
	 * (树组织结构)显示详细信息
	 * 
	 * @return
	 */
	public String viewTree() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				if(dicId==null||dicId.equals("")){
					throw new RuntimeException("找不到关联的数据字典！");
				}
				entity = new AloneDictionaryType();
				entity.setEntity(dicId);
				if (parentId != null && !parentId.equals("")) {
					AloneDictionaryType pEntity = aloneIMP.getEntity(parentId);
					if (pEntity.getState().equals("1")) {
						parentName = pEntity.getName();// 回显父组织机构名称
						entity.setParentid(parentId);
					}
				}else{
					entity.setParentid("NONE");
				}
				return SUCCESS;
				
			}
			case (0): {// 展示
				entity = aloneIMP.getEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getEntity(ids);
				if (!entity.getParentid().equals("NONE")) {
					parentName = aloneIMP.getEntity(entity.getParentid()).getName();
				}
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
	 * 加载树节点
	 * 
	 * @return
	 */
	public String loadTreeNode() {
		
		treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(EasyUiTreeNode
				.queryTreeNodeOne(id, "SORT", " (SELECT a.id AS ID,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM ALONE_DICTIONARY_TYPE a LEFT JOIN ALONE_DICTIONARY_ENTITY b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and a.ENTITY='"+ids+"' and a.state!=2").getResultList(), EasyUiTreeNode
				.queryTreeNodeTow(id, "SORT", " (SELECT a.id AS ID,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM ALONE_DICTIONARY_TYPE a LEFT JOIN ALONE_DICTIONARY_ENTITY b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and a.ENTITY='"+ids+"' and a.state!=2").getResultList(), "PARENTID", "ID",
				"NAME","CTIME");
		return SUCCESS;
	}
	private final static DictionaryTypeManagerInter aloneIMP = (DictionaryTypeManagerInter) BeanFactory
			.getBean("ALONE_DictionaryType_DAO_PROXY");

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

	public AloneDictionaryType getEntity() {
		return entity;
	}

	public void setEntity(AloneDictionaryType entity) {
		this.entity = entity;
	}

	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public void setId(String id) {
		this.id = id;
	}
	private static final Logger log = Logger
			.getLogger(ActionPopDictionaryType.class);
	private static final long serialVersionUID = 1L;
}
