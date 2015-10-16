package com.farm.console.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.server.contain.ActionManagerInter;
import com.farm.console.server.contain.OrganizationManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

/**
 * 权限资源
 * 
 * @author MAC_alone
 * 
 */
public class ActionPopAction extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneAction entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private String orgId;// 组织机构ID；组织机构实体导航页面使用_zhanghc_2012.9.6
	private String orgName;// 组织机构名称。移动组织机构时用_zhanghc_2012.9.9
	private String pageType;// 页面类型；组织机构实体导航页面使用_zhanghc_2012.9.6
	private String excludeOrgId;// 要排除的组织机构ID。移动组织机构时用_zhanghc_2012.9.9
	private String filterCondits = "";// 附件相关的过滤条件。例：点击组织机构上传附件时，添加它的过滤条件
	private String positionId;// 用于组织机构、区域地点等页面上传下载时的参数
	private String userId;// 用于组织机构、区域地点等页面上传下载时的参数
	private String areaId;// 用于组织机构、区域地点等页面上传下载时的参数
	private String equipmentId;// 用于组织机构、区域地点等页面上传下载时的参数
	private String appId;// 用于组织机构、区域地点等页面上传下载时的参数

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery
					.init(query, "alone_action", "id,name,url,ischeck");
			query.addDefaultSort(new DBSort("utime", "DESC"));
			result = query.search();
			Map<String, String> dictionary = new HashMap<String, String>();
			dictionary.put("0", "否");
			dictionary.put("1", "是");
			result.runDictionary(dictionary, "ISCHECK");
			result.LoadListArray();
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
			log.error(result.getMessage());
		}
		return SUCCESS;
	}
	/**
	 * 查询结果集合demo人员的
	 * 
	 * @return
	 */
	public String queryFileViewAll() {
		try {
			query = DataQuery
					.init(
							query,
							"CCS_FILE a "
									+ "left join CCS_FILEEX b on a.id = b.fileid ",
							"a.id as id, a.fname as fname, a.versioncode as versioncode, a.minname as minname");
			query.addRule(new DBRule("a.pstate", "2", "!="));
			/******************************************************/
			//TODO 如果在超链接上不带参数，查询的是全部的，有问题，未处理！2012.10.7
			//													处理：2012.10.17				
			/******************************************************/
			
			if (positionId != null && !positionId.equals("")) {
				query.addRule(new DBRule("b.POSITIONID",positionId, "="));
			}
			if (userId != null && !userId.equals("")) {
				query.addRule(new DBRule("b.USERID",userId, "="));
			}
			if (orgId != null && !orgId.equals("")) {
				query.addRule(new DBRule("b.ORGID",orgId, "="));
			}
			if (areaId != null && !areaId.equals("")) {
				query.addRule(new DBRule("b.areaId",areaId, "="));
			}
			if (equipmentId != null && !equipmentId.equals("")) {
				query.addRule(new DBRule("b.equipmentId",equipmentId, "="));
			}
			if (appId != null && !appId.equals("")) {
				query.addRule(new DBRule("b.appId",appId, "="));
			}
			if((positionId == null || positionId.equals(""))&&(userId == null || userId.equals(""))&&
					(orgId == null || orgId.equals(""))&&(areaId == null || areaId.equals(""))&&
					(equipmentId == null || equipmentId.equals(""))&&(appId == null || appId.equals(""))){
				query.addRule(new DBRule("a.id", "你妹", "="));
			}
			
			query.addSort(new DBSort("a.etime", "desc"));
			query.addSort(new DBSort("a.id", "desc"));
			query.setPagesize(100);
			result = query.search();
			String imgExStr= FarmManager.instance().getConfigValue("UPLOAD_VIEWTYPE");
			for(Map<String, Object> node:result.getResultList()){
				String exname=node.get("MINNAME").toString().toUpperCase();
				if(imgExStr.toUpperCase().indexOf(exname)>=0){
					node.put("ISIMG","1");
				}else{
					node.put("ISIMG","0");
				}
			}
		} catch (Exception e) {
			DataResults.setException(result, e);
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
			entity.setState("1");
			entity = aloneIMP.editEntity(entity, getCurrentUser());
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.UPDATE, CommitType.FALSE, e
					.toString()
					+ e.getMessage());
			log.error(pageset.getMessage());
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
			entity = aloneIMP.insertEntity(entity, getCurrentUser());
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e.toString()
					+ e.getMessage());
			log.error(pageset.getMessage());
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
				aloneIMP.deleteEntity(id);
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e.toString()
					+ e.getMessage());
			log.error(pageset.getMessage());
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
	 * 组织机构跳转
	 * 
	 * @return
	 */
	public String specifiedOrgForSend() {
		if (excludeOrgId != null && !excludeOrgId.equals("")) {
			orgName = orgManager.getEntity(excludeOrgId).getName();
		}
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
				entity = aloneIMP.getEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.getEntity(ids);
				return SUCCESS;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (pageset == null) {
				pageset = new PageSet(PageType.OTHER, CommitType.FALSE, e
						.toString()
						+ e.getMessage());
			} else {
				pageset.setCommitType(CommitType.FALSE.value);
			}
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	public String fileForSend() {
		StringBuilder sb = new StringBuilder();
		if (positionId != null && !positionId.equals("")) {
			sb.append("b.positionid:=:" + positionId);
		}
		if (userId != null && !userId.equals("")) {
			if (sb.toString() != "") {
				sb.append(",");
			}
			sb.append("b.userid:=:" + userId);
		}
		if (orgId != null && !orgId.equals("")) {
			if (sb.toString() != "") {
				sb.append(",");
			}
			sb.append("b.orgid:=:" + orgId);
		}
		if (areaId != null && !areaId.equals("")) {
			if (sb.toString() != "") {
				sb.append(",");
			}
			sb.append("b.areaid:=:" + areaId);
		}
		if (equipmentId != null && !equipmentId.equals("")) {
			if (sb.toString() != "") {
				sb.append(",");
			}
			sb.append("b.equipmentid:=:" + equipmentId);
		}
		if (appId != null && !appId.equals("")) {
			if (sb.toString() != "") {
				sb.append(",");
			}
			sb.append("b.appId:=:" + appId);
		}
		filterCondits = sb.toString();
		return SUCCESS;
	}

	private final static ActionManagerInter aloneIMP = (ActionManagerInter) BeanFactory
			.getBean("ALONE_Action_DAO_PROXY");
	// 组织机构Manager
	private final static OrganizationManagerInter orgManager = (OrganizationManagerInter) BeanFactory
			.getBean("ALONE_Organization_DAO_PROXY");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public AloneAction getEntity() {
		return entity;
	}

	public void setEntity(AloneAction entity) {
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getExcludeOrgId() {
		return excludeOrgId;
	}

	public void setExcludeOrgId(String excludeOrgId) {
		this.excludeOrgId = excludeOrgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFilterCondits() {
		return filterCondits;
	}

	public void setFilterCondits(String filterCondits) {
		this.filterCondits = filterCondits;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

	private static final Logger log = Logger.getLogger(ActionPopAction.class);
	private static final long serialVersionUID = 1L;
}
