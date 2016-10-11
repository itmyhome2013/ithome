package com.ithome.pcs.business.web.action;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
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
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;
/**
 * 
 * @项目名称：pcs
 * @名称：ProcessModelAction
 * @描述：TODO(流程模型管理Action)
 * @创建人： 张晓亮
 * @创建时间：2016年1月11日 上午10:42:51
 * @修改人：张晓亮
 * @修改时间：2016年1月11日 上午10:42:51
 * @修改备注：
 */
@SuppressWarnings("serial")
public class ProcessModelAction extends WebSupport {
	private Map<String, Object> jsonResult = new HashMap<String, Object>();// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private ModelEntity entity;//模型实体类
	
	 /**
	  * 流程模型信息
	  * @return
	  */
	 public String findProcessModelList(){
	    	try {
	    		query = EasyUiUtils.formatGridQuery(getRequest(), query);
	    		Pager pager = PageUtil.getPager(query);
				DBRule nameRule = query.getAndRemoveRule("name");
	    		ModelQuery modelQuery = this.repositoryService.createModelQuery()
                        .orderByCreateTime().asc()
                        .orderByModelVersion().asc();
	    		if (nameRule!=null && nameRule.getValue()!=null) {
	    			modelQuery.modelNameLike("%"+nameRule.getValue()+"%");
				 }
	    		
				List<Model> list = modelQuery.listPage(pager.getFirstResult(), pager.getMaxResults());

	    		List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();
	    		for(Model model:list){
	    			ModelEntity me = (ModelEntity) model;
	    			Map<String,Object> map = new HashMap<String, Object>();
	    			map.put("id", me.getId());
	    			map.put("name", me.getName());
	    			map.put("key", me.getKey());
	    			map.put("metaInfo", me.getMetaInfo());
	    			map.put("createTime",  DateUtil.date2Str(me.getCreateTime(), DateUtil.datetimeFormat));
	    			map.put("lastUpdateTime", DateUtil.date2Str(me.getLastUpdateTime(), DateUtil.datetimeFormat));
	    			map.put("editorsourceextravalueid", me.getEditorSourceExtraValueId());
	    			jsonList.add(map);
	    		}
	    		jsonResult.put("total", modelQuery.count());
				jsonResult.put("rows", jsonList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return this.SUCCESS;
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
				default:
					break;
				}
			} catch (Exception e) {
				pageset=PageSets.initPageSet(pageset, PageType.OTHER, CommitType.FALSE, e);
			}
			return SUCCESS;
		}
	
	 /**
		 * 创建模型
		 * @return
		 * @throws Exception 
		 */
		public String saveProcessModel() throws Exception{
			try{
				Model model = this.iProcessCoreService.findNewModel();
				model.setName(entity.getName());
				model.setKey(entity.getKey());
				model.setMetaInfo(entity.getMetaInfo());
				this.iProcessCoreService.saveCreateNewModel(model);
				pageset = new PageSet(PageType.ADD, CommitType.TRUE);
			}catch(Exception e){
				pageset=PageSets.initPageSet(pageset, PageType.DEL, CommitType.FALSE, e);
			}
			return SUCCESS;
		}
	 /**
	  * 删除模型
	  * @return
	  * @throws Exception
	  */
	 public String deleteModel() throws Exception{
			try {
				for (String modelId : ParameterUtils.expandDomainPara(ids)) {
					this.iProcessCoreService.deleteModel(modelId);
				}
				pageset = new PageSet(PageType.DEL, CommitType.TRUE);
			} catch (Exception e) {
				pageset=PageSets.initPageSet(pageset, PageType.DEL, CommitType.FALSE, e);
			}
			return this.SUCCESS;
		}
	 
     /**
      * 导出XML
      * @throws Exception
      */
	 public void exportModel() throws Exception{
			ByteArrayInputStream byteArrayInputStream = this.iProcessCoreService.findBpmnModelByteArrayInputStream(entity.getId());
			IOUtils.copy(byteArrayInputStream, this.getResponse().getOutputStream());
	        String filename = entity.getName() + ".bpmn";
	        this.getResponse().setHeader("Content-Disposition", "attachment; filename=" + new String( filename.getBytes("gb2312"), "ISO8859-1" ));
	        this.getResponse().flushBuffer();
		}
	 
	 
	 /**
	  * 部署流程
	  * @return
	  * @throws Exception
	  */
	 public String deployModel() throws Exception {
		 try {
			 this.iProcessCoreService.saveDeployProcess(entity.getId());
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

	// /////////////////////////////////////////////////get and// set/////////////////////////////////////////////////////////
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

	public ModelEntity getEntity() {
		return entity;
	}

	public void setEntity(ModelEntity entity) {
		this.entity = entity;
	}



	

}
