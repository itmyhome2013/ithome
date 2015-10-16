package com.farm.console.adapter.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farm.console.contain.exception.UserNoExistException;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.console.server.contain.OrganizationManagerInter;
import com.farm.console.server.contain.RolegroupManagerInter;
import com.farm.console.server.contain.UserManagerInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.adapter.AuthAdapterInter;
import com.farm.web.spring.BeanFactory;

public class FarmAuthAdapterImpl implements AuthAdapterInter {

	private UserManagerInter getUserManager() {
		return (UserManagerInter) BeanFactory.getBean("ALONE_USER_DAO_PROXY");
	}

	private OrganizationManagerInter getOrganizationManger() {
		return (OrganizationManagerInter) BeanFactory
				.getBean("ALONE_Organization_DAO_PROXY");
	}

	@Override
	public void doLogin(String userid) {
		getUserManager().doLogin(userid);
	}

	@Override
	public AloneUser getEntityByLoginName(String loginname) {
		return getUserManager().getEntityByLoginName(loginname);
	}

	@Override
	public AloneOrganization getMainOrgByUserId(String userid) {
		return getOrganizationManger().getMainOrgByUserId(userid);
	}

	private RolegroupManagerInter getRolegroupManager() {
		return (RolegroupManagerInter) BeanFactory
				.getBean("ALONE_Rolegroup_DAO_PROXY");
	}

	@Override
	public List<AloneRolegroup> getRoles(String userid) {
		return getRolegroupManager().getRoles(userid);
	}

	@Override
	public AloneUser getUserById(String userId) {
		return getUserManager().getEntity(userId);
	}

	@Override
	public boolean isLegality(String loginname, String password)
			throws UserNoExistException {
		return getUserManager().isLegality(loginname, password);
	}

	@Override
	public List<Map<String, Object>> getUserMenu(String UserId, String password) {
		DataQuery query = DataQuery
				.getInstance(
						"1",
						"G.TREECODE AS TREECODE,G.TYPE AS TYPE,H.URL AS URL,G.ID AS ID,G.IMG AS IMG,G.SORT AS SORT,G.PARENTID as PARENTID,G.NAME AS NAME",
						"alone_user a left join ALONE_USER_ROLEGROUP d on a.id = d.userid left join alone_rolegroup e on e.id = d.rolegroup left join alone_menu_rolegroup f on f.rolegroupid = e.id left join alone_menu g on g.id = f.menuid left join alone_action h on h.id = g.action ");
		query.setPagesize(1000);
		query.addRule(new DBRule("G.state", "1", "="));
		query.addRule(new DBRule("a.id", UserId, "="));
		query.SetDISTINCT(true);
		DataResult result = null;
		try {
			result = query.search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result.getResultList();
	}

	@Override
	public Map<String, String> getUserAction(String UserId, String password) {
		Map<String, String> map = new HashMap<String, String>();
		DataQuery query = DataQuery
				.getInstance(
						"1",
						"h.id as id,h.url as url",
						"alone_user a left join ALONE_USER_ROLEGROUP d on a.id = d.userid left join alone_rolegroup e on e.id = d.rolegroup left join alone_menu_rolegroup f on f.rolegroupid = e.id left join alone_menu g on g.id = f.menuid left join alone_action h on h.id = g.action ");
		query.setPagesize(1000);
		query.addUserWhere(" and h.id is not null ");
		query.addRule(new DBRule("h.state", "1", "="));
		query.addRule(new DBRule("a.id", UserId, "="));
		query.SetDISTINCT(true);
		DataResult result = null;
		try {
			result = query.search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Map<String, Object> node : result.getResultList()) {
			String url = node.get("URL").toString();
			String id = node.get("ID").toString();
			map.put(url, id);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getAllOrg() {
		DataQuery query = DataQuery.getInstance("1",
				"ID, TREECODE, COMMENTS, NAME,  PARENTID, SORT, TYPE",
				"alone_organization");
		List<Map<String, Object>> list = null;
		try {
			list = query.search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getUserByOrg(String orgId) {
		DataQuery query = DataQuery
				.getInstance("1", "a.id as ID,a.name as NAME",
						"alone_user a left join alone_organization_user  b on a.id=b.userid ");
		List<Map<String, Object>> list = null;
		try {
			list = query.addRule(new DBRule("b.organizationid", orgId, "="))
					.search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean editPassword(String id, String oldPassword,
			String newpassword) {
		return getUserManager().editPassword(id, oldPassword, newpassword);
	}
}
