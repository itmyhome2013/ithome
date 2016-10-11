package com.ithome.pcs.business.web.action;

import java.util.Map;

import com.ithome.pcs.business.server.ActExLeavetestManagerInter;
import com.ithome.pcs.comm.entity.ActExCompletedform;
import com.ithome.pcs.comm.entity.ActExLeavetest;
import com.ithome.pcs.utils.DateUtil;
import com.ithome.pcs.utils.UUIDGenerator;

import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

public class ActExLeavetestAction extends WebSupport{
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private ActExLeavetest actExLeavetest;//实体类
	private ActExCompletedform actExCompletedform;////实体类
	private String processId;
	private String pcsfromcfgId;
	private String formtableName;
	
	
	
	
	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				String leaveId = UUIDGenerator.getUUID();
				actExLeavetest = new ActExLeavetest();
				actExLeavetest.setProcessid(processId);
				actExLeavetest.setLeaveid(leaveId);
				actExCompletedform = new ActExCompletedform();
				actExCompletedform.setCompletedformid(UUIDGenerator.getUUID());
				actExCompletedform.setProcessid(processId);
				actExCompletedform.setDataid(leaveId);
				actExCompletedform.setPcsfromcfgid(pcsfromcfgId);
				actExCompletedform.setFormtablename(formtableName);
				
				return SUCCESS;
			}
			case (0): {// 展示
				actExLeavetest = this.actExLeavetestManagerInter.getActExLeavetestEntity(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				actExLeavetest = this.actExLeavetestManagerInter.getActExLeavetestEntity(ids);
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
	 * 提交新增数据
	 * 
	 * @return
	 */
	public String addSubmit() {
		try {
			actExCompletedform.setInformant(this.getCurrentUser().getId());
			actExCompletedform.setInfordate(DateUtil.date2Str(DateUtil.datetimeFormat));
			this.actExLeavetestManagerInter.insertLeavetestandCompletedform(actExLeavetest, actExCompletedform, this.getCurrentUser());
			pageset = new PageSet(PageType.ADD,CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.ADD, CommitType.FALSE, e);
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
			this.actExLeavetestManagerInter.editActExLeavetestEntity(actExLeavetest, getCurrentUser());
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.UPDATE, CommitType.FALSE, e);
		}
		return SUCCESS;
	}
	
	
	
	private final static ActExLeavetestManagerInter actExLeavetestManagerInter = (ActExLeavetestManagerInter) BeanFactory
			.getBean("actExLeavetest_ProxyId");
	
	
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
	public ActExLeavetest getActExLeavetest() {
		return actExLeavetest;
	}
	public void setActExLeavetest(ActExLeavetest actExLeavetest) {
		this.actExLeavetest = actExLeavetest;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}



	public ActExCompletedform getActExCompletedform() {
		return actExCompletedform;
	}



	public void setActExCompletedform(ActExCompletedform actExCompletedform) {
		this.actExCompletedform = actExCompletedform;
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



	
	
	
	
	
	
	
}
