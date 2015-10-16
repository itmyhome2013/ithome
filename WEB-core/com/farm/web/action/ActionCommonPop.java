package com.farm.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.dispatcher.Dispatcher;

import com.farm.web.easyui.EasyUiTreeNode;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;

/**
 * 内容提示
 * 
 * @author MAC_alone
 * 
 */
public class ActionCommonPop extends WebSupport {
	private String index;
	private String value;
	private String id;
	private List<EasyUiTreeNode> treeNodes;// 异步树的封装

	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public String findAjaxUrlTree() {
		if (id == null) {
			treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(getAllUrlNodes(1),
					getAllUrlNodes(2), "PID", "ID", "ID","IMG");
		} else {
			treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(getAllUrlNodes(id),
					new ArrayList<Map<String, Object>>(), "PID", "ID", "ID","IMG");
		}
		return SUCCESS;
	}

	private List<Map<String, Object>> getAllUrlNodes(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Configuration configuration = Dispatcher.getInstance()
				.getConfigurationManager().getConfiguration();
		Set<String> packageConfigNames = configuration.getPackageConfigNames();
		for (String key : packageConfigNames) {
			PackageConfig packageConfig = configuration.getPackageConfigs()
					.get(key);
			if (key.equals(id)) {
				for (String actionKey : packageConfig.getActionConfigs()
						.keySet()) {
					Map<String, Object> node = new HashMap<String, Object>();
					node.put("PID", key);
					node.put("ID", actionKey);
					list.add(node);
				}
			}
		}
		return list;
	}

	/**
	 * 查询所有strust中配置的URL关键字
	 */
	private List<Map<String, Object>> getAllUrlNodes(int depth) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Configuration configuration = Dispatcher.getInstance()
				.getConfigurationManager().getConfiguration();
		Set<String> packageConfigNames = configuration.getPackageConfigNames();
		if (depth == 1) {
			for (String key : packageConfigNames) {
				PackageConfig packageConfig = configuration.getPackageConfigs()
						.get(key);
				if (packageConfig.getActionConfigs().size() <= 0) {
					continue;
				}
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("PID", "NONE");
				node.put("ID", key);
				list.add(node);
			}
		}
		if (depth == 2) {
			for (String key : packageConfigNames) {
				PackageConfig packageConfig = configuration.getPackageConfigs()
						.get(key);
				if (packageConfig.getParents().size() <= 0) {
					continue;
				}
				for (String actionKey : packageConfig.getActionConfigs()
						.keySet()) {
					Map<String, Object> node = new HashMap<String, Object>();
					node.put("PID", key);
					node.put("ID", actionKey);
					list.add(node);
				}
			}
		}
		return list;
	}

	// ----------------------------------------------------------------------------------

	public String getIndex() {
		return index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private static final long serialVersionUID = 1L;
}
