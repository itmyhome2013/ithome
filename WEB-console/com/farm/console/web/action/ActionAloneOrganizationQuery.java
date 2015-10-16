package com.farm.console.web.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.server.contain.OrganizationManagerInter;
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
 * 组织机构Action
 * 
 * @author zhang_hc
 * @time 2012-8-31 上午09:15:30
 * @update 2012-12-17 14:32 zhang_hc 修改父组织机构，级联修改所有子组织机构的“专业类型”为父组织机构的“专业类型”；
 *         新增子组织机构，默认“专业类型”为父组织机构的“专业类型”
 */
public class ActionAloneOrganizationQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneOrganization entity;// 实体封装
	private AloneOrganization fatherEntity;// 实体封装
	private DataResult treeResult;// 树节点对象
	private String aloneContext;// 树节点id
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合

	/* 页面参数 */
	private String parentId;// 父组织机构ID。当点击新增修改按钮时，把选择树时的组织机构ID传过去；2012.8.31
	private String contact;// 通讯方式。用于插入组织机构扩展中；2012.9.3
	private String userId;// 用户ID
	private String orgId;// 组织机构ID
	private String orgName;// 用来验证名称是否唯一
	private String excludeOrgId;// 要排除的组织机构，包括子机构。组织机构移动操作用_zhang_hc_2012.9.9
	private String formOrgId;// 把组织机构“A”移动到组织机构“B”下_zhang_hc_2012.9.9
	private String toOrgId;// 组织机构移动操作用_zhang_hc_2012.9.9
	private String id;// 异步树传入的id
	/* 回显数据 */
	private String parentName;// 父组织机构名称
	private boolean isUniqName;// 是否是唯一orgName。
	private boolean isTopNode;// 是否是顶级节点，移动组织机构时，不能移动顶级的。_zhang_hc_2012.9.9
	private List<EasyUiTreeNode> treeNodes;// 异步树的封装

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery
					.init(
							//
							query,
							"ALONE_ORGANIZATION a  "
									+ "left join ALONE_ORGANIZATION b on a.parentid = b.id",
							"a.id as id,a.name as name, b.name as pname, a.type as type, "
									+ "a.comments as comments, a.sort as sort, a.state as state");

			query.addUserWhere(" and (a.state = '0' or a.state = '1') ");// 查询非删除的组织机构
			DBRule dbrule = query.getAndRemoveRule("A.TREECODE");
			if (dbrule != null) {
				dbrule.setValue(aloneIMP.getEntity(dbrule.getValue())
						.getTreecode());
				query.addRule(dbrule);
				query.addSort(new DBSort("A.TREECODE", "asc"));
			}
			query.addSort(new DBSort("a.sort", "asc"));
			query.setPagesize(10);
			result = query.search();

			// 状态转义
			{
				result.runDictionary("1:可用,0:禁用", "STATE");
				result.runDictionary(FarmManager.instance().findDicTitleForIndex("ORG_TYPE"),
						"TYPE");
				result.runDictionary("null:/", "PNAME");
				Map<String, String> transMap = new HashMap<String, String>();
				transMap.put("null", "");
				result.runDictionary(transMap, "COMMENTS");
			}

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
			entity = aloneIMP.editEntity(entity, getCurrentUser(), contact);
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
			entity = aloneIMP.insertEntity(entity, getCurrentUser(), contact);
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

	/**
	 * 跳转
	 * 
	 * @return
	 */
	public String forSend() {
		return SUCCESS;
	}

	/**
	 * 加载树节点
	 * 
	 * @return
	 */
	public String loadTreeNode() {
		// DataQuery query = DataQuery
		// .getInstance(
		// "1",
		// "id as ID, a.utime as VSORT, a.parentid as VPARENTID, a.name as VNAME, a.state as VSTATE",
		// "ALONE_ORGANIZATION a");
		//
		// if (aloneContext == null || aloneContext.trim().length() <= 0
		// || aloneContext.trim().toUpperCase().equals("NONE")) {
		// query
		// .setUserWhere("and (a.parentid='NONE' or a.parentid is null or a.parentid='')");
		// } else {
		// query.setUserWhere("and a.parentid='" + aloneContext + "'");
		// }
		//
		// // 要排除的组织机构，包括子机构。组织机构移动操作用
		// if (excludeOrgId != null && !excludeOrgId.equals("")) {
		// query.addRule(new DBRule("a.id", excludeOrgId, "!="));
		// query.addRule(new DBRule("a.parentId", excludeOrgId, "!="));
		// }
		//
		// query.addRule(new DBRule("a.state", "1", "="));// 只查询可用的
		// query.addSort(new DBSort("a.sort", "ASC"));
		// query.setPagesize(200);
		//
		// try {
		// treeResult = query.search();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(EasyUiTreeNode
				.queryTreeNodeOne(id, "SORT", "ALONE_ORGANIZATION", "ID",
						"PARENTID", "NAME","CTIME").getResultList(), EasyUiTreeNode
				.queryTreeNodeTow(id, "SORT", "ALONE_ORGANIZATION", "ID",
						"PARENTID", "NAME","CTIME").getResultList(), "PARENTID", "ID",
				"NAME","CTIME");

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
				initAddData();
				break;
			}
			case (0): {// 展示
				initEditData();
				break;
			}
			case (2): {// 修改
				initEditData();
				break;
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
	 * 验证组织机构名称是否唯一
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-7 上午10:16:18
	 */
	public String ValidateIsUniqName() {
		try {
			isUniqName = aloneIMP.isUniqName(orgName.trim(), orgId);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE,
					"验证组织机构名称唯一失败：" + e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 移动组织机构
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-9 下午06:40:14
	 */
	public String moveOrg() {
		try {
			aloneIMP.moveOrg(formOrgId, toOrgId);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, "移动组织机构失败："
					+ e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 验证是否是顶级节点
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-9 下午07:48:46
	 */
	public String ValidateIsTopNode() {
		try {
			isTopNode = aloneIMP.isTopNode(orgId);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE,
					"验证是否是顶级节点失败：" + e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 初始化修改回显数据
	 * 
	 * @author zhang_hc
	 * @time 2012-9-4 上午09:23:54
	 */
	private void initEditData() {
		entity = aloneIMP.getEntity(ids);
		if (!entity.getParentid().equals("NONE")) {
			parentName = aloneIMP.getEntity(entity.getParentid()).getName();
		}
	}

	/**
	 * 初始化新增回显数据
	 * 
	 * @author zhang_hc
	 * @time 2012-9-4 上午09:24:07
	 */
	private void initAddData() {
		entity = new AloneOrganization();
		if (parentId != null && !parentId.equals("")) {
			AloneOrganization pEntity = aloneIMP.getEntity(parentId);
			if (pEntity.getState().equals("1")) {
				parentName = pEntity.getName();// 回显父组织机构名称
				entity.setParentid(parentId);
			}
		}
	}

	// ----------------------------------------------------------------------------------
	private final static OrganizationManagerInter aloneIMP = (OrganizationManagerInter) BeanFactory
			.getBean("ALONE_Organization_DAO_PROXY");

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

	public AloneOrganization getEntity() {
		return entity;
	}

	public void setEntity(AloneOrganization entity) {
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

	public DataResult getTreeResult() {
		return treeResult;
	}

	public void setTreeResult(DataResult treeResult) {
		this.treeResult = treeResult;
	}

	public String getAloneContext() {
		return aloneContext;
	}

	public void setAloneContext(String aloneContext) {
		this.aloneContext = aloneContext;
	}

	public AloneOrganization getFatherEntity() {
		return fatherEntity;
	}

	public void setFatherEntity(AloneOrganization fatherEntity) {
		this.fatherEntity = fatherEntity;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public boolean getIsUniqName() {
		return isUniqName;
	}

	public void setIsUniqName(boolean isUniqName) {
		this.isUniqName = isUniqName;
	}

	public String getExcludeOrgId() {
		return excludeOrgId;
	}

	public void setExcludeOrgId(String excludeOrgId) {
		this.excludeOrgId = excludeOrgId;
	}

	public String getFormOrgId() {
		return formOrgId;
	}

	public void setFormOrgId(String formOrgId) {
		this.formOrgId = formOrgId;
	}

	public String getToOrgId() {
		return toOrgId;
	}

	public void setToOrgId(String toOrgId) {
		this.toOrgId = toOrgId;
	}

	public boolean getIsTopNode() {
		return isTopNode;
	}

	public void setIsTopNode(boolean isTopNode) {
		this.isTopNode = isTopNode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private static final Logger log = Logger.getLogger(ActionPopAction.class);
	private static final long serialVersionUID = 1L;
}
