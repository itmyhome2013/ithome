package com.ithome.popu.web.action;

import com.ithome.popu.domain.PopuHuInfo;
import com.ithome.popu.server.PopuHuInfoManagerInter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import com.farm.report.FarmReport;
import com.farm.web.easyui.EasyUiUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

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
import com.farm.core.time.TimeTool;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

/**
 * 房屋基础信息
 * 
 * @author autoCode
 * 
 */
public class ActionPopuHuInfoQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private List<Map<String,Object>> resultList; //导出结果集
	private DataQuery query;// 条件查询
	private PopuHuInfo entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private Map<String, Object> node;
	private InputStream inputStream;

	private String huid;  //户ID
	private String idCard; //身份证号
	private boolean isOnly;  //判断身份证是否唯一
	private boolean isHuFloorOnly;  //判断房屋地址是否唯一
	
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			
			DataResult result = aloneIMP.createPopuHuInfoSimpleQuery(query,getCurrentUserOrg()).search();
			resultList = result.getResultList();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUQUALE"), "HUQUALE"); //户口性质
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUTYPE"), "HUTYPE"); //户口类别
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUSTATE"), "HUSTATE"); //户籍状态
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUSTRUCTURE"), "HUSTRUCTURE"); //家庭结构
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUHOUSEQUALE"), "HUHOUSEQUALE"); //家庭结构
			jsonResult = EasyUiUtils.formatGridData(result);
			
			//根据 CITIZENFLOOR,CITIZENCELL,CITIZENROOMNO查询拼接处房屋地址文件赋值给LIVESADDRESS
			/*ArrayList list = (ArrayList) jsonResult.get("rows");
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> m = (Map<String, Object>) list.get(j);
				{// 房屋地址文本路径的拼接
					ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
					apq.setIds(m.get("HUFLOOR").toString());
					// 加载楼的上层地址
					apq.nodeAllPath();
					// 加载楼的信息
					apq.nodeLoad();
					if (m.get("HUCELL") == null) {
						m.put("LIVESADDRESS", apq.getPathParent() + apq.getNode().get("NAME"));
					} else {
						StringBuffer buf = new StringBuffer(apq.getPathParent() + apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL").get(m.get("HUCELL")));
						
						if(m.get("HUROOMNO")!=null){
							buf.append( m.get("HUROOMNO") + "号");
						}
						
						m.put("LIVESADDRESS", buf.toString());
					}

				}
			}*/
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}
	/**
	 * 身份证唯一性校验 
	 */
	public String idCardCheckOnly(){
		if(idCard!=null&&!"".equals(idCard)){
			isOnly =  aloneIMP.idCardCheckOnly(idCard,huid);
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
			if(entity.getHuid()==null){
				entity = aloneIMP.insertPopuHuInfoEntity(entity, getCurrentUser());
			}else{
				entity = aloneIMP.editPopuHuInfoEntity(entity, getCurrentUser());	
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
			entity = aloneIMP.insertPopuHuInfoEntity(entity, getCurrentUser());
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
				aloneIMP.deletePopuHuInfoEntityByLogic(new BigDecimal(id),
						getCurrentUser());
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

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				// 增加是否出租默认值
				entity = new PopuHuInfo();
				//entity.setHuchuzuis("0");
				//entity.setHucodetype("0");
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getPopuHuInfoEntity(new BigDecimal(ids));
				if (entity.getHufloor() != null) {
					node = loadNode(entity.getHufloor().toString());
				}
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getPopuHuInfoEntity(new BigDecimal(ids));
				if (entity.getHufloor() != null) {
					node = loadNode(entity.getHufloor().toString());  //回显房屋地址
				}
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
				entity = aloneIMP.getPopuHuInfoEntity(new BigDecimal(ids));
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getPopuHuInfoEntity(new BigDecimal(ids));
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
	
	public String report() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			query.setPagesize(Integer.parseInt(AppConfig.getString("config.export.number")));
			queryall();
			// ------------------------开始报表
			//inputStream = FarmReport.newInstance("huInfo.xls").addParameter("result", jsonResult.get("rows")).generate();
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/")
						+ "/WEB-INF/classes/report/resource/户导出信息_"+TimeTool.getTimeDate14()+".xls";
			//使用LinkedHashMap 保证顺序输出
			Map<String,Integer> headName = new LinkedHashMap<String,Integer>();
			headName.put("户主姓名", null);
			headName.put("户地址", null);
			headName.put("户口性质", null);
			headName.put("户主身份证", null);
			List<String> contentName = new ArrayList<String>();
			contentName.add("HUNAME");
			contentName.add("LIVESADDRESS");
			contentName.add("HUQUALE");
			contentName.add("HUIDENTITY");
			
			FarmReport.generateExcel("户信息报表", "户导出信息", path, headName,contentName, resultList);
			inputStream = new FileInputStream(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}
	/**
	 * 获得一个树节点
	 * 
	 * @param code
	 *            节点id
	 * @return
	 */
	private Map<String, Object> loadNode(String code) {
		ActionPopuAddressQuery treeAction = new ActionPopuAddressQuery();
		treeAction.setIds(code);
		treeAction.nodeLoad();
		return treeAction.getNode();
	}

	private final static PopuHuInfoManagerInter aloneIMP = (PopuHuInfoManagerInter) BeanFactory
			.getBean("POPU_HU_INFOProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public PopuHuInfo getEntity() {
		return entity;
	}

	public void setEntity(PopuHuInfo entity) {
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

	public String getHuid() {
		return huid;
	}
	public void setHuid(String huid) {
		this.huid = huid;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public boolean isOnly() {
		return isOnly;
	}
	public void setOnly(boolean isOnly) {
		this.isOnly = isOnly;
	}
	public boolean isHuFloorOnly() {
		return isHuFloorOnly;
	}
	public void setHuFloorOnly(boolean isHuFloorOnly) {
		this.isHuFloorOnly = isHuFloorOnly;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	private static final Logger log = Logger
			.getLogger(ActionPopuHuInfoQuery.class);
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
}
