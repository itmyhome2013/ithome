package com.farm.console.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
import com.farm.console.prisist.domain.AloneParameter;
import com.farm.console.server.contain.ParameterManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;

/**
 * 系统参数Action
 * 
 * @author zhang_hc
 * @time 2012-8-31 上午11:47:25
 */
public class ActionAloneParameterQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneParameter entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合

	/* 页面参数 */
	private String domainType;// 参数域类型（1：系统配置页；2：应用参数页）。说明：“系统参数管理”页面按导航栏分开_zhang_hc_2012.8.30
	private String key;// 参数key。用于新增、修改验证_zhang_hc_2012.8.30
	private String pvalue;// 系统参数的值。来自“系统参数管理”提交表单的值_zhang_hc_2012.8.30。第二个字母不可大写，很要命！

	/* 回显数据 */
	List<Map<String, Object>> paramList;// “系统参数管理”页面用到的list集合。如：将字符串解析为list，页面组合为下拉选。_zhang_hc_2012.8.30
	private boolean isRepeatKey;// 用于新增、修改验证_zhang_hc_2012.8.30
	List<Map<String, Object>> propertys;// 用于easyui展示的json

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery.init(//
					query, //
					"Alone_Parameter a", //
					"id,domain,name,pkey,pvalue,vtype,comments");

			query.addSort(new DBSort("a.utime", "desc"));// 按最后修改事件排序
			query.setPagesize(10);
			result = query.search();

			// 状态转义
			result.runDictionary(FarmManager.instance()
					.findDicTitleForIndex("SYSPARAM_DOMAIN"), "DOMAIN");
			result.runDictionary(FarmManager.instance()
					.findDicTitleForIndex("SYSPARAM_TYPE"), "VTYPE");
			HashMap<String, String> transMap = new HashMap<String, String>();
			transMap.put("null", "");
			result.runDictionary(transMap, "COMMENTS");

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

	public String queryallForEasyUi() {
		try {
			query = DataQuery.init(//
					query, //
					"Alone_Parameter a", //
					"id,domain,name,pkey,pvalue,vtype,comments,rules");

			query.addSort(new DBSort("a.name", "asc"));// 按最后修改事件排序
			query.setPagesize(100);
			result = query.search();
			propertys = EasyUiUtils.formatPropertygridData(result
					.getResultList(), "NAME", "PVALUE", "DOMAIN", "VTYPE",
					"RULES", "ID");
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
	 * 提交修改数据
	 * 
	 * @return
	 */
	public String editSubmit() {
		try {
			entity = aloneIMP.editEntity(entity, getCurrentUser());
			FarmManager.instance().refreshConfigMap();
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
			FarmManager.instance().initConfig();
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.UPDATE, CommitType.FALSE, e
					.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 修改系统参数的值
	 * 
	 * @return
	 */
	public String editSubmitByPValue() {
		try {
			String[] paraArrays = ids.split("&2582&");
			for (int i = 0; i < paraArrays.length; i++) {
				String[] paraEntry = paraArrays[i].split("&2581&");
				String id = paraEntry[0];
				String value = paraEntry[1];
				entity = aloneIMP.editSubmitByPValue(id, value,
						getCurrentUser());
			}
			FarmManager.instance().refreshConfigMap();
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
			FarmManager.instance().initConfig();
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.UPDATE, CommitType.FALSE, e
					.getMessage()
					+ e);
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
			entity = aloneIMP.insertEntity(entity, entity.getDomain(),
					getCurrentUser());
			FarmManager.instance().refreshConfigMap();
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
			FarmManager.instance().initConfig();
		} catch (Exception e) {
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e
					.getMessage());
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
				aloneIMP.deleteEntity(id, getCurrentUser());
			}
			FarmManager.instance().refreshConfigMap();
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

	/**
	 * 显示系统参数配置页
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-8-28 下午03:32:40
	 */
	public String paramConfingShow() {
		paramList = aloneIMP.getTransParamList(domainType);
		return SUCCESS;
	}

	/**
	 * 验证是否是重复key
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-7 下午01:51:25
	 */
	public String ValidateIsRepeatKey() {
		isRepeatKey = aloneIMP.isRepeatKey(key.trim(), ids);
		return SUCCESS;
	}

	private final static ParameterManagerInter aloneIMP = (ParameterManagerInter) BeanFactory
			.getBean("ALONE_Parameter_DAO_PROXY");

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

	public AloneParameter getEntity() {
		return entity;
	}

	public void setEntity(AloneParameter entity) {
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

	public List<Map<String, Object>> getParamList() {
		return paramList;
	}

	public void setParamList(List<Map<String, Object>> paramList) {
		this.paramList = paramList;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isIsRepeatKey() {
		return isRepeatKey;
	}

	public void setIsRepeatKey(boolean isRepeatKey) {
		this.isRepeatKey = isRepeatKey;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public List<Map<String, Object>> getPropertys() {
		return propertys;
	}

	public void setPropertys(List<Map<String, Object>> propertys) {
		this.propertys = propertys;
	}

	private static final Logger log = Logger.getLogger(ActionPopAction.class);
	private static final long serialVersionUID = 1L;
}
