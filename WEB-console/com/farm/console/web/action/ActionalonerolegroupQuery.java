package com.farm.console.web.action;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.console.server.contain.RolegroupManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.spring.BeanFactory;

/**
 * 角色
 * 
 * @author autoCode
 * 
 */
public class ActionalonerolegroupQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneRolegroup entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private String menuIds;// 菜单主键集合
	private String userid;// 用户id
	private List<EasyUiTreeNode> treeNodes;// 异步树的封装

	/**
	 * 查找用户的权限角色
	 * 
	 * @return
	 */
	public String loadUserRulegroup() {
		try {
			query = DataQuery
					.init(
							query,
							"ALONE_ROLEGROUP a left join ALONE_USER_ROLEGROUP b on a.id=b.rolegroup and b.userid='"
									+ userid + "'",
							"a.id as aid,a.name as aname,a.comments as acomments,b.id as bid");
			query.addRule(new DBRule("a.state", "1", "="));
			result = query.search();
			for (Map<String, Object> node : result.getResultList()) {
				if (node.get("BID") != null) {
					node.put("BID", "1");
				} else {
					node.put("BID", "0");
				}
			}
		} catch (Exception e) {
			DataResults.setException(result, e);
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
			query = DataQuery.init(query, "alone_rolegroup",
					"id,name,comments,state");
			result = query.search();
			Map<String, String> dictMap = new HashMap<String, String>();
			dictMap.put("1", "可用");
			dictMap.put("0", "禁用");
			result.runDictionary(dictMap, "STATE");
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
	 * 为用户设置权限角色
	 * 
	 * @return
	 */
	public String userRuleGroupsCommit() {
		try {
			aloneIMP.setRuleGroupForUser(ids, userid);
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			PageSets.initPageSet(pageset, CommitType.FALSE, e);
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
		} catch (DataIntegrityViolationException e) {
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, "该角色已被使用");
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e.toString()
					+ e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 查询菜单权限构造树形菜单
	 * 
	 * @return
	 */
	public String initRoleGruopTreeNode() {
		try {
			query = DataQuery
					.init(
							query,
							" ALONE_MENU A LEFT JOIN ALONE_ACTION B ON A.ACTION=B.ID  left join (select rolegroupid,menuid from alone_menu_rolegroup where rolegroupid='"
									+ ids + "') C on A.id=C.menuid ",
							"A.NAME,A.ID,A.SORT,A.PARENTID,A.TREECODE,B.URL,A.name as B_LABLE,A.TYPE,c.rolegroupid");
			query.SetDISTINCT(true);
			query.setPagesize(100);
			query.addSort(new DBSort("length(A.TREECODE)", "ASC",false));
			result = query.search();
		} catch (Exception e) {
			result = DataResult.setMessager(result, e + e.getMessage());
			log.error(e.getMessage() + e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 设置角色权限树
	 * 
	 * @return
	 */
	public String roleGroupSetTree() {
		try {
			List<String> menuIdList = ParameterUtils.expandDomainPara(menuIds);
			aloneIMP.reSetMenu(menuIdList, ids);
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.OTHER, CommitType.FALSE);
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
	 * 一次查出所有菜单(标注角色选中状态)
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String loadRuleTreeNode() throws SQLException {
		initRoleGruopTreeNode();
		// query = DataQuery.init(query, "alone_menu",
		// "ID,NAME,TYPE,SORT,PARENTID,STATE,IMG,COMMENTS");
		// query.setPagesize(100);
		// query.addRule(new DBRule("STATE", "1", "="));
		// query.addUserWhere(" ORDER BY LENGTH(TREECODE) ASC,SORT ASC");
		// result = query.search();
		treeNodes = EasyUiTreeNode.formatAjaxTree(result.getResultList(),
				"A_PARENTID", "A_ID", "A_NAME", "C_ROLEGROUPID");
		return SUCCESS;
	}

	private final static RolegroupManagerInter aloneIMP = (RolegroupManagerInter) BeanFactory
			.getBean("ALONE_Rolegroup_DAO_PROXY");

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

	public AloneRolegroup getEntity() {
		return entity;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public void setEntity(AloneRolegroup entity) {
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	private static final Logger log = Logger.getLogger(ActionPopAction.class);
	private static final long serialVersionUID = 1L;
}
