package com.ithome.pcs.business.web.action;

import java.util.HashMap;
import java.util.Map;

import com.farm.console.FarmManager;
import com.farm.core.page.PageSet;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;

/**
 * 
 * @项目名称：pcs
 * @名称：AlreadyHandledAction
 * @描述：TODO(任务管理-已办理)
 * @创建人： 张晓亮
 * @创建时间：2016年1月14日 下午3:57:30
 * @修改人：张晓亮
 * @修改时间：2016年1月14日 下午3:57:30
 * @修改备注：
 */
public class AlreadyHandledAction extends WebSupport{
	private Map<String, Object> jsonResult = new HashMap<String, Object>();// 结果集合
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	
	
	
	/**
	 * 
	 * @方法名称: findMyProcessList
	 * @描述：TODO(已办理) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findAlreadyHandledList(){
		try {

			query = EasyUiUtils.formatGridQuery(getRequest(), query);
			query.addSort(new DBSort("B.URGENCY", "desc"));
			query.addUserWhere(" and a.proc_inst_id_ in ( select distinct (t.proc_inst_id_)  from act_hi_taskinst t where t.end_time_ is not null and t.assignee_ = '"+this.getCurrentUser().getId()+"' )  ");
			DataQuery dbQuery = DataQuery
					.init(query,
							" act_hi_procinst a left join act_ex_process b on a.business_key_ = b.processid  ",
							"'1',PROCESSID ,PROCESSNAME,CREATEUSERNAME,CREATEDATE,PROCDEFNAME,URGENCY,COMMENTS");
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
	 * @方法名称: forSend
	 * @描述：TODO(页面跳转) 
	 * @返回值类型： String  
	 * @return
	 */
	public String forSend(){
		return this.SUCCESS;
	}
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
	
	
	
}
