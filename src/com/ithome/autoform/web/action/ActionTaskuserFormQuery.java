package com.ithome.autoform.web.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.ithome.autoform.domain.FrmTaskUser;
import com.ithome.autoform.server.FrmTaskUserManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;

/**
 * 任务节点配置信息
 * 
 */
public class ActionTaskuserFormQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private List<Map<String, Object>> resultList; // 导出结果集
	private DataQuery query;// 条件查询
	private FrmTaskUser entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private Map<String, Object> node;
	private InputStream inputStream;

	private String taskKey; //任务节点key
	
	private boolean flag = false; // 操作成功标志
	
	private String users;  //人员
	private String group;  //小组
	private String role; //角色
	
	

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);

			DataResult result = aloneIMP.createFrmTaskUserSimpleQuery(query).search();
			resultList = result.getResultList();

			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}

	public String queryAllUser(){
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
	
			query.setPagesize(1000);
			DataResult result = aloneIMP.queryAllUser(query).search();
			resultList = result.getResultList();
			jsonResult = EasyUiUtils.formatGridData(result);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return SUCCESS;
	}
	
	public String queryAllGroup(){
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
	
			query.setPagesize(1000);
			DataResult result = aloneIMP.queryAllGroup(query).search();
			resultList = result.getResultList();
			jsonResult = EasyUiUtils.formatGridData(result);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return SUCCESS;
	}
	
	public String queryAllRole(){
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
	
			query.setPagesize(1000);
			DataResult result = aloneIMP.queryAllRole(query).search();
			resultList = result.getResultList();
			jsonResult = EasyUiUtils.formatGridData(result);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 提交人员修改数据
	 * 
	 * @return
	 */
	public String editUsersSubmit() {
		
		try {
			
			List<DBRule> rules = new ArrayList<DBRule>();
			rules.add(new DBRule("TASKKEY",entity.getTaskkey(),"="));
			rules.add(new DBRule("SELECTIVETYPE","0","="));
			aloneIMP.deleteEntitys(rules);
			
			//添加人员
			for (String id : ParameterUtils.expandDomainPara(entity.getUsers())) {
				entity.setSelectivetype("0");
				entity.setUserorgroupid(id);
				aloneIMP.insertFrmTaskUserEntity(entity);
			}

			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.UPDATE,
					CommitType.FALSE, e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 提交角色修改数据
	 * 
	 * @return
	 */
	public String editRoleSubmit() {
		
		try {
			
			List<DBRule> rules = new ArrayList<DBRule>();
			rules.add(new DBRule("TASKKEY",entity.getTaskkey(),"="));
			rules.add(new DBRule("SELECTIVETYPE","1","="));
			aloneIMP.deleteEntitys(rules);
			
			
			//添加角色
			for (String id : ParameterUtils.expandDomainPara(entity.getRole())) {
				entity.setSelectivetype("1");
				entity.setUserorgroupid(id);
				aloneIMP.insertFrmTaskUserEntity(entity);
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
		
		users = aloneIMP.getUserOrgGroupIdByTaskKey(entity.getTaskkey(),"0");
		role = aloneIMP.getUserOrgGroupIdByTaskKey(entity.getTaskkey(),"1");
		group = aloneIMP.getUserOrgGroupIdByTaskKey(entity.getTaskkey(),"2");
		
		
		if(users.endsWith(",")){
			users = users.substring(0,users.length()-1);
		}
		if(group.endsWith(",")){
			group = group.substring(0,group.length()-1);
		}
		if(role.endsWith(",")){
			role = role.substring(0,role.length()-1);
		}
		
		return SUCCESS;
	}
	
	public String configForm(){
				
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
				
				users = aloneIMP.getUserOrgGroupIdByTaskKey(entity.getTaskkey(),"0");
				role = aloneIMP.getUserOrgGroupIdByTaskKey(entity.getTaskkey(),"1");
				group = aloneIMP.getUserOrgGroupIdByTaskKey(entity.getTaskkey(),"2");
				
				
				if(users.endsWith(",")){
					users = users.substring(0,users.length()-1);
				}
				if(group.endsWith(",")){
					group = group.substring(0,group.length()-1);
				}
				if(role.endsWith(",")){
					role = role.substring(0,role.length()-1);
				}
				
				return SUCCESS;
			}
			case (0): {// 展示
				//entity = aloneIMP.getFrmProcessFormEntity(ids);

				return SUCCESS;
			}
			case (2): {// 修改
				//entity = aloneIMP.getFrmProcessFormEntity(ids);

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

	
	private final static FrmTaskUserManagerInter aloneIMP = (FrmTaskUserManagerInter) BeanFactory
			.getBean("FrmTaskUserProxyId");
	
	
	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	

	public FrmTaskUser getEntity() {
		return entity;
	}

	public void setEntity(FrmTaskUser entity) {
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

	

	private static final Logger log = Logger
			.getLogger(ActionTaskuserFormQuery.class);
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


	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}



	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	
	
}
