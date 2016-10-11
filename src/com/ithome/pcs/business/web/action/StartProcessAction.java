package com.ithome.pcs.business.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Message;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.impl.util.io.StreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DiagramElement;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.spring.SpringProcessEngineConfiguration;
import com.ithome.pcs.business.server.ActExProcessManagerInter;
import com.ithome.pcs.business.server.ActExTaskManagerInter;
import com.ithome.pcs.business.server.ProcessCoreManagerInter;
import com.ithome.pcs.comm.entity.ActExProcess;
import com.ithome.pcs.comm.entity.ActExTask;
import com.ithome.pcs.utils.DateUtil;
import com.ithome.pcs.utils.UUIDGenerator;

import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.spring.BeanFactory;

/**
 * 
 * @项目名称：pcs
 * @名称：StartProcessAction
 * @描述：TODO(启动流程 Action)
 * @创建人： 张晓亮
 * @创建时间：2016年1月11日 上午10:43:57
 * @修改人：张晓亮
 * @修改时间：2016年1月11日 上午10:43:57
 * @修改备注：
 */
public class StartProcessAction extends WebSupport{
	private Map<String, Object> jsonResult = new HashMap<String, Object>();// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private List<EasyUiTreeNode> treeNodes;//系统树形节点
	private List<Map<String,Object>> treejson = new ArrayList<Map<String,Object>>();//自定义树形
	private String procDefId;
	private ProcessDefinition procDefEntity;
	private Deployment deploymentEntity;
	private ActExProcess actExProcessEntity;
	private ActExTask actExTaskEntity;
	
	/**
	 * 
	 * @方法名称: findProcDefTree
	 * @描述：TODO(流程定义树型查询) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findProcDefTree() {
		try {
			// 流程定义查询
			List<ProcessDefinition> procDefList=this.repositoryService.createProcessDefinitionQuery()
					             	.orderByProcessDefinitionVersion()
					             	.latestVersion()
					             	.asc()
					             	.list();
			/*List<ProcessDefinition> procDefList = this.iProcessCoreService
					                                  .findProcessDefinitionList();*/
			
			for (ProcessDefinition procDef : procDefList) {
				//树型数据
				Map<String,Object> attributes = new HashMap<String, Object>();
				attributes.put("DiagramResourceName", procDef.getDiagramResourceName());
				Map<String, Object> treeMap = new HashMap<String, Object>();
				treeMap.put("id", procDef.getId());
				treeMap.put("text", procDef.getName());
				treeMap.put("iconCls", "icon-sprocket_dark");
				treeMap.put("state", "open");
				treeMap.put("checked", "true");
				treeMap.put("attributes", attributes);
				treejson.add(treeMap);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return this.SUCCESS;
	}
	
	/**
	 * 
	 * @方法名称: forSendStartIframe
	 * @描述：TODO(树型加载页面 ) 
	 * @返回值类型： String  
	 * @return
	 */
	public String forSendStartIframe(){
		procDefEntity=this.repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		deploymentEntity= this.repositoryService.createDeploymentQuery().deploymentId(procDefEntity.getDeploymentId()).singleResult();
		return this.SUCCESS;
	}
	
	
	/**
	 * 
	 * @方法名称: forSendActExProcessEntity
	 * @描述：TODO(业务流程表页面) 
	 * @返回值类型： String  
	 * @return
	 */
	public String forSendActExProcessEntity(){
		return this.SUCCESS;
	}
	
	
	/**
	 * 
	 * @方法名称: startProcess
	 * @描述：TODO(正式启动流程) 
	 * @返回值类型： String  
	 * @return
	 */
	public String startProcess(){
		try{
			//添加业务数据
			String businessKey=UUIDGenerator.getUUID();
			actExProcessEntity.setProcessid(businessKey);
			actExProcessEntity.setCreateusername(this.getCurrentUser().getName());
			actExProcessEntity.setCreateuserid(this.getCurrentUser().getId());
			actExProcessEntity.setCreatedate(DateUtil.date2Str(DateUtil.datetimeFormat));
			actExProcessEntity.setIsdeleted("0");
			this.actExProcessManagerInter.insertActExProcessEntity(actExProcessEntity, this.getCurrentUser());
			//起动流程
			Map<String,Object> variables = new HashMap<String, Object>();
			variables.put("inputUser", this.getCurrentUser().getId());
			this.iProcessCoreService.saveStartProcessInstanceByKey(actExProcessEntity.getProcdefkey(), businessKey, variables);
			pageset = new PageSet(PageType.OTHER,CommitType.TRUE);
		}catch(Exception e){
			pageset=PageSets.initPageSet(pageset, PageType.OTHER, CommitType.FALSE, e);
		}
		return this.SUCCESS;
	}
	
	
	
	/**
	 * 跳转
	 * 
	 * @return
	 */
	public String forSend() {
		//添加业务数据
		
		return SUCCESS;
	}
	
	/**
	 * 同步流程节点
	 */
	public void synchronProcess(){
		getResponse().setContentType("text/plain;charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = getResponse().getWriter();
			ProcessDefinition  processDefinition = null;
			if(ids != null && !"".equals(ids.trim())){
				String[] deploymentIds = ids.split(",");
				for (String deploymentId : deploymentIds) {
					processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
					synchronNode(processDefinition);
				}
			}else{
				processDefinition  = repositoryService.createProcessDefinitionQuery().processDefinitionKey(actExProcessEntity.getProcdefkey()).latestVersion().singleResult();
				synchronNode(processDefinition);
			}
			writer.write("true");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("false");
		}
		
	}

	
	//========================帮助方法：根据流程定义同步流程节点===============================================
	private void synchronNode(ProcessDefinition processDefinition) {
		//保存当前部署的流程节点
		String processKey = processDefinition.getKey();
		//版本号
		int version = processDefinition.getVersion();
		//1、判断是否是新部署的流程（两个条件：流程key，版本号）
		List<ActExTask> list = actExTaskManagerInter.findByProcessKeyAndVersion(processKey,version);
		if(list != null && list.size() > 0){
			return;
		}
		 
		//2、根据流程key删除之前保存的流程节点
		actExTaskManagerInter.deleteByProcessKey(processKey);
		 InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
		StreamSource xmlSource = new InputStreamSource(resourceAsStream);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlSource, false, false, processEngineConfiguration.getXmlEncoding());
		org.activiti.bpmn.model.Process process = bpmnModel.getProcesses().get(0);
			Collection<FlowElement> elements = process.getFlowElements();
			for (FlowElement flowElement : elements) {
				String name = flowElement.getClass().getSimpleName();
				if("UserTask".equals(name)){
					String taskKey = flowElement.getId();
		    		String taskName = flowElement.getName();
		    		int rowNum = flowElement.getXmlRowNumber();
		    		//3、保存所有流程节点
					if(actExTaskEntity == null){
						actExTaskEntity = new ActExTask();
					}
					actExTaskEntity.setId(UUIDGenerator.getUUID());
					actExTaskEntity.setProcessKey(processKey);
					actExTaskEntity.setTaskKey(taskKey);
					actExTaskEntity.setTaskName(taskName);
					actExTaskEntity.setVersion(String.valueOf(version));
					actExTaskEntity.setState("0");
					actExTaskEntity.setFlag("0");
					actExTaskEntity.setSort(String.valueOf(rowNum));
					actExTaskManagerInter.insertActExTaskEntity(actExTaskEntity);
				}
				
		}
	}
	
	
	private final static ActExProcessManagerInter actExProcessManagerInter = (ActExProcessManagerInter) BeanFactory
			.getBean("actExProcess_ProxyId");
	private final static ProcessCoreManagerInter iProcessCoreService = (ProcessCoreManagerInter) BeanFactory
			.getBean("processCore");
	private final static ProcessEngineConfiguration processEngineConfiguration = (ProcessEngineConfiguration) BeanFactory
			.getBean("processEngineConfiguration");
	private final static RepositoryService repositoryService = (RepositoryService) BeanFactory
			.getBean("repositoryService");
	private final static RuntimeService runtimeService = (RuntimeService) BeanFactory
			.getBean("runtimeService");
	private final static TaskService taskService = (TaskService) BeanFactory
			.getBean("taskService");
	private final static ActExTaskManagerInter actExTaskManagerInter = (ActExTaskManagerInter) BeanFactory
			.getBean("actExTask_ProxyId");
	
	//get and set 
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


	public String getProcDefId() {
		return procDefId;
	}


	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}


	public ProcessDefinition getProcDefEntity() {
		return procDefEntity;
	}


	public void setProcDefEntity(ProcessDefinition procDefEntity) {
		this.procDefEntity = procDefEntity;
	}


	public Deployment getDeploymentEntity() {
		return deploymentEntity;
	}


	public void setDeploymentEntity(Deployment deploymentEntity) {
		this.deploymentEntity = deploymentEntity;
	}

	public ActExProcess getActExProcessEntity() {
		return actExProcessEntity;
	}

	public void setActExProcessEntity(ActExProcess actExProcessEntity) {
		this.actExProcessEntity = actExProcessEntity;
	}

	public ActExTask getActExTaskEntity() {
		return actExTaskEntity;
	}

	public void setActExTaskEntity(ActExTask actExTaskEntity) {
		this.actExTaskEntity = actExTaskEntity;
	}
	
	
	
	
	
}
