package com.farm.console.web.action;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.prisist.domain.AloneMenu;
import com.farm.console.server.contain.ActionManagerInter;
import com.farm.console.server.contain.MenuManagerInter;
import com.farm.core.file.Files;
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
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.spring.BeanFactory;

/**
 * 权限资源
 * 
 * @author MAC_alone
 * 
 */
public class ActionPopMenu extends WebSupport {
	private DataResult result;// 结果集合 result.resultList
	private DataQuery query;// 条件查询
	private AloneMenu entity;// 实体封装
	private AloneMenu fatherEntity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private DataResult treeResult;// 树节点对象
	private String aloneContext;// 树节点id
	private AloneAction action;// 菜单对应权限
	private List<String> menuIcons;// 菜单可选图标路径
	private String urlPath;// 相对路径
	private String oids;
	private String id;// 异步树传入的id
	private List<EasyUiTreeNode> treeNodes;// 异步树的封装

	/**
	 * 复制菜单
	 * 
	 * @return
	 */
	public String copySubmit() {
		try {
			menuIMP.copyMenuTo(ids, oids, getCurrentUser());
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.OTHER, CommitType.FALSE, e
					.toString()
					+ e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery.init(query, "alone_menu",
					"ID,NAME,TYPE,SORT,STATE,IMG,COMMENTS");
			query.addDefaultSort(new DBSort("utime", "DESC"));
			result = query.search();
			Map<String, String> dictionary = new HashMap<String, String>();
			dictionary.put("1", "构造菜单");
			dictionary.put("0", "实体菜单");
			dictionary.put("3", "功能权限");
			result.runDictionary(dictionary, "TYPE");
			result.runDictionary("0:禁用,1:可用", "STATE");
			result.LoadListArray();
		} catch (Exception e) {
			DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 选择图标
	 * 
	 * @return
	 */
	public String chooseIcon() {
		try {
			// File dir = new
			// File(Files.initSeparator(AloneBaseManager.instance()
			// .getParameterFace().getConfigValue("MENU_ICON_PATH")));
			File dir = new File(Files.initSeparator(FarmManager.instance()
					.getRealPath()
					+ "/WEB-FACE/img/menu"));
			urlPath = dir.getPath();
			if (urlPath.contains(FarmManager.instance().getRealPath())) {
				urlPath = urlPath.replace(FarmManager.instance()
						.getRealPath(), "");
			}
			urlPath = urlPath.trim().substring(1) + File.separator;
			urlPath = Files.initSeparatorRight(urlPath);
			menuIcons = new ArrayList<String>();
			for (String file : dir.list()) {
				if (file.toUpperCase().contains("JPG")
						|| file.toUpperCase().contains("PNG")
						|| file.toUpperCase().contains("GIF")) {
					menuIcons.add(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询树节点
	 * 
	 * @return
	 */
	public String queryTree() {
		try {
			query = DataQuery.init(query, "alone_menu",
					"id,name as data,'close' as state");
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
	 * 提交修改数据
	 * 
	 * @return
	 */
	public String editSubmit() {
		try {
			initActionEdit();
			entity = menuIMP.editEntity(entity, getCurrentUser());
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

	private void initActionEdit() {
		if (entity.getAction() == null
				|| entity.getAction().trim().length() <= 0) {
			entity.setAction(null);
		}
		if ((!entity.getType().equals("1"))) {
			// 判断已有的URL和提交上来的URL是否相等不等：就修改menu的URL
			String url = action.getUrl();
			if ((entity.getAction() == null)
					|| (!actionIMP.getEntity(entity.getAction()).getUrl()
							.equals(url))) {
				// 提交上来的action和URL是否一致不一致就新建立URL并赋给menu,一致就将anction赋给menu
				AloneAction action_temp = actionIMP.getEntity(action.getId());
				if ((action_temp != null) && url.equals(action_temp.getUrl())) {
					entity.setAction(action_temp.getId());
				} else {
					String actionId = actionIMP.insertEntity(
							new AloneAction(url, entity.getName(), null, null,
									null, null, "1", "1"), getCurrentUser())
							.getId();
					entity.setAction(actionId);
				}
			}
		}

	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	public String addSubmit() {
		try {
			if (fatherEntity != null && fatherEntity.getId() != null) {
				entity.setParentid(fatherEntity.getId());
			}
			entity = menuIMP.insertEntity(entity, getCurrentUser(), action);
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e.toString()
					+ e.getMessage());
			log.error(result.getMessage());
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
				menuIMP.deleteEntity(id);
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
	 * 加载树节点
	 * 
	 * @return
	 */
	public String loadTreeNode() {
		treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(EasyUiTreeNode
				.queryTreeNodeOne(id, "SORT", "alone_menu", "ID", "PARENTID",
						"NAME","IMG").getResultList(), EasyUiTreeNode
				.queryTreeNodeTow(id, "SORT", "alone_menu", "ID", "PARENTID",
						"NAME","IMG").getResultList(), "PARENTID", "ID", "NAME","IMG");
		return SUCCESS;
	}

	/**
	 * 一次查出所有菜单
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String loadAllTreeNode() throws SQLException {
		query = DataQuery.init(query, "alone_menu",
				"ID,NAME,TYPE,SORT,PARENTID,STATE,IMG,COMMENTS");
		query.setPagesize(100);
		query.addRule(new DBRule("STATE", "1", "="));
		query.addUserWhere(" ORDER BY LENGTH(TREECODE) ASC,SORT ASC");
		result = query.search();
		treeNodes = EasyUiTreeNode.formatAjaxTree(result.getResultList(),
				"PARENTID", "ID", "NAME");
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
				action = new AloneAction();
				if (aloneContext != null && aloneContext.trim().length() >= 0) {
					fatherEntity = menuIMP.getEntity(aloneContext);
				} else {
					fatherEntity = new AloneMenu();
					fatherEntity.setName("无");
				}
				return SUCCESS;
			}
			case (0): {// 展示
				entity = menuIMP.getEntity(ids);
				if (!entity.getType().equals("1")) {
					action = actionIMP.getEntity(entity.getAction());
				}
				break;
			}
			case (2): {// 修改
				entity = menuIMP.getEntity(ids);
				if (!entity.getType().equals("1")) {
					action = actionIMP.getEntity(entity.getAction());
				}
				break;
			}
			default:
				break;
			}
			if (aloneContext != null && aloneContext.trim().length() >= 0) {
				fatherEntity = menuIMP.getEntity(aloneContext);
			} else {
				fatherEntity = menuIMP.getEntity(entity.getParentid());
			}
			if (action == null) {
				action = new AloneAction();
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

	private final static MenuManagerInter menuIMP = (MenuManagerInter) BeanFactory
			.getBean("ALONE_Menu_DAO_PROXY");
	private final static ActionManagerInter actionIMP = (ActionManagerInter) BeanFactory
			.getBean("ALONE_Action_DAO_PROXY");

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

	public AloneMenu getEntity() {
		return entity;
	}

	public void setEntity(AloneMenu entity) {
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

	public DataResult getTreeResult() {
		return treeResult;
	}

	public void setTreeResult(DataResult treeResult) {
		this.treeResult = treeResult;
	}

	public String getAloneContext() {
		return aloneContext;
	}

	public void setAloneContext(String aloneContext) {
		this.aloneContext = aloneContext;
	}

	public AloneMenu getFatherEntity() {
		return fatherEntity;
	}

	public void setFatherEntity(AloneMenu fatherEntity) {
		this.fatherEntity = fatherEntity;
	}

	public AloneAction getAction() {
		return action;
	}

	public void setAction(AloneAction action) {
		this.action = action;
	}

	public List<String> getMenuIcons() {
		return menuIcons;
	}

	public void setMenuIcons(List<String> menuIcons) {
		this.menuIcons = menuIcons;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getOids() {
		return oids;
	}

	public void setOids(String oids) {
		this.oids = oids;
	}

	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private static final Logger log = Logger.getLogger(ActionPopMenu.class);
	private static final long serialVersionUID = 1L;
}
