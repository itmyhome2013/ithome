package com.ithome.pcs.business.web.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.struts2.ServletActionContext;
import com.ithome.pcs.business.server.ProcessCoreManagerInter;
import com.ithome.pcs.comm.Pager;
import com.ithome.pcs.utils.DateUtil;
import com.ithome.pcs.utils.PageUtil;

import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;
/**
 * 
 * @项目名称：pcs
 * @名称：ProcessManageAction
 * @描述：TODO(流程部署管理，流程定义管理Action)
 * @创建人： 张晓亮
 * @创建时间：2016年1月11日 上午10:42:02
 * @修改人：张晓亮
 * @修改时间：2016年1月11日 上午10:42:02
 * @修改备注：
 */

@SuppressWarnings("serial")
public class ProcessManageAction extends WebSupport {
	private Map<String, Object> jsonResult = new HashMap<String, Object>();// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private String processName;// 部署名称
	private File deployProcessZip;// 部署文件
	private String imageName;//流程图片名称
	private ProcessDefinitionEntity processDefinitionEntity;//流程定义实例
	
	/**
	 * 流程部署列表
	 * 
	 * @return
	 */
	public String findDeploymentList() {
		try {
			 query = EasyUiUtils.formatGridQuery(getRequest(), query);
			 Pager pager = PageUtil.getPager(query);
			 DBRule nameRule = query.getAndRemoveRule("name");
			 DeploymentQuery deploymentQuery = this.repositoryService
					                              .createDeploymentQuery()
					                              .orderByDeploymenTime()
					                              .asc();
			if (nameRule!=null && nameRule.getValue()!=null) {
				deploymentQuery.deploymentNameLike("%"+nameRule.getValue()+"%");
			 }
			
			List<Deployment> list = deploymentQuery.listPage(pager.getFirstResult(), pager.getMaxResults());
			List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
			
			for (Deployment dep : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", dep.getId());
				map.put("name", dep.getName());
				map.put("deploymentTime", DateUtil.date2Str(dep.getDeploymentTime(), DateUtil.datetimeFormat));
				jsonList.add(map);
			}

			jsonResult.put("total", deploymentQuery.count());
			jsonResult.put("rows", jsonList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.SUCCESS;
	}

	/**
	 * 部署流程
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveDeployProcess() throws Exception {
		try {
			processName = processName.substring(0, processName.lastIndexOf("."));
			this.iProcessCoreService.saveDeployProcess(deployProcessZip,processName);
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.OTHER, CommitType.FALSE, e);
		}
		return null;
	}

	/**
	 * 删除流程部署信息
	 */
	public String deleteProcessDefinition() throws Exception{
		String[] deploymentIds = ids.split(",");
		try {
			this.iProcessCoreService.deleteDeploymentByIds(deploymentIds);
			pageset = new PageSet(PageType.DEL, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.DEL, CommitType.FALSE, e);
		}
		return this.SUCCESS;
    } 
	
	/**
	 * 流程定义列表
	 * @return
	 */
	  public String findProcessDefinitionList(){
		  try{
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			Pager pager = PageUtil.getPager(query);
			DBRule keyRule = query.getAndRemoveRule("key");
			ProcessDefinitionQuery processDefinitionQuery = this.repositoryService
					.createProcessDefinitionQuery()
					.orderByProcessDefinitionVersion().asc();
			if (keyRule != null && keyRule.getValue() != null) {
				processDefinitionQuery.processDefinitionKeyLike("%"
						+ keyRule.getValue() + "%");
			}
			
			List<ProcessDefinition> list = processDefinitionQuery.listPage(pager.getFirstResult(), pager.getMaxResults());
			
			List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
			for (ProcessDefinition pd : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", pd.getId());
				map.put("deploymentId", pd.getDeploymentId());
				map.put("name", pd.getName());
				map.put("key", pd.getKey());
				map.put("imageName", pd.getDiagramResourceName());
				map.put("version", pd.getVersion());
				map.put("resourcename", pd.getResourceName());
				jsonList.add(map);
			}
				 
				 
			jsonResult.put("total", processDefinitionQuery.count());
			jsonResult.put("rows", jsonList);	 
		  } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return this.SUCCESS;
	    }
	
	
	  
	  /**
	   * 获取流程图
	   */
	  public String findImageInputStream() throws Exception{
		    String[] deploymentIds = ids.split(",");
	    	InputStream in=this.iProcessCoreService.findImageInputStream(deploymentIds[0], imageName);
	    	OutputStream out = ServletActionContext.getResponse().getOutputStream();
	    	for (int b = -1; (b = in.read()) != -1;) {
				out.write(b);
			}
			out.close();
			in.close();
			return null;
	    }
	  
	  /**
	   * 转换为流程模型
	   * @return
	   */
	  public String saveConvertToModel(){
		  try {
			this.iProcessCoreService.saveConvertToModel(processDefinitionEntity.getId());
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.OTHER, CommitType.FALSE, e);
		}
		  return this.SUCCESS;
	  }
	  
	  
	  
	  
	  private final static ProcessCoreManagerInter iProcessCoreService = (ProcessCoreManagerInter) BeanFactory
				.getBean("processCore");
	private final static RepositoryService repositoryService = (RepositoryService) BeanFactory
			.getBean("repositoryService");

	// /////////////////////////////////////////////////get and
	// set/////////////////////////////////////////////////////////
	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public File getDeployProcessZip() {
		return deployProcessZip;
	}

	public void setDeployProcessZip(File deployProcessZip) {
		this.deployProcessZip = deployProcessZip;
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

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public ProcessDefinitionEntity getProcessDefinitionEntity() {
		return processDefinitionEntity;
	}

	public void setProcessDefinitionEntity(
			ProcessDefinitionEntity processDefinitionEntity) {
		this.processDefinitionEntity = processDefinitionEntity;
	}

	

	

}
