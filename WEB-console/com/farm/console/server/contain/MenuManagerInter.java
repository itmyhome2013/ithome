package com.farm.console.server.contain;


import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.prisist.domain.AloneMenu;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;


public interface MenuManagerInter {
	/**
	 * 父节点为根目录是存入的ID
	 */
	public static String ROOT_PAREANT="NONE";
	/**菜单类型
	 * @author wangdong
	 *
	 */
	public enum MENU_TYPE{
		STRUCT("1"),MENU("0"),POP("3");
		private String value;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		MENU_TYPE(String value){
			this.value=value;
		}
	}
	/**
	 * 通过一个键获得一个应用配置字符串值
	 */
	public String getConfigValue(String key);

	/**
	 * 初始化应用配置集合
	 */
	public boolean initConfig();

	/**
	 * 强制刷新配置集合
	 */
	public boolean refreshConfigMap();

	public void deleteEntity(String entity);

	public void editEntity(AloneMenu entity);

	public AloneMenu getEntity(String id);

	/**插入菜单节点
	 * @param entity 菜单实体
	 * @param currentUser 当前用户
	 * @return
	 */
	public AloneMenu insertEntity(AloneMenu entity, AloneUser currentUser,AloneAction
			action);

	public AloneMenu editEntity(AloneMenu entity, AloneUser currentUser);

	public void deleteEntity(String name, AloneUser currentUser);

	/**
	 * 初始化标准树
	 * 
	 * @param query
	 * @param tableName
	 * @param rowName
	 * @return
	 */
	public DataResult initTree(DataQuery query, DBSort sort, String tableName,
			String rowName);

	/**
	 * 加载树的子节点
	 * 
	 * @param query
	 * @param aloneContext
	 * @param tableName
	 * @param rowName
	 * @return
	 */
	public DataResult addTreeNode(DataQuery query, DBSort sort,
			String aloneContext, String tableName, String rowName);

	/**
	 * 根据菜单id查询菜单的上级节点
	 * @param menuId
	 * @return
	 */
	public String getMenuParentNamePath(String menuId);

	/**复制一个多个菜单(不包含子菜单)到目标菜单下
	 * @param ids  原菜单id
	 * @param oids 目标菜单id
	 */
	public void copyMenuTo(String ids, String oids,AloneUser cuser);
}
