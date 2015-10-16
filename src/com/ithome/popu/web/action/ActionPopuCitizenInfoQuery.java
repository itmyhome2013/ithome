package com.ithome.popu.web.action;


import java.io.InputStream;
import java.math.BigDecimal;
import com.farm.report.FarmReport;
import com.farm.web.easyui.EasyUiUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

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
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;
import com.ithome.popu.domain.PopuCitizenInfo;
import com.ithome.popu.domain.PopuHuInfo;
import com.ithome.popu.server.PopuCitizenInfoManagerInter;
import com.ithome.popu.server.PopuHuInfoManagerInter;

/**
 * 人口基础信息
 * 
 * @author autoCode
 * 
 */
public class ActionPopuCitizenInfoQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private PopuCitizenInfo entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private BigDecimal huid;
	private Map<String, Object> node;
	private String liveaddress; //现居住地址
	private InputStream inputStream;
	
	private String citizenid;  //居民ID
	private String idCard; //身份证号
	private boolean isOnly;  //判断身份证是否唯一 
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			DBRule rule1 = query.getAndRemoveRule("CITIZENBIRTH");// 生日起始条件
			DBRule rule2 = query.getAndRemoveRule("CITIZENBIRTH");// 生日结束条件
			DBRule rule3 = query.getAndRemoveRule("CITIZENJZDZ");// 居住地址条件
			query = aloneIMP.createPopuCitizenInfoSimpleQuery(query,getCurrentUserOrg());
			if (rule1 != null) {
				query.addUserWhere(" and CITIZENBIRTH "
						+ rule1.getComparaSign() + " to_date( '"
						+ rule1.getValue() + "' , 'yyyy-mm-dd' )");
			}
			if (rule2 != null) {
				query.addUserWhere(" and CITIZENBIRTH "
						+ rule2.getComparaSign() + " to_date( '"
						+ rule2.getValue() + "' , 'yyyy-mm-dd' )");
			}
			if (rule3 != null) {
				query.addUserWhere(" and b.HUFLOOR like '"
						+ rule3.getComparaSign() + "%'");
			}
			DataResult result = query.search();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUHOUSERELATION"), "CITIZENHURELATION");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("CITIZEN_LIVESTATE"), "CITIZENLIVESTATE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("SEX"), "CITIZENSEX");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("IDCard"), "CITIZENCARDTYPE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("CITIZEN_MINGZU"), "CITIZENNATION");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("CITIZEN_ZZMM"), "CITIZENSTATUS");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("CITIZEN_WHCD"), "CITIZENEDUCATION");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("CITIZEN_ZZWWQKLX"), "CITIZENZZWW");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("CITIZEN_JNYLBXLX"), "CITIZENYIBAO");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNOT"), "CITIZENSERVICE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNOT"), "IS_CARE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNOT"), "IS_DISABLE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNOT"), "IS_WED");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNOT"), "IS_DIBAO");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("ISNOT"), "IS_SFLTX");
			
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUQUALE"), "CITIZENHUQUALITY"); //户口性质
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("HUSTATE"), "CITIZENHUSTATE"); //户籍状态
			
			result.runformatTime("CREATEDATE", "yyyy-MM-dd");
			result.runformatTime("UPDATEDATE", "yyyy-MM-dd");
			result.runformatTime("CITIZENBIRTH", "yyyy-MM-dd");
			result.runformatTime("CITIZENBOOKDATE", "yyyy-MM-dd");
			jsonResult = EasyUiUtils.formatGridData(result);
			
			//根据 CITIZENFLOOR,CITIZENCELL,CITIZENROOMNO查询拼接处房屋地址文件赋值给LIVESADDRESS
			/*ArrayList list = (ArrayList) jsonResult.get("rows");
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> m = (Map<String, Object>) list.get(j);
				{// 房屋地址文本路径的拼接
					ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
					apq.setIds(m.get("CITIZENFLOOR").toString());
					// 加载楼的上层地址
					apq.nodeAllPath();
					// 加载楼的信息
					apq.nodeLoad();
					if (m.get("CITIZENCELL") == null) {
						m.put("LIVESADDRESS", apq.getPathParent() + apq.getNode().get("NAME"));
					} else {
						StringBuffer buf = new StringBuffer(apq.getPathParent() + apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL").get(m.get("CITIZENCELL")));
						
						if(m.get("CITIZENROOMNO")!=null){
							buf.append( m.get("CITIZENROOMNO") + "号");
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
	public String report() {
		try {
			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			query.setPagesize(Integer.parseInt(AppConfig.getString("config.export.number")));
			queryall();
			// ------------------------开始报表
			inputStream = FarmReport.newInstance("citizenInfo.xls").addParameter(
					"result", jsonResult.get("rows")).generate();
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
			isOnly =  aloneIMP.idCardCheckOnly(idCard,citizenid,huid);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据户信息查询人口信息
	 * 
	 * @return
	 */
	public String huCitizenInfoqueryAll() {
		try {
			StringBuffer buf = new StringBuffer();
			String hids[] = ids.split(",");
			for (int i = 0; i < hids.length; i++) {
				buf.append("'").append(hids[i]).append("'").append(",");
			}
			String queryWhere = ""; // 查询条件， 根据huid查询人口信息
			if (!"".equals(ids)) {
				queryWhere = "and a.huid in("
						+ buf.substring(0, buf.length() - 1) + ")";
			}

			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			query.setDefaultSort(new DBSort("CITIZENHURELATION","ASC"));
			query = aloneIMP.createPopuCitizenInfoSimpleQuery(query);
			DataResult result = query.addUserWhere(queryWhere).search();
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"HUHOUSERELATION"), "CITIZENHURELATION");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"CITIZEN_LIVESTATE"), "CITIZENLIVESTATE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"SEX"), "CITIZENSEX");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"IDCard"), "CITIZENCARDTYPE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"CITIZEN_MINGZU"), "CITIZENNATION");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"CITIZEN_ZZMM"), "CITIZENSTATUS");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"CITIZEN_WHCD"), "CITIZENEDUCATION");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"CITIZEN_ZZWWQKLX"), "CITIZENZZWW");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"CITIZEN_JNYLBXLX"), "CITIZENYIBAO");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"ISNOT"), "CITIZENSERVICE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"ISNOT"), "IS_CARE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"ISNOT"), "IS_DISABLE");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"ISNOT"), "IS_WED");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"ISNOT"), "IS_DIBAO");
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"ISNOT"), "IS_SFLTX");
			
			result.runDictionary(FarmManager.instance().findDicTitleForIndex(
					"HUQUALE"), "CITIZENHUQUALITY");
			
			result.runformatTime("CITIZENBIRTH", "yyyy-MM-dd");

			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}

	/**
	 * 编辑
	 * 
	 * @return
	 */
	public String editSubmit() {
		try {
			if (entity.getHuid() == null) {
				 throw new RuntimeException("未获得正确房ID");
			}
			if (entity.getCitizenid() == null) {
				entity = aloneIMP.insertPopuCitizenInfoEntity(entity,
						getCurrentUser());
			} else {
				entity = aloneIMP.editPopuCitizenInfoEntity(entity,
						getCurrentUser());
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.ADD,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	public String addSubmit() {
		try {
			// 统一走编辑方法,此功能不使用
			entity = aloneIMP.editPopuCitizenInfoEntity(entity,
					getCurrentUser());
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.UPDATE,
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
				aloneIMP.deletePopuCitizenInfoEntityByLogic(new BigDecimal(id),
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
				entity = new PopuCitizenInfo();
				if (huid != null) {
					entity.setHuid(huid);
					PopuHuInfo huinfo = aloneHuinfoIMP.getPopuHuInfoEntity(huid);
					if (huinfo.getHufloor() != null) {
						node = loadNode(huinfo.getHufloor().toString());  //回显房屋地址
					}
					entity.setCitizenfloor(node.get("ENTITYTYPE").toString());
					entity.setCitizenfloortitle(node.get("NAME").toString());
					entity.setCitizencell(huinfo.getHucell());
					entity.setCitizenroomno(huinfo.getHuroomno());
				}
				// 人口基本信息的默认值
				entity.setCitizenlivestate("1");
				//entity.setCitizencardtype("0");
				//entity.setCitizennation("1");
				/*entity.setIsCare("0");
				entity.setIsDibao("0");
				entity.setIsDisable("0");
				entity.setIsWed("0");
				entity.setIsSfltx("0");
				entity.setCitizenservice("0");*/
				//entity.setIsPlanbreed("0");
				//entity.setCitizenstatus("12"); //默认群众 
				entity.setCitizenbookdate(new Date());
				
				liveaddress = aloneHuinfoIMP.getPopuHuInfoEntity(huid).getLivesaddress();
				
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getPopuCitizenInfoEntity(new BigDecimal(ids));
				
				if (entity.getCitizenfloor() != null) {
					node = loadNode(entity.getCitizenfloor().toString());  //回显房屋地址
				}
				
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getPopuCitizenInfoEntity(new BigDecimal(ids));
				
				if (entity.getCitizenfloor() != null) {
					node = loadNode(entity.getCitizenfloor().toString());  //回显房屋地址
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

	public String allView() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.getPopuCitizenInfoEntity(new BigDecimal(ids));
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getPopuCitizenInfoEntity(new BigDecimal(ids));
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

	private final static PopuCitizenInfoManagerInter aloneIMP = (PopuCitizenInfoManagerInter) BeanFactory
			.getBean("POPU_CITIZEN_INFOProxyId");
	private final static PopuHuInfoManagerInter aloneHuinfoIMP = (PopuHuInfoManagerInter) BeanFactory
			.getBean("POPU_HU_INFOProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public PopuCitizenInfo getEntity() {
		return entity;
	}

	public void setEntity(PopuCitizenInfo entity) {
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

	public BigDecimal getHuid() {
		return huid;
	}

	public void setHuid(BigDecimal huid) {
		this.huid = huid;
	}

	public String getLiveaddress() {
		return liveaddress;
	}

	public void setLiveaddress(String liveaddress) {
		this.liveaddress = liveaddress;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Map<String, Object> getNode() {
		return node;
	}
	public void setNode(Map<String, Object> node) {
		this.node = node;
	}

	public String getCitizenid() {
		return citizenid;
	}
	public void setCitizenid(String citizenid) {
		this.citizenid = citizenid;
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

	private static final Logger log = Logger
			.getLogger(ActionPopuCitizenInfoQuery.class);
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
