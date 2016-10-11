package com.ithome.pcs.business.web.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import com.ithome.pcs.business.server.ProcessCoreManagerInter;

import com.farm.console.FarmManager;
import com.farm.core.config.AppConfig;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
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
 * @描述：TODO(流程待办任务)
 * @创建人： 张晓亮
 * @创建时间：2016年1月12日 下午4:20:26
 * @修改人：张晓亮
 * @修改时间：2016年1月12日 下午4:20:26
 * @修改备注：
 */
public class DoTaskAction extends WebSupport{
	private Map<String, Object> jsonResult = new HashMap<String, Object>();// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private List<EasyUiTreeNode> treeNodes;//系统树形节点
	private List<Map<String,Object>> treejson = new ArrayList<Map<String,Object>>();//自定义树形
	private String taskId;
	private String taskDefKey;
	private String processId;
	private String taskName;
	private String message;
	private String handleType;//办理类型（0：办理，1：查看，2.已归档查看）
	
	
	/**
	 * 
	 * @方法名称: findDoTaskList
	 * @描述：TODO(代办任务列表) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findDoTaskList(){
		try {

			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			query.addRule(new DBRule("B.ASSIGNEE_", this.getCurrentUser().getId(), "="));
			query.addSort(new DBSort("C.URGENCY", "desc"));
			DataQuery dbQuery = DataQuery
					.init(query,
							"  act_ru_execution a left join act_ru_task b on a.id_ = b.execution_id_ left join act_ex_process c on a.business_key_ = c.processid ",
							"'1', B.ID_ as TASKID,TASK_DEF_KEY_ AS TASKDEFKEY, NAME_ as TASKNAME,PROCESSID ,name_ as CURRENTTASKNAME,PROCESSNAME,CREATEUSERNAME,CREATEDATE,PROCDEFNAME,URGENCY,COMMENTS");
			DataResult result = dbQuery.search();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("URGENCY"), "URGENCY");
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @方法名称: findviewCurrentImage
	 * @描述：TODO(查看当前流程图) 
	 * @返回值类型： String  
	 * @return
	 * @throws Exception
	 */
	 public String findviewCurrentImage() throws Exception{
		 InputStream in= this.iProcessCoreService.findviewCurrentImage(taskId);
		 OutputStream out = ServletActionContext.getResponse().getOutputStream();
			// 4：将输入流中的数据读取出来，写到输出流中
			for (int b = -1; (b = in.read()) != -1;) {
				out.write(b);
			}
			out.close();
			in.close();
			// 将图写到页面上，用输出流写
			return null;
	 }
	
	/**
	 * 
	 * @方法名称: fillFormList
	 * @描述：TODO(当前任务可填表单) 
	 * @返回值类型： String  
	 * @return
	 */
	 public String doTaskfillFormList() {
			try{
				query = EasyUiUtils.formatGridQuery(getRequest(), query);
				query.setPagesize(100000);
				query.addRule(new DBRule("A.TASKKEY", this.taskDefKey, "="));
				query.addRule(new DBRule("A.ISDISABLE","0", "="));
				DataQuery dbQuery = DataQuery
						.init(query,
							  " act_ex_pcsfromcfg a left join act_ex_completedform b on a.pcsfromcfgid = b.pcsfromcfgid and a.formtablename = b.formtablename and B.PROCESSID = '"+processId+"'",
							  "'1', A.PCSFROMCFGID as PCSFROMCFGID, A.TASKKEY as TASKKEY, A.TASKNAME as TASKNAME,A.formtableid as FORMTABLEID,A.FORMNAME as FORMNAME, A.FROMTYPE as FROMTYPE,A.ISREQUIRED as ISREQUIRED,A.FORMTABLENAME as FORMTABLENAME,A.FORMURL as FORMURL,A.ISDISABLE as ISDISABLE,B.DATAID as DATAID,B.INFORMANT as INFORMANT,B.PATH as PATH");
				query.addUserWhere(" and b.dataid is null");  //过滤未填写表单
				DataResult result = dbQuery.search();
				List<Map<String, Object>> resultList = result.getResultList();
				for(int i=0;i<resultList.size();i++){
					Map<String, Object> rowmap =resultList.get(i);
					String fromType =rowmap.get("FROMTYPE").toString();
					String pcsfromcfgId=rowmap.get("PCSFROMCFGID").toString();
					String formtableName =rowmap.get("FORMTABLENAME").toString();
					String url="";
					//表单类型（0：url,1：表单,2:word）
					if(fromType.equals("0")){
						url =rowmap.get("FORMURL").toString();
						//dataId 为空表示未填报过
						if(null==rowmap.get("DATAID")){
							url=url+"?pageset.pageType=1&processId="+processId+"&pcsfromcfgId="+pcsfromcfgId+"&formtableName="+formtableName;
						}else{
							url=url+"?pageset.pageType=2&ids="+rowmap.get("DATAID")+"&processId="+processId+"&pcsfromcfgId="+pcsfromcfgId+"&formtableName="+formtableName;
						}
					}else if(fromType.equals("2")){ 
						if(null!=rowmap.get("DATAID")){ //修改
							url = "admin/FrmPageOffice.do?pageset.pageType=2&ids="+rowmap.get("DATAID")+"&path=" + rowmap.get("PATH").toString() //+ rowmap.get("FORMTABLENAME").toString()
							+"&processId="+processId+"&pcsfromcfgId="+pcsfromcfgId+"&name="+formtableName+"&timestamp="+System.currentTimeMillis();
						}else{ //新增
							url = "admin/FrmPageOffice.do?pageset.pageType=1&path=" + rowmap.get("FORMURL").toString() + rowmap.get("FORMTABLENAME").toString()
							+"&processId="+processId+"&pcsfromcfgId="+pcsfromcfgId+"&name="+formtableName+"&timestamp="+System.currentTimeMillis();
						}
						
					}else if(fromType.equals("1")){
						if(null!=rowmap.get("DATAID")){ //修改
							url = "admin/FrmFormTableshow.do?pageset.pageType=2&ids="+rowmap.get("DATAID")+"&processId="+processId+"&pcsfromcfgId="+pcsfromcfgId + "&tableid=" + rowmap.get("FORMTABLEID");	
						}else{
							url = "admin/FrmFormTableshow.do?pageset.pageType=1&processid="+processId+"&pcsfromcfgid="+pcsfromcfgId+"&formtablename="+formtableName+"&tableid=" + rowmap.get("FORMTABLEID");
						}
						
					}else{
						
					}
					
					Map<String,Object> attributes = new HashMap<String, Object>();
					attributes.put("url", url);
					Map<String, Object> treeMap = new HashMap<String, Object>();
					treeMap.put("id", rowmap.get("PCSFROMCFGID"));
					treeMap.put("dataid", rowmap.get("DATAID"));
					treeMap.put("text", rowmap.get("FORMNAME"));
					treeMap.put("iconCls", "icon-future-projects");
					treeMap.put("state", "open");
					treeMap.put("checked", "true");
					treeMap.put("attributes", attributes);
					treejson.add(treeMap);
				}
				
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			
	       return this.SUCCESS;
		}
	 
	 
	    /**
	     * 
	     * @方法名称: completedFormList
	     * @描述：TODO(已填写表单列表) 
	     * @返回值类型： String  
	     * @return
	     */
		public String completedFormList() {
			try{
				query = EasyUiUtils.formatGridQuery(getRequest(), query);
				query.addRule(new DBRule("B.PROCESSID", processId, "="));
				query.addSort(new DBSort("b.infordate", "desc"));
				query.setPagesize(100000);
				DataQuery dbQuery = DataQuery
						.init(query,
							  " act_ex_pcsfromcfg a left join act_ex_completedform b on a.pcsfromcfgid = b.pcsfromcfgid and a.formtablename = b.formtablename",
							  " '1', A.PCSFROMCFGID as PCSFROMCFGID, A.TASKKEY as TASKKEY, A.TASKNAME as TASKNAME,A.formtableid as FORMTABLEID,A.FORMNAME as FORMNAME, A.FROMTYPE as FROMTYPE,A.ISREQUIRED as ISREQUIRED,A.FORMTABLENAME as FORMTABLENAME,A.FORMURL as FORMURL,A.ISDISABLE as ISDISABLE,B.DATAID as DATAID ,B.PATH as PATH");
				DataResult result = dbQuery.search();
				List<Map<String, Object>> resultList = result.getResultList();
				for(int i=0;i<resultList.size();i++){
					Map<String, Object> rowmap =resultList.get(i);
					String fromType =rowmap.get("FROMTYPE").toString();
					String pcsfromcfgId=rowmap.get("PCSFROMCFGID").toString();
					String formtableName =rowmap.get("FORMTABLENAME").toString();
					String url="";
					//表单类型（0：url,1：实体）
					if(fromType.equals("0")){
						url =rowmap.get("FORMURL").toString();
							url=url+"?pageset.pageType=0&ids="+rowmap.get("DATAID");
					}else if(fromType.equals("2")){ 
						url = "admin/FrmPageOfficeView.do?name=" + rowmap.get("PATH").toString();
					}else if(fromType.equals("1")){
						if(null!=rowmap.get("DATAID")){ //修改
							url = "admin/FrmFormTableshow.do?pageset.pageType=2&ids="+rowmap.get("DATAID")+"&processId="+processId+"&pcsfromcfgId="+pcsfromcfgId + "&tableid=" + rowmap.get("FORMTABLEID");	
						}else{
							url = "admin/FrmFormTableshow.do?pageset.pageType=1&processid="+processId+"&pcsfromcfgid="+pcsfromcfgId+"&formtablename="+formtableName+"&tableid=" + rowmap.get("FORMTABLEID");
						}
						
					}else{
						
					}
					
					Map<String,Object> attributes = new HashMap<String, Object>();
					attributes.put("url", url);
					Map<String, Object> treeMap = new HashMap<String, Object>();
					treeMap.put("id", rowmap.get("PCSFROMCFGID"));
					treeMap.put("dataid", rowmap.get("DATAID"));
					treeMap.put("text", rowmap.get("FORMNAME")+"("+rowmap.get("TASKNAME")+")");
					treeMap.put("iconCls", "icon-finished-work");
					treeMap.put("state", "open");
					treeMap.put("checked", "true");
					treeMap.put("attributes", attributes);
					treejson.add(treeMap);
				}
				
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			
	       return this.SUCCESS;
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

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	
	
	
	
	
	
	
	
}
