package com.ithome.autoform.web.action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.ithome.autoform.domain.FrmProcessForm;
import com.ithome.autoform.server.FrmProcessFormManagerInter;
import com.ithome.pcs.business.server.ActExCompletedformManagerInter;
import com.ithome.pcs.comm.entity.ActExCompletedform;
import com.ithome.pcs.utils.DateUtil;
import com.ithome.pcs.utils.UUIDGenerator;
import com.farm.console.FarmManager;
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
 * 流程表单配置信息
 * 
 */
public class ActionFrmProcessFormQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private List<Map<String, Object>> resultList; // 导出结果集
	private DataQuery query;// 条件查询
	private FrmProcessForm entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private Map<String, Object> node;
	private InputStream inputStream;

	private String tableid; // 表单ID
	private String state; // 删除状态 0：禁用，1：正常，2：删除
	private String path; //word文档路径名字
	
	private ActExCompletedform actExCompletedform;////实体类
	private String processId;
	private String pcsfromcfgId;
	private String formtableName;
	private String name; //表单名称
	private String timestamp;
	
	private String taskKey; //任务节点key
	
	private boolean flag = false; // 操作成功标志

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);

			DataResult result = aloneIMP.createFrmProcessFormSimpleQuery(query).search();
			
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("FORMTYPE"), "FROMTYPE"); //表单类型
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNULL"), "ISREQUIRED"); //是否必填
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNULL"), "ISDISABLE"); //是否禁用
			
			resultList = result.getResultList();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"STATE"), "STATE"); // 状态
			result.runformatTime("CRETIME", "yyyy-MM-dd HH:mm:ss");// 

			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}
	
	public String configFormqueryall(){
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);

			query.addRule(new DBRule("TASKKEY",taskKey,"="));
			DataResult result = aloneIMP.createFrmProcessFormSimpleQuery(query).search();
			
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("FORMTYPE"), "FROMTYPE"); //表单类型
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNULL"), "ISREQUIRED"); //是否必填
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNULL"), "ISDISABLE"); //是否禁用
			
			resultList = result.getResultList();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"STATE"), "STATE"); // 状态
			result.runformatTime("CRETIME", "yyyy-MM-dd HH:mm:ss");// 

			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
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
			if ("".equals(entity.getPcsfromcfgid())) {
				entity = aloneIMP.insertFrmProcessFormEntity(entity,
						getCurrentUser());
			} else {
				entity = aloneIMP.editFrmProcessFormEntity(entity,
						getCurrentUser());
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
		try {
			entity = aloneIMP.insertFrmProcessFormEntity(entity,
					getCurrentUser());
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.ADD,
					CommitType.FALSE, e);
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
				aloneIMP.deleteFrmProcessFormEntity(id);
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
		return SUCCESS;
	}
	
	public String openForm(){
		
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				String leaveId = UUIDGenerator.getUUID();
				actExCompletedform = new ActExCompletedform();
				actExCompletedform.setCompletedformid(UUIDGenerator.getUUID());
				actExCompletedform.setProcessid(processId);
				actExCompletedform.setDataid(leaveId);
				actExCompletedform.setPcsfromcfgid(pcsfromcfgId);
				actExCompletedform.setFormtablename(name);
				
				return SUCCESS;
			}
			case (0): {// 展示
				actExCompletedform = aloneCompletedFormIMP.getActExCompletedformEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				actExCompletedform = aloneCompletedFormIMP.getActExCompletedformEntity(ids);
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
	
	public String saveWordForm(){
		
		/*FileSaver fs=new FileSaver(getRequest(),getResponse());
		fs.saveToFile("");
		fs.close();*/
				
		actExCompletedform.setInformant(this.getCurrentUser().getId());
		actExCompletedform.setInfordate(DateUtil.date2Str(DateUtil.datetimeFormat));
		
		String path = actExCompletedform.getPath();
		
		//String destFile = path.substring(0, path.lastIndexOf(".")) + "_" + actExCompletedform.getTimestamp()
		//					+ path.substring(path.lastIndexOf("."));
		
		String destFile = path.substring(0,path.indexOf(actExCompletedform.getFormtablename().substring(0,actExCompletedform.getFormtablename().lastIndexOf(".")))) + actExCompletedform.getFormtablename().substring(0,actExCompletedform.getFormtablename().lastIndexOf(".")) + "_"
							+ actExCompletedform.getTimestamp() + path.substring(path.lastIndexOf("."));
		actExCompletedform.setPath(destFile);
		//CopyFileUtil.copyFile(actExCompletedform.getPath().replace("\\", "/"), destFile.replace("\\", "/"), true);
		
		if(pageset.getPageType() == 1){
			aloneCompletedFormIMP.insertActExCompletedformEntity(actExCompletedform, getCurrentUser());
		}else if(pageset.getPageType() == 2){
			aloneCompletedFormIMP.editActExCompletedformEntity(actExCompletedform, getCurrentUser());
		}
		
		
		//CopyFileUtil.changeName(actExCompletedform.getPath().replace("\\", "/"), destFile.replace("\\", "/"));
		
		flag = true;
		
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
				entity = new FrmProcessForm();
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getFrmProcessFormEntity(ids);

				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getFrmProcessFormEntity(ids);

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
	
	public String configFormView(){
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				entity = new FrmProcessForm();
				entity.setTaskkey(taskKey);
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getFrmProcessFormEntity(ids);

				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getFrmProcessFormEntity(ids);

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

	public String allView() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getFrmProcessFormEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getFrmProcessFormEntity(ids);
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

	private final static FrmProcessFormManagerInter aloneIMP = (FrmProcessFormManagerInter) BeanFactory
			.getBean("FrmProcessFormProxyId");
	
	private final static ActExCompletedformManagerInter aloneCompletedFormIMP = (ActExCompletedformManagerInter) BeanFactory
			.getBean("actExCompletedform_ProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public FrmProcessForm getEntity() {
		return entity;
	}

	public void setEntity(FrmProcessForm entity) {
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

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private static final Logger log = Logger
			.getLogger(ActionFrmProcessFormQuery.class);
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	
	
}
