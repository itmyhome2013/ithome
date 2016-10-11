package com.farm.console.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
import com.farm.console.contain.exception.UserLoginNameIsExistException;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.console.server.contain.OrganizationManagerInter;
import com.farm.console.server.contain.UserManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.core.web.ParameterUtils;
import com.farm.util.online.OnlineUserOpImpl;
import com.farm.util.online.OnlineUserOpInter;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

/**
 * 用户信息
 * 
 * @author wangdong-2012/11/07
 * 
 */
public class ActionAloneUserQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private AloneUser entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private AloneOrganization orgEntity;// 用户组织机构
	private String passwordl;// 原始密码
	private String passwordn1;// 新密码
	private String passwordn2;// 新密码
	private String param;// 密码修改是否成功
	private String orgId;// 组织机构ID。组织机构选择人员时传的参数_zhanghc_2012.9.4
	private String userId;// 人员ID。组织机构选择人员时传的参数_zhanghc_2012.9.4
	private String fileId;// 附件ID。用于人员关联照片
	private Map<String, Object> photoInfoMap;// 验证该人员是否有照片和照片路径。用于实体页面显示照片
	private List<Map<String, String>> positionList;// 岗位集合
	private List<Map<String, String>> workList;// 工种集合
	private Map<String, Object> isMainOrgInfoMap;// 是否是主要组织机构信息_zhanghc_2012.9.6
	private List<AloneOrganization> orgList;

	/**
	 * 查询用户信息结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			query = DataQuery
					.init(
							query,
							"ALONE_USER a  left join ALONE_ORGANIZATION_USER e on e.USERID=a.id and e.type='1' left join ALONE_ORGANIZATION f on f.id=e.ORGANIZATIONID left join (SELECT b.id as userid, wm_concat(c.name) as rolename FROM alone_user_rolegroup a left join alone_user b  on a.userid = b.id left join alone_rolegroup c on a.rolegroup = c.id  group by b.id ) r on r.userid = a.id ",
							"a.id as id,a.name,a.LOGINTIME,a.loginname,a.type,f.name as orgname,r.rolename as ROLENAME,a.state,a.utime");
			DBRule treeRule = query.getAndRemoveRule("PARENTID");
			if (treeRule == null || treeRule.getValue().equals("NONE")) {
				// 清除PARENTID=NONE的条件这里为了中和一个页面BUG:王东20121203
				if (treeRule != null && treeRule.getValue().equals("NONE")) {
					int size = query.getQueryRule().size();
					for (int i = 0; i <= size; i++) {
						query.getAndRemoveRule("PARENTID");
					}
				}
			} else {

				query.addRule(treeRule);
			}
			query.SetDISTINCT(true);
			query.addRule(new DBRule("a.state", "2", "!="));
			query.addSort(new DBSort("a.utime", "desc"));
			result = query.search();
			Map<String, String> dicMap = new HashMap<String, String>();
			dicMap.put("1", "可用");
			dicMap.put("0", "禁用");
			result.runDictionary(dicMap, "A_STATE");
			result.runDictionary("1:系统管理员,0:普通", "A_TYPE");
			result.runDictionary(FarmManager.instance()
					.findDicTitleForIndex("USER_EDUCATION"), "B_EDUCATION");
			result.LoadListArray("a.id,a.name,f.name as orgname,a.state");
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 查找全部角色并标志用户拥有状态
	 * 
	 * @return
	 */
	public String ruleGroupQueryallForUser() {
		try {
			query = DataQuery
					.init(
							query,
							"ALONE_ROLEGROUP a left join ALONE_USER_ROLEGROUP b on a.id=b.rolegroup and b.userid='"
									+ userId + "'",
							"a.id as id,a.name as aname,a.comments as acomments,b.id as bid");
			query.addRule(new DBRule("a.state", "1", "="));
			result = query.search();
			for (Map<String, Object> node : result.getResultList()) {
				if (node.get("BID") != null) {
					node.put("BID", "1");
				} else {
					node.put("BID", "0");
				}
			}
		} catch (Exception e) {
			DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 选择用户时的查询结果集合
	 * 
	 * @return
	 */
	public String adminChooseall() {
		try {
			query = DataQuery.init(query, "Alone_User a ",
					"a.id,a.name,a.state");
			query.addRule(new DBRule("a.type", "0", "="));
			query.addRule(new DBRule("a.state", "1", "="));
			result = query.search();
			Map<String, String> dicMap = new HashMap<String, String>();
			dicMap.put("1", "可用");
			dicMap.put("0", "禁用");
			result.runDictionary(dicMap, "A_STATE");
			for (Map<String, Object> node : result.getResultList()) {
				String userId = (String) node.get("A_ID");
				DataQuery dq = DataQuery
						.getInstance(
								"1",
								"b.id,b.name",
								"ALONE_ORGANIZATION_USER a left join ALONE_ORGANIZATION b on b.id=a.ORGANIZATIONID");
				dq.addRule(new DBRule("a.USERID", userId, "="));
				DataResult dr = dq.search();
				StringBuffer orgTitle = new StringBuffer();
				for (Map<String, Object> orgnode : dr.getResultList()) {
					orgTitle.append(orgnode.get("B_NAME"));
				}
				node.put("ORG", orgTitle.toString());
			}
			result.LoadListArray("a.id,a.name,org,a.state");
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 管理员查询结果集合
	 * 
	 * @return
	 */
	public String adminQueryall() {
		try {
			query = DataQuery
					.init(
							query,
							"Alone_User a left join alone_organization_user b on a.id=b.userid and b.type='1' left join ALONE_ORGANIZATION c on c.id=b.ORGANIZATIONID",
							"a.id,a.name as aname,a.loginname,c.name as cname,a.state");
			query.addRule(new DBRule("a.TYPE", "1", "="));
			query.addRule(new DBRule("a.state", "1", "="));
			result = query.search();
			Map<String, String> dicMap = new HashMap<String, String>();
			dicMap.put("1", "可用");
			dicMap.put("0", "禁用");
			result.runDictionary(dicMap, "A_STATE");
			result.LoadListArray("a.id,aname,a.loginname,cname,a.state");
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 通过组织机构选择人员。
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-4 上午10:54:22
	 */
	public String orgChooseall() {
		try {
			query = DataQuery
					.init(
							query,//
							"(select A.ID    AS ID,"
									+ "       A.NAME  AS USERNAME,"
									+ "       D.NAME  AS POSTNAME,"
									+ "       C.NAME  AS WORKNAME,"
									+ "       A.STATE AS STATE"
									+ "  FROM ALONE_USER a /* 用户 */"
									+ "  left join CCS_USEREX b on a.id = b.userid /* 用户扩展 */"
									+ "  left join CCS_WORKTYPE c on b.worktype = c.id /* 工种岗位 */"
									+ "  left join CCS_WORKTYPE d on b.position = d.id /* 工种岗位 */"
									+ " where a.id not in (select x.userid"
									+ "                      	from ALONE_ORGANIZATION_USER x /* 人员和组织中间表 */"
									+ "                     	where x.organizationid = '"
									+ orgId + "')"
									+ "   	and A.STATE != '2') a ",
							"a.id as id, a.username as username, a.postname as postname, a.workname as workname, a.state as state");

			query.setPagesize(10);
			result = query.search();

			// 处理结果
			for (Map<String, Object> map : result.getResultList()) {
				// 添加所属组织机构
				List<AloneOrganization> orgList = aloneIMP
						.findUnMianOrgList(map.get("ID") + "");
				if (orgList.size() > 0) {
					StringBuilder sb = new StringBuilder();
					for (AloneOrganization org : orgList) {
						sb.append(org.getName() + "、");
					}
					map.put("ALLORG", sb.substring(0, sb.length() - 1));
				} else {
					map.put("ALLORG", "");
				}
			}

			// 状态转换
			result.runDictionary(FarmManager.instance()
					.findDicTitleForIndex("STATE"), "STATE");

			result.LoadListArray("id,username,postname,workname,allorg,state");
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 通过组织机构查看人员。
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-5 上午08:59:40
	 */
	public String orgQueryall() {
		try {
			query = DataQuery
					.init(
							query,//
							"ALONE_ORGANIZATION_USER a  "// a加a.orgid=?过滤出的是当前组织机构的所有不会重复的人
									+ "left join ALONE_USER b on a.userid = b.id  "// 查找人名
									+ "left join ALONE_ORGANIZATION_USER c on c.userid=b.id and c.type='1'  "// a和b合为一张表，左外c并加type=1，找出是该人员的主要组织机构或空
									+ "left join ALONE_ORGANIZATION d on d.id=c.organizationid  "// 查找组织机构名称
							,
							"b.id as id, b.name as username, d.name as mainorgname, b.state as state");

			query.addRule(new DBRule("a.organizationid", orgId, "="));
			query.addRule(new DBRule("b.state", "1", "="));
			query.setPagesize(10);
			result = query.search();
			// 状态转换
			result.runDictionary(FarmManager.instance()
					.findDicTitleForIndex("STATE"), "STATE");
			result.LoadListArray();
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 用户申请账户
	 * 
	 * @return
	 */
	public String register() {
		try {
			aloneIMP.register(entity, orgEntity, "0");
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE, "密码修改成功!");
		} catch (UserLoginNameIsExistException e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
			pageset.setMessage("该登陆名已被使用！");
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 查找是否是主要组织机构
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-6 上午10:05:23
	 */
	public String findIsMainOrg() {
		try {
			isMainOrgInfoMap = aloneIMP.findIsMainOrg(userId, orgId);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 为人员设置主要组织机构
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-6 上午10:07:02
	 */
	public String SubmitSetMainOrg() {
		try {
			aloneIMP.addOrg(userId, orgId, true);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, "关联人员失败："
					+ e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 为人员批量设置主要组织机构
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-6 上午10:07:02
	 */
	public String SubmitAllSetOrg() {
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				aloneIMP.addOrg(id, orgId, true);
			}
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, "关联人员失败："
					+ e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 为人员设置非主要组织机构
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-7 下午03:00:20
	 */
	public String SubmitSetUnMainOrg() {
		try {
			aloneIMP.addOrg(userId, orgId, false);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, "关联人员失败："
					+ e.getMessage());
			log.error(pageset.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 删除人员和组织机构的关系
	 * 
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-6 下午03:16:40
	 */
	public String deleteOrg() {
		try {
			aloneIMP.deleteOrg(userId, orgId);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 设置系统管理员
	 * 
	 * @return
	 */
	public String SubmitSetAdmin() {
		try {
			aloneIMP.updateToAdminFromUser(ids, getCurrentUser());
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE, "设置管理员");
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 删除系统管理员
	 * 
	 * @return
	 */
	public String SubmitRemoveAdmin() {
		try {
			aloneIMP.updateToRemoveAdmin(ids, getCurrentUser());
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE, "删除管理员");
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String updataPassWordCommit() {
		try {
			if (!aloneIMP.editPassword(getCurrentUser().getId(), passwordl,
					passwordn1)) {
				param = "fail";
				throw new RuntimeException("密码修改失败！");
			} else {
				param = "success";
			}
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE, "密码修改成功!");
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 查看在线用户
	 * 
	 * @return
	 */
	public String findOnlineUser() {
		OnlineUserOpInter ouop = OnlineUserOpImpl.getInstance();
		result = ouop.findOnlineUser();
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
			// 插入用户组织机构
			if (orgEntity != null && orgEntity.getId() != null
					&& orgEntity.getId().trim().length() > 0) {
				aloneIMP.addOrg(entity.getId(), orgEntity.getId(), true);
			}
			aloneIMP.editEntity(entity, getCurrentUser());
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
			// 插入用户
			entity = aloneIMP.insertUser(entity, getCurrentUser());
			// 插入用户组织机构
			if (orgEntity != null && orgEntity.getId() != null
					&& orgEntity.getId().trim().length() > 0) {
				aloneIMP.addOrg(entity.getId(), orgEntity.getId(), true);
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
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
				aloneIMP.deleteLogicEntity(id, getCurrentUser());
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 初始化用户密码
	 * 
	 * @return
	 */
	public String resetPassWord() {
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				aloneIMP.initPassword(id);
			}
			pageset = new PageSet(PageType.OTHER, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
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
				break;
			}
			case (0): {// 展示
				entity = aloneIMP.getEntity(ids);
				orgEntity = orgIMP.getMainOrgByUserId(ids);
				orgList = orgIMP.getNotMainOrgByUserId(ids);
				break;
			}
			case (2): {// 修改
				entity = aloneIMP.getEntity(ids);
				orgEntity = orgIMP.getMainOrgByUserId(ids);
				orgList = orgIMP.getNotMainOrgByUserId(ids);
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	private final static UserManagerInter aloneIMP = (UserManagerInter) BeanFactory
			.getBean("ALONE_USER_DAO_PROXY");
	private final static OrganizationManagerInter orgIMP = (OrganizationManagerInter) BeanFactory
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

	public AloneUser getEntity() {
		return entity;
	}

	public void setEntity(AloneUser entity) {
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

	public AloneOrganization getOrgEntity() {
		return orgEntity;
	}

	public void setOrgEntity(AloneOrganization orgEntity) {
		this.orgEntity = orgEntity;
	}

	public String getPasswordl() {
		return passwordl;
	}

	public void setPasswordl(String passwordl) {
		this.passwordl = passwordl;
	}

	public String getPasswordn1() {
		return passwordn1;
	}

	public void setPasswordn1(String passwordn1) {
		this.passwordn1 = passwordn1;
	}

	public String getPasswordn2() {
		return passwordn2;
	}

	public void setPasswordn2(String passwordn2) {
		this.passwordn2 = passwordn2;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<Map<String, String>> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<Map<String, String>> positionList) {
		this.positionList = positionList;
	}

	public List<Map<String, String>> getWorkList() {
		return workList;
	}

	public void setWorkList(List<Map<String, String>> workList) {
		this.workList = workList;
	}

	public List<AloneOrganization> getOrgList() {
		return orgList;
	}

	public Map<String, Object> getIsMainOrgInfoMap() {
		return isMainOrgInfoMap;
	}

	public void setIsMainOrgInfoMap(Map<String, Object> isMainOrgInfoMap) {
		this.isMainOrgInfoMap = isMainOrgInfoMap;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setOrgList(List<AloneOrganization> orgList) {
		this.orgList = orgList;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Map<String, Object> getPhotoInfoMap() {
		return photoInfoMap;
	}

	public void setPhotoInfoMap(Map<String, Object> photoInfoMap) {
		this.photoInfoMap = photoInfoMap;
	}

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AloneOrganization.class);
}
