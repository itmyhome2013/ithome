package com.ithome.pcs.business.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.log4j.Logger;
import com.ithome.pcs.business.server.ActExApprovalManagerInter;
import com.ithome.pcs.business.server.ProcessCoreManagerInter;
import com.ithome.pcs.comm.entity.ActExApproval;
import com.ithome.pcs.comm.entity.ActExCompletedform;
import com.ithome.pcs.utils.DateUtil;
import com.ithome.pcs.utils.UUIDGenerator;

import com.farm.console.FarmManager;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;

/**
 * 
 * @项目名称：pcs
 * @名称：ActExApprovaltestAction
 * @描述：TODO(审批信息)
 * @创建人： 张晓亮
 * @创建时间：2016年1月13日 下午1:58:17
 * @修改人：张晓亮
 * @修改时间：2016年1月13日 下午1:58:17
 * @修改备注：
 */
public class ActExApprovalAction extends WebSupport{
	private Map<String, Object> jsonResult = new HashMap<String, Object>();// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private ActExApproval actExApproval;//实体类
	private ActExCompletedform actExCompletedform;////实体类
	private String taskId;
	private String processId;
	private String pcsfromcfgId;
	private String formtableName;
	private String taskName;
	private String taskDefKey;
	private List<Map<String,String>> backactivitylist;
	private String message;
	
	
	/**
	 * 
	 * @方法名称: findApprovalList
	 * @描述：TODO(审批列表) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findApprovalList(){
		try {
		query = EasyUiUtils.formatGridQuery(getRequest(), query);
		query.addRule(new DBRule("PROCESSID", this.processId, "="));
		query.addSort(new DBSort("APPROVALDATE", "DESC"));
		DataResult result=this.actExApprovalManagerInter.createActExApprovalSimpleQuery(query).search();
		result.runDictionary(FarmManager.instance().findDicTitleForIndex("APPROVALSTATE"), "APPROVALSTATE");
		jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.SUCCESS;
	}
	
	
	
	
	/**
	 * 
	 * @方法名称: findBackactivityId
	 * @描述：TODO(查找可驳回的节点) 
	 * @返回值类型： String  
	 * @return
	 * @throws Exception
	 */
	public String findBackactivityId() throws Exception{
		 List<ActivityImpl> list=this.iProcessCoreService.findBackAvtivity(taskId);
		 backactivitylist = new ArrayList<Map<String,String>>();
		 Collections.reverse(list);
		 for(ActivityImpl ac:list){
			 Map<String,String> map = new HashMap<String, String>();
			 map.put("val", ac.getProperty("name").toString());
			 map.put("key", ac.getId());
			 backactivitylist.add(map);
		 }
		 return this.SUCCESS;
	 }
	
	/**
	 * 
	 * @方法名称: reportVerify
	 * @描述：TODO(上报前检查是否已填了必填的表单) 
	 * @返回值类型： String  
	 * @return
	 */
	public String reportVerify(){
		 try{
		//where a.isrequired='1' and a.taskkey='LEAVE_LEAVEUSERINFO'  and b.dataid is  null
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			query.addRule(new DBRule("A.ISREQUIRED", "1", "="));
			query.addRule(new DBRule("A.TASKKEY", this.taskDefKey, "="));
			query.addUserWhere(" and B.DATAID is null");
			query.setPagesize(100000);
			DataQuery dbQuery = DataQuery
					.init(query,
						  " act_ex_pcsfromcfg a left join (select b.dataid, b.pcsfromcfgid from act_ex_completedform b where b.processid='"+this.processId+"')b on a.pcsfromcfgid=b.pcsfromcfgid ",
						  " '1', a.formname as FORMNAME");
			DataResult result = dbQuery.search();
			List<Map<String, Object>> resultList = result.getResultList();
			if(resultList.isEmpty()){
			    pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
			}else{
				StringBuffer sb = new StringBuffer("表单（");
				for(int i=0;i<resultList.size();i++){
					sb.append(resultList.get(i).get("FORMNAME"));
					if(i!=resultList.size()-1){
						sb.append(",");
					}
				}
				sb.append(")为必填表单,请点击‘可填业务表单’进行填写。");
				this.message=sb.toString();
				pageset = new PageSet(PageType.OTHER, CommitType.FALSE);
			}
			
		 }catch(Exception e){
	       pageset=PageSets.initPageSet(pageset, PageType.OTHER, CommitType.FALSE, e);
		 }
		
		
		return this.SUCCESS;
	}
	
	/**
	 * 
	 * @方法名称: saveActExApproval
	 * @描述：TODO(保存事件审批信息并提交任务) 
	 * @返回值类型： String  
	 * @return
	 * @throws Exception
	 */
	 public String saveApprovalandTaskcomplete() throws Exception{		 
		 try {      
			       actExApproval.setApprovaldate(DateUtil.date2Str(DateUtil.datetimeFormat));
			       actExApproval.setApprovaluserid(this.getCurrentUser().getId());
			       actExApproval.setApprovalusername(this.getCurrentUser().getName());
				    //同意
					 if(actExApproval.getApprovalstate().equals("0")){
						 actExApproval.setSubmitnodekey(null);
						 actExApproval.setSubmitnodekname(null);
						 this.actExApprovalManagerInter.insertActExApprovalEntity(actExApproval, getCurrentUser());
						 this.iProcessCoreService.saveCommitProcess(actExApproval.getTaskid(), null, null);
					 }else{
						 this.actExApprovalManagerInter.insertActExApprovalEntity(actExApproval, getCurrentUser());
						 this.iProcessCoreService.saveCommitProcess(actExApproval.getTaskid(), null, actExApproval.getSubmitnodekey());
					 }
				 pageset = new PageSet(PageType.ADD, CommitType.TRUE);
			} catch (Exception e) {
				pageset=PageSets.initPageSet(pageset, PageType.ADD, CommitType.FALSE, e);
			}
		 return this.SUCCESS;
	 }
	
	/**
	 * 
	 * @方法名称: view
	 * @描述：TODO(审批页面) 
	 * @返回值类型： String  
	 * @return
	 */
     public String view() {
			try {
				switch (pageset.getPageType()) {
				case (1): {// 新增
					   actExApproval = new ActExApproval();
					   actExApproval.setApprovalid(UUIDGenerator.getUUID());
					   actExApproval.setProcessid(processId);
				       actExApproval.setApprovaltaskkey(taskDefKey);
				       actExApproval.setApprovaltaskname(taskName);
				       actExApproval.setTaskid(taskId);
					return SUCCESS;
				}
				case (0): {// 展示
					actExApproval = this.actExApprovalManagerInter.getActExApprovalEntity(ids);
					return SUCCESS;
				}
				case (2): {// 修改
					actExApproval = this.actExApprovalManagerInter.getActExApprovalEntity(ids);
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
	 
	/**
	 * 
	 * @方法名称: forSend
	 * @描述：TODO(页面跳转) 
	 * @返回值类型： String  
	 * @return
	 */
	public String forSend(){
		return this.SUCCESS;
	}
	
	
	
	private final static ActExApprovalManagerInter actExApprovalManagerInter = (ActExApprovalManagerInter) BeanFactory
			.getBean("actExApproval_ProxyId");
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
	


	public ActExApproval getActExApproval() {
		return actExApproval;
	}


	public void setActExApproval(ActExApproval actExApproval) {
		this.actExApproval = actExApproval;
	}


	public ActExCompletedform getActExCompletedform() {
		return actExCompletedform;
	}


	public void setActExCompletedform(ActExCompletedform actExCompletedform) {
		this.actExCompletedform = actExCompletedform;
	}


	public String getProcessId() {
		return processId;
	}


	public void setProcessId(String processId) {
		this.processId = processId;
	}


	public String getPcsfromcfgId() {
		return pcsfromcfgId;
	}


	public void setPcsfromcfgId(String pcsfromcfgId) {
		this.pcsfromcfgId = pcsfromcfgId;
	}

	public String getFormtableName() {
		return formtableName;
	}

	public void setFormtableName(String formtableName) {
		this.formtableName = formtableName;
	}

	public List<Map<String, String>> getBackactivitylist() {
		return backactivitylist;
	}


	public void setBackactivitylist(List<Map<String, String>> backactivitylist) {
		this.backactivitylist = backactivitylist;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}



	public String getTaskName() {
		return taskName;
	}



	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}



	public String getTaskDefKey() {
		return taskDefKey;
	}



	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	private static final Logger log = Logger.getLogger(ActExApprovalAction.class);
	private static final long serialVersionUID = 1L;
	
	
	
	
}
