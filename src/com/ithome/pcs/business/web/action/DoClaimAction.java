package com.ithome.pcs.business.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.ithome.pcs.business.server.ProcessCoreManagerInter;

import com.farm.console.FarmManager;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;

/**
 * 
 * @项目名称：pcs
 * @名称：DoTaskAction
 * @描述：TODO(流程待签收任务)
 * @创建人： 张晓亮
 * @创建时间：2016年1月12日 下午4:20:26
 * @修改人：张晓亮
 * @修改时间：2016年1月12日 下午4:20:26
 * @修改备注：
 */
public class DoClaimAction extends WebSupport{
	private Map<String, Object> jsonResult = new HashMap<String, Object>();// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private List<EasyUiTreeNode> treeNodes;//系统树形节点
	private List<Map<String,Object>> treejson = new ArrayList<Map<String,Object>>();//自定义树形
	private String taskId;
	private String message;

	
	/**
	 * 
	 * @方法名称: findDoClaimList
	 * @描述：TODO(待签收任务) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findDoClaimList(){
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			query.addRule(new DBRule("D.USER_ID_", this.getCurrentUser().getId(), " = "));
			DataQuery dbQuery = DataQuery
					.init(query,
						  " act_ru_execution a left join act_ru_task b on a.id_ = b.execution_id_ left join act_ex_process c  on a.business_key_ = c.processid left join ACT_RU_IDENTITYLINK d on d.task_id_ = b.id_ and B.ASSIGNEE_ is null  ",
						  "'1',B.ID_ as TASKID,TASK_DEF_KEY_ AS TASKDEFKEY,PROCESSID ,name_ as CURRENTTASKNAME,PROCESSNAME,CREATEUSERNAME,CREATEDATE,PROCDEFNAME,URGENCY,COMMENTS ");    
			DataResult result=dbQuery.search();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("URGENCY"), "URGENCY");
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @方法名称: taskclaim
	 * @描述：TODO(签收任务) 
	 * @返回值类型： String  
	 * @return
	 * @throws Exception
	 */
	public String taskClaim() throws Exception{
		 try{
	       String  assignee=this.iProcessCoreService.findTaskById(taskId).getAssignee();
			if(StringUtils.isEmpty(assignee)){
				this.iProcessCoreService.saveTaskclaim(taskId, this.getCurrentUser().getId());
				this.message="签收成功";
			}else{
				this.message="任务已被其它人签收";
			}
		   
		   pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
		 }catch(Exception e){
	       pageset=PageSets.initPageSet(pageset, PageType.OTHER, CommitType.FALSE, e);
		 }
		 return SUCCESS;
	 }
	
	
	/**
	 * 
	 * @方法名称: forSend
	 * @描述：TODO(页面跳转) 
	 * @返回值类型： String  
	 * @return
	 */
	public String forSend(){
		return SUCCESS;
	}
	
	
	private final static ProcessCoreManagerInter iProcessCoreService = (ProcessCoreManagerInter) BeanFactory
			.getBean("processCore");
	
	//set and get
	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}
	public DataQuery getQuery() {
		return query;
	}
	public void setQuery(DataQuery query) {
		this.query = query;
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
	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}
	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}
	public List<Map<String, Object>> getTreejson() {
		return treejson;
	}
	public void setTreejson(List<Map<String, Object>> treejson) {
		this.treejson = treejson;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
	
	
	
	
	
}
