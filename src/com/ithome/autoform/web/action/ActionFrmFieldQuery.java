package com.ithome.autoform.web.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.ithome.autoform.domain.CompletedForm;
import com.ithome.autoform.domain.FrmField;
import com.ithome.autoform.domain.FrmTable;
import com.ithome.autoform.server.FrmFieldManagerInter;
import com.ithome.autoform.server.FrmTableManagerInter;

import com.farm.web.easyui.EasyUiUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ParameterAware;

import com.farm.console.FarmManager;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

 /**
 * 实体管理类功能说明
 *
 * @author 作者:自动生成
 * @version 日期(用日期代替版本号)+版本备注
 */ 
public class ActionFrmFieldQuery extends WebSupport implements ParameterAware {
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private FrmField entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private String tableid;  //表单ID
	
	private String fieldParameter;
	private Map parameters; //接收参数
	private List<Object> parameterList;
	private String dataid; //表单主键ID
	
	private String completedformid; //主键ID
	private String processid; //外键（ACT_EX_PROCESS）
	private String pcsfromcfgid; //外键（ACT_EX_PCSFROMCFG）
	private String informant; //填报人
	private String fromtablename;  //表单对应的表名
	
	private CompletedForm completedForm;
	
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			DataResult result=aloneIMP.createFrmFieldSimpleQuery(query,tableid).search();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("FILEDTYPE"), "FILEDTYPE"); //字段类型
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("INPUTTYPE"), "INPUTTYPE"); //输入类型
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("LABELTYPE"), "LABELTYPE"); //标签类型
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNULL"), "ISNULL"); //是否为空
			
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}

	public String operateFormqueryall(){
		
		FrmTable frmTable = aloneTableIMP.getFrmTableEntity(tableid);
		List<FrmField> frmFields = aloneIMP.querFrmFieldsByTableId(tableid);
		StringBuffer fields = new StringBuffer("id,");
		for(int i=0;i<frmFields.size();i++){
			fields.append(frmFields.get(i).getEnname()).append(",");
		}
		
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			DataResult result=aloneIMP.createFrmOperateFormSimpleQuery(query,frmTable.getEnname(),fields.substring(0, fields.length()-1)).search();
			//格式化时间格式
			for(int i=0;i<frmFields.size();i++){
				if("2".equals(frmFields.get(i).getInputtype())){
					result.runformatTime(frmFields.get(i).getEnname().toUpperCase(), "yyyy-MM-dd");
				}
			}
			
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
			if(entity.getId()==null || "".equals(entity.getId())){
				entity = aloneIMP.insertFrmFieldEntity(entity, getCurrentUser()); //新增
			}else{
				entity = aloneIMP.editFrmFieldEntity(entity, getCurrentUser()); //修改	
			}
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.UPDATE, CommitType.FALSE, e);
		}
		return SUCCESS;
	}
	
	public String editOperateSubmit(){
		try {
			fieldParameter = URLDecoder.decode(fieldParameter,"utf-8");  //解码  
			Map<String,String> map = toHashMap(fieldParameter);
			for(Map.Entry<String, String> entry : map.entrySet()){
			    System.out.println(entry.getKey()+" : "+entry.getValue());
			}
			//if("".equals(map.get("id")) || map.get("id") == null){
			if(pageset.getPageType() == 1){
				completedForm = new CompletedForm();
				completedForm.setProcessid(processid);
				completedForm.setPcsfromcfgid(pcsfromcfgid);
				completedForm.setInformant(informant);
				completedForm.setFromtablename(fromtablename);
				entity = aloneIMP.insertFrmOperateFieldEntity(map, completedForm,getCurrentUser()); //新增	
			}else if(pageset.getPageType() == 2){
				entity = aloneIMP.editFrmOperateFieldEntity(map, completedformid,getCurrentUser()); //修改
			}
			
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.UPDATE, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	public HashMap<String, String> toHashMap(Object object) {
		HashMap<String, String> data = new HashMap<String, String>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = (String) jsonObject.get(key);
			
			//过滤以下字段 
			if(!"processid".equals(key) && !"pcsfromcfgid".equals(key) && !"informant".equals(key) && 
					!"fromtablename".equals(key) && !"completedformid".equals(key) && !"dataid".equals(key) && !"pageset.pageType".equals(key)){
				data.put(key, value);
			}
		}
		return data;
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	public String addSubmit() {
		try {
			entity = aloneIMP.insertFrmFieldEntity(entity, getCurrentUser());
			pageset = new PageSet(PageType.ADD,CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.ADD, CommitType.FALSE, e);
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
				aloneIMP.deleteFrmFieldEntity(id,getCurrentUser());
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.DEL, CommitType.FALSE, e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除操作表单
	 * @return
	 */
	public String delOperateSubmit(){
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				aloneIMP.deleteFrmOperateFieldEntity(tableid,id);
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.DEL, CommitType.FALSE, e);
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
	 * 表单预览
	 * @return
	 */
	public String previewForm(){
		ids = dataid;
		return SUCCESS;
	}
	
	/**
	 * 操作表单
	 * @return
	 */
	public String operateForm(){
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
				entity = aloneIMP.getFrmFieldEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getFrmFieldEntity(ids);
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

	private final static FrmFieldManagerInter aloneIMP = (FrmFieldManagerInter) BeanFactory
			.getBean("FRM_FIELDProxyId");
	
	private final static FrmTableManagerInter aloneTableIMP = (FrmTableManagerInter) BeanFactory
			.getBean("FrmTableProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public FrmField getEntity() {
		return entity;
	}

	public void setEntity(FrmField entity) {
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
	
	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getFieldParameter() {
		return fieldParameter;
	}

	public void setFieldParameter(String fieldParameter) {
		this.fieldParameter = fieldParameter;
	}

	
	

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public List<Object> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<Object> parameterList) {
		this.parameterList = parameterList;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getPcsfromcfgid() {
		return pcsfromcfgid;
	}

	public void setPcsfromcfgid(String pcsfromcfgid) {
		this.pcsfromcfgid = pcsfromcfgid;
	}

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getFromtablename() {
		return fromtablename;
	}

	public void setFromtablename(String fromtablename) {
		this.fromtablename = fromtablename;
	}

	private static final Logger log = Logger.getLogger(ActionFrmFieldQuery.class);
	public CompletedForm getCompletedForm() {
		return completedForm;
	}

	public void setCompletedForm(CompletedForm completedForm) {
		this.completedForm = completedForm;
	}

	public String getCompletedformid() {
		return completedformid;
	}

	public void setCompletedformid(String completedformid) {
		this.completedformid = completedformid;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 加载树节点 public String loadTreeNode() { treeNodes =
	 * EasyUiTreeNode.formatAjaxTree(EasyUiTreeNode .queryTreeNodeOne(id,
	 * "SORT", "alone_menu", "ID", "PARENTID", "NAME").getResultList(),
	 * EasyUiTreeNode .queryTreeNodeTow(id, "SORT", "alone_menu", "ID",
	 * "PARENTID", "NAME").getResultList(), "PARENTID", "ID", "NAME"); return
	 * SUCCESS; }
	 * @return
	 */
}
