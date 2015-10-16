package com.ithome.popu.web.action;

import java.math.BigDecimal;

import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
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

/**
 * 人口基础信息
 * 
 * @author autoCode
 * 
 */
public class ActionPopuAddressQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	//private PopuCitizenInfo entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private List<EasyUiTreeNode> treeNodes;
	private String id;
	private Map<String, Object> node;
	private String pathParent;
	private String isBuilding; //是否平房楼房

	public String treeLoad() throws SQLException {
		boolean oneIs=true;
		if (id == null) {
			oneIs=true;
			id = getCurrentUserOrg().getTreecode();
		}else{
			oneIs=false;
		}
		if (!getCurrentUserOrg().getType().equals("5")) {
			treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(
					EasyUiTreeNode.queryTreeNodeOne(id, "CODE", "ADDRESS_TREE",
							"CODE", "PARENTCODE", "NAME", "LEVELS", "")
							.getResultList(), EasyUiTreeNode.queryTreeNodeTow(
							id, "CODE", "ADDRESS_TREE", "CODE", "PARENTCODE",
							"NAME", "LEVELS").getResultList(), "PARENTCODE",
					"CODE", "NAME", "LEVELS")
					
					;
		} else {
			if(oneIs){
				DataQuery query = DataQuery.getInstance("1",
						"CODE,PARENTCODE,NAME,LEVELS", "(select distinct d.*,d.GRIDCODE as agridcode,b.GRIDCODE as bgridcode from ADDRESS_TREE d left join ADDRESS_TREE b on d.code = b.parentcode)");
				query.setPagesize(10000);
				query.setUserWhere(" and ( aGRIDCODE = '"+id+"' or bGRIDCODE = '"+id+"')");
				
				DataResult result = query.search();
				treeNodes = EasyUiTreeNode.formatTreeNodes(result.getResultList(),
						"PARENTCODE", "CODE", "NAME", "LEVELS");
			}else{
				DataQuery query = DataQuery.getInstance("1",
						"CODE,PARENTCODE,NAME,LEVELS", "(select distinct d.*,d.GRIDCODE as agridcode,b.GRIDCODE as bgridcode from ADDRESS_TREE d left join ADDRESS_TREE b on d.code = b.parentcode)");
				query.setPagesize(10000);
				query.setUserWhere(" and ( aGRIDCODE = '"+getCurrentUserOrg().getTreecode()+"' or bGRIDCODE = '"+getCurrentUserOrg().getTreecode()+"')");
				query.addUserWhere(" and PARENTCODE = '"+id+"'");
				DataResult result = query.search();
				System.out.println(getCurrentUserOrg().getTreecode());
				treeNodes = EasyUiTreeNode.formatTreeNodes(result.getResultList(),
						"PARENTCODE", "CODE", "NAME", "LEVELS");
			}
		}
		  
		//房屋地址排序 
		/*for (int i = 0; i < treeNodes.size() - 1; i++) {
			for (int j = 0; j < treeNodes.size() - i - 1; j++) {
				String regEx="[^0-9]";   
				Pattern p = Pattern.compile(regEx);   
				Matcher m = p.matcher(treeNodes.get(j).getText());   
				Matcher m2 = p.matcher(treeNodes.get(j+1).getText());   
				
				if(Integer.parseInt(m.replaceAll("").trim().equals("")?"0":m.replaceAll("").trim()) > Integer.parseInt( m2.replaceAll("").trim().equals("")?"0":m2.replaceAll("").trim())){ //前一个数和后一个数比较
					String a = treeNodes.get(j).getText();
					treeNodes.get(j).setText(treeNodes.get(j+1).getText());
				    treeNodes.get(j+1).setText(a);
				}
			}
		}*/
		
		return SUCCESS;
	}


	public String nodeLoad() {
		DataQuery query = DataQuery.getInstance("1",
				"NAME,ENTITYTYPE,ENTITY,ID,PARENTID,TREECODE", "alone_dictionary_type");
		query.addRule(new DBRule("id", ids, "="));
		try {
			DataResult result = query.search();
			List<Map<String, Object>> nodes = result.getResultList();
			if (nodes.size() > 0) {
				node = nodes.get(0);
			} else {
				node = new HashMap<String, Object>();
				//node.put("NAME", "");
			}
		} catch (SQLException e) {
			node = new HashMap<String, Object>();
			node.put("CODE", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getParentCode(){
		DataQuery query = DataQuery.getInstance("1","CODE,PARENTCODE", "ADDRESS_TREE");
		query.addRule(new DBRule("CODE", ids, "="));
		try {
			DataResult result = query.search();
			List<Map<String, Object>> nodes = result.getResultList();
			if(nodes.size()>0){
				node = nodes.get(0);	
			}else{
				node = new HashMap<String,Object>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String nodeBuilding(){
		DataQuery query2 = DataQuery.getInstance("1",
				"NAME,ENTITYTYPE,ENTITY,ID,PARENTID", "alone_dictionary_type");
		if(ids==null||"".equals(ids)){
			ids = ""; 
		}
		query2.addUserWhere(" and  id = '"+ids+"'");
		try {
			DataResult result2 = query2.search();
			List<Map<String, Object>> list = result2.getResultList();
			if(list.size()>0){
				isBuilding = list.get(0).get("ISBUILDING")==null?"":list.get(0).get("ISBUILDING").toString();	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return SUCCESS;
	}
	public String nodeAllPath() {
		String codes = null;
		String ccode = null;
		if (ids == null) {
			return SUCCESS;
		} else {
			//ids = "402881f34d325ce7014d3276c5170002402881f34d325ce7014d327bcf67000f402881f34d325ce7014d327cfdca0013";
			int len = ids.length() / 32;
			for (int i = 0; i < len; i++) { // 0 1 

				if (codes == null) {
					ccode = ids.substring(i * 32, i * 32 + 32);
					codes = "'" + ccode + "'";
				} else {
					//ccode = ids.substring(i * 32, i * 32+32);
					codes = codes + "," + "'" + ids.substring(i * 32, i * 32+32) + "'";
				}
			}
		}

		System.out.println("************* "+codes);
		DataQuery query = DataQuery.getInstance("1",
				"NAME,ENTITYTYPE,ENTITY,ID,PARENTID", "alone_dictionary_type");
		query.addUserWhere(" and id in (" + codes + ")");
		//query.addSort(new DBSort("LEVELS", "asc"));
		try {
			DataResult result = query.search();
			for (Map<String, Object> node : result.getResultList()) {
				if (pathParent == null) {
					pathParent = node.get("NAME").toString();
				} else {
					pathParent = pathParent + node.get("NAME").toString();
				}
			}
			List<Map<String, Object>> nodes = result.getResultList();
			if (nodes.size() > 0) {
				node = nodes.get(0);
			}
			if (pathParent == null) {
				pathParent = "";
			}
		} catch (SQLException e) {
			node = new HashMap<String, Object>();
			node.put("CODE", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 选择楼信息时，不加载此信息
	 * @return
	 */
	public String nodeAllPath2() {
		String codes = null;
		String ccode = null;
		if (ids == null) {
			return SUCCESS;
		} else {
			int len = ids.length() / 32;
			for (int i = 0; i < len-1; i++) { // 0 1 

				if (codes == null) {
					ccode = ids.substring(i * 32, i * 32 + 32);
					codes = "'" + ccode + "'";
				} else {
					//ccode = ids.substring(i * 32, i * 32+32);
					codes = codes + "," + "'" + ids.substring(i * 32, i * 32+32) + "'";
				}
			}
		}

		DataQuery query = DataQuery.getInstance("1",
				"NAME,ENTITYTYPE,ENTITY,ID,PARENTID", "alone_dictionary_type");
		query.addUserWhere(" and id in (" + codes + ")");
		//query.addSort(new DBSort("LEVELS", "asc"));
		try {
			DataResult result = query.search();
			for (Map<String, Object> node : result.getResultList()) {
				if (pathParent == null) {
					pathParent = node.get("NAME").toString();
				} else {
					pathParent = pathParent + node.get("NAME").toString();
				}
			}
			List<Map<String, Object>> nodes = result.getResultList();
			if (nodes.size() > 0) {
				node = nodes.get(0);
			}
			if (pathParent == null) {
				pathParent = "";
			}
		} catch (SQLException e) {
			node = new HashMap<String, Object>();
			node.put("CODE", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*private final static PopuCitizenInfoManagerInter aloneIMP = (PopuCitizenInfoManagerInter) BeanFactory
			.getBean("POPU_CITIZEN_INFOProxyId");*/

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	/*public PopuCitizenInfo getEntity() {
		return entity;
	}

	public void setEntity(PopuCitizenInfo entity) {
		this.entity = entity;
	}*/

	public PageSet getPageset() {
		return pageset;
	}

	public void setPageset(PageSet pageset) {
		this.pageset = pageset;
	}

	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getNode() {
		return node;
	}

	public void setNode(Map<String, Object> node) {
		this.node = node;
	}

	public String getPathParent() {
		return pathParent;
	}

	public void setPathParent(String pathParent) {
		this.pathParent = pathParent;
	}


	public String getIsBuilding() {
		return isBuilding;
	}

	public void setIsBuilding(String isBuilding) {
		this.isBuilding = isBuilding;
	}

	private static final Logger log = Logger
			.getLogger(ActionPopuAddressQuery.class);
	private static final long serialVersionUID = 1L;

}
