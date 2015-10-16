package com.farm.doc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.farm.console.FarmManager;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import org.macpro.ca.procase.MayCase;

import com.farm.doc.FarmDocumentFace;
import com.farm.doc.dao.FarmDocDaoInter;
import com.farm.doc.dao.FarmDocgroupDaoInter;
import com.farm.doc.dao.FarmDocgroupUserDaoInter;
import com.farm.doc.domain.FarmDoc;
import com.farm.doc.domain.FarmDocgroup;
import com.farm.doc.domain.FarmDocgroupUser;
import com.farm.doc.exception.NoGroupAuthForLicenceException;
import com.farm.doc.server.FarmDocgroupManagerInter;
import com.farm.web.constant.FarmConstant;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;

/**
 * 工作小组
 * 
 * @author MAC_wd
 */
public class FarmDocgroupManagerImpl implements FarmDocgroupManagerInter {
	private FarmDocgroupDaoInter farmDocgroupDao;
	private FarmDocgroupUserDaoInter farmDocgroupUserDao;
	private FarmDocmessageManagerInter farmDocmessageServer;
	private FarmDocDaoInter farmDocDao;
	private FarmDocManagerInter farmdocServer;
	private static final Logger log = Logger
			.getLogger(FarmDocgroupManagerImpl.class);

	public FarmDocgroup editFarmDocgroupEntity(FarmDocgroup entity,
			AloneUser user) {
		FarmDocgroup entity2 = farmDocgroupDao.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPstate(entity.getPstate());
		entity2.setPcontent(entity.getPcontent());
		entity2.setGroupname(entity.getGroupname());
		entity2.setGroupnote(entity.getGroupnote());
		entity2.setGrouptag(entity.getGrouptag());
		entity2.setGroupimg(entity.getGroupimg());
		entity2.setJoincheck(entity.getJoincheck());
		farmDocgroupDao.editEntity(entity2);
		return entity2;
	}

	public void deleteFarmDocgroupEntity(String groupId, AloneUser user) {
		List<DBRule> list = new ArrayList<DBRule>();
		list.add(new DBRule("GROUPID", groupId, "="));
		farmDocgroupUserDao.deleteEntitys(list);
		{
			List<DBRule> doclist = new ArrayList<DBRule>();
			doclist.add(new DBRule("DOCGROUPID", groupId, "="));
			// 释放所有文档
			for (FarmDoc node : farmDocDao.selectEntitys(doclist)) {
				farmdocServer.flyDoc(node);
			}
		}

		farmDocgroupDao.deleteEntity(farmDocgroupDao.getEntity(groupId));
	}

	public FarmDocgroup getFarmDocgroupEntity(String id) {
		if (id == null) {
			return null;
		}
		FarmDocgroup group = farmDocgroupDao.getEntity(id);
		if (group.getGroupimg() != null
				&& group.getGroupimg().trim().length() <= 0) {
			group.setGroupimg(null);
		}
		if (group.getGroupimg() != null) {
			group.setImgurl(FarmDocumentFace.getDocimpl().getFileURL(
					group.getGroupimg()));
		}
		if (group.getGrouptag() != null) {
			String tags = group.getGrouptag();
			String[] tags1 = tags.trim().replaceAll("，", ",").replaceAll("、",
					",").split(",");
			group.setTags(Arrays.asList(tags1));
		}
		return group;
	}

	@Override
	public DataQuery createFarmDocgroupQueryJoinUser(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"farm_docgroup a left join FARM_DOCGROUP_USER b on b.GROUPID=a.id",
						"a.id as ID,b.SHOWHOME as SHOWHOME, a.PSTATE as PSTATE,a.PCONTENT as PCONTENT,GROUPNAME,GROUPNOTE,GROUPTAG,GROUPIMG,JOINCHECK");
		return dbQuery;
	}

	@Override
	public DataQuery createFarmDocgroupQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"(select ID,(SELECT COUNT(a.id) FROM farm_doc a WHERE a.DOCGROUPID=farm_docgroup.ID ) as DOCNUM,PSTATE,PCONTENT,USERNUM,CUSERNAME,CTIME,GROUPNAME,GROUPNOTE,GROUPTAG,GROUPIMG,JOINCHECK from farm_docgroup) b ",
						"ID,DOCNUM,PSTATE,PCONTENT,USERNUM,CUSERNAME,CTIME,GROUPNAME,GROUPNOTE,GROUPTAG,GROUPIMG,JOINCHECK");
		return dbQuery;
	}

	public FarmDocgroupUser editFarmDocgroupUserEntity(FarmDocgroupUser entity,
			AloneUser user) {
		FarmDocgroupUser entity2 = farmDocgroupUserDao
				.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPstate(entity.getPstate());
		entity2.setPcontent(entity.getPcontent());
		entity2.setGroupid(entity.getGroupid());
		entity2.setUserid(entity.getUserid());
		entity2.setLeadis(entity.getLeadis());
		entity2.setEditis(entity.getEditis());
		entity2.setShowhome(entity.getShowhome());
		entity2.setShowsort(entity.getShowsort());
		farmDocgroupUserDao.editEntity(entity2);
		return entity2;
	}

	public void deleteFarmDocgroupUserEntity(String entity, AloneUser user) {
		farmDocgroupUserDao.deleteEntity(farmDocgroupUserDao.getEntity(entity));
	}

	public FarmDocgroupUser getFarmDocgroupUserEntity(String id) {
		if (id == null) {
			return null;
		}
		return farmDocgroupUserDao.getEntity(id);
	}

	@Override
	public DataQuery createFarmDocgroupUserSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"farm_docgroup_user a left join alone_user b on a.userid=b.id left join farm_docgroup c on a.groupid=c.id",
						"a.id as id,a.PSTATE as PSTATE,GROUPID,b.name as username,LEADIS,EDITIS,SHOWHOME,SHOWSORT,c.groupname as groupname");
		return dbQuery;
	}

	// ----------------------------------------------------------------------------------
	public FarmDocgroupDaoInter getfarmDocgroupDao() {
		return farmDocgroupDao;
	}

	public void setfarmDocgroupDao(FarmDocgroupDaoInter dao) {
		this.farmDocgroupDao = dao;
	}

	public FarmDocgroupUserDaoInter getFarmDocgroupUserDao() {
		return farmDocgroupUserDao;
	}

	public FarmDocDaoInter getFarmDocDao() {
		return farmDocDao;
	}

	public FarmDocManagerInter getFarmdocServer() {
		return farmdocServer;
	}

	public void setFarmdocServer(FarmDocManagerInter farmdocServer) {
		this.farmdocServer = farmdocServer;
	}

	public void setFarmDocDao(FarmDocDaoInter farmDocDao) {
		this.farmDocDao = farmDocDao;
	}

	public FarmDocmessageManagerInter getFarmDocmessageServer() {
		return farmDocmessageServer;
	}

	public void setFarmDocmessageServer(
			FarmDocmessageManagerInter farmDocmessageServer) {
		this.farmDocmessageServer = farmDocmessageServer;
	}

	public void setFarmDocgroupUserDao(
			FarmDocgroupUserDaoInter farmDocgroupUserDao) {
		this.farmDocgroupUserDao = farmDocgroupUserDao;
	}

	@Override
	public FarmDocgroup creatDocGroup(String groupname, String grouptag,
			String groupimg, boolean joincheck, String groupnote,
			AloneUser currentUser) throws NoGroupAuthForLicenceException {
	//	String licence = FarmConstant.LICENCE;
	//	if (!MayCase.isCase(licence == null ? "NONE" : licence)) {
	//		FarmManager.instance().initLicence(currentUser);
	//		FarmConstant.LICENCE_FLAG="0";
	//		throw new NoGroupAuthForLicenceException();
	//	}
		FarmDocgroup entity = new FarmDocgroup();
		entity.setCuser(currentUser.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(currentUser.getName());
		entity.setEuser(currentUser.getId());
		entity.setEusername(currentUser.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		entity.setGroupimg(groupimg);
		if (groupimg.trim().length() <= 0) {
			throw new RuntimeException("小组头像不能为空");
		}
		farmdocServer.submitFile(groupimg);
		entity.setGroupname(groupname);
		entity.setGroupnote(groupnote);
		entity.setUsernum(0);
		entity.setGrouptag(grouptag);
		entity.setJoincheck(joincheck ? "1" : "0");
		entity = farmDocgroupDao.insertEntity(entity);
		// 将小组付给当前用户并设置为管理员
		FarmDocgroupUser goupuser = new FarmDocgroupUser();
		goupuser.setCuser(currentUser.getId());
		goupuser.setCtime(TimeTool.getTimeDate14());
		goupuser.setCusername(currentUser.getName());
		goupuser.setEuser(currentUser.getId());
		goupuser.setEusername(currentUser.getName());
		goupuser.setEtime(TimeTool.getTimeDate14());
		goupuser.setPstate("1");
		goupuser.setGroupid(entity.getId());
		goupuser.setUserid(currentUser.getId());
		goupuser.setLeadis("1");
		goupuser.setEditis("1");
		goupuser.setShowhome("1");
		goupuser.setShowsort(10);
		farmDocgroupUserDao.insertEntity(goupuser);
		return entity;
	}

	@Override
	public FarmDocgroup editDocGroup(String id, String groupname,
			String grouptag, String groupimg, boolean joincheck,
			String groupnote, AloneUser currentUser) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("GROUPID", id, "="));
		rules.add(new DBRule("LEADIS", "1", "="));
		rules.add(new DBRule("USERID", currentUser.getId(), "="));
		if (farmDocgroupUserDao.selectEntitys(rules).size() <= 0) {
			throw new RuntimeException("用户没有修改小组权限");
		}
		FarmDocgroup entity2 = farmDocgroupDao.getEntity(id);
		entity2.setEuser(currentUser.getId());
		entity2.setEusername(currentUser.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setGroupname(groupname);
		entity2.setGroupnote(groupnote);
		entity2.setGrouptag(grouptag);
		farmdocServer.cancelFile(entity2.getGroupimg());
		farmdocServer.submitFile(groupimg);
		entity2.setGroupimg(groupimg);
		entity2.setJoincheck(joincheck ? "1" : "0");
		farmDocgroupDao.editEntity(entity2);
		return entity2;
	}

	@Override
	public boolean isJoinCheck(String groupid) {
		return getFarmDocgroupEntity(groupid).getJoincheck().equals("1");
	}

	@Override
	public void applyGroup(String groupId, String joinUserId, String note,
			AloneUser currentUser) {
		FarmDocgroup entity = farmDocgroupDao.getEntity(groupId);
		FarmDocgroupUser goupuser = new FarmDocgroupUser();
		goupuser.setCuser(currentUser.getId());
		goupuser.setCtime(TimeTool.getTimeDate14());
		goupuser.setCusername(currentUser.getName());
		goupuser.setEuser(currentUser.getId());
		goupuser.setEusername(currentUser.getName());
		goupuser.setEtime(TimeTool.getTimeDate14());
		// 申请
		if (entity.getJoincheck().equals("1")) {
			goupuser.setPstate("3");
		} else {
			goupuser.setPstate("1");
			entity.setUsernum(getAllUserNoApplyByGroup(groupId).size() + 1);
			farmDocgroupDao.editEntity(entity);
		}
		goupuser.setGroupid(groupId);
		goupuser.setUserid(joinUserId);
		goupuser.setLeadis("0");
		goupuser.setEditis("0");
		goupuser.setShowhome("1");
		goupuser.setShowsort(10);
		goupuser.setApplynote(note);
		farmDocgroupUserDao.insertEntity(goupuser);
		// 发送消息给管理员
		for (FarmDocgroupUser node : getAllAdministratorByGroup(groupId)) {
			try {
				// 申请
				if (entity.getJoincheck().equals("1")) {
					farmDocmessageServer
							.sendMessage(node.getUserid(),
									"有用户'" + currentUser.getName() + "'申请加入"
											+ entity.getGroupname()
											+ "小组,申请备注：" + note, "有用户'"
											+ currentUser.getName() + "'申请加入"
											+ entity.getGroupname() + "小组",
									"工作小组", currentUser);
				} else {
					farmDocmessageServer
							.sendMessage(node.getUserid(), "用户'"
									+ currentUser.getName() + "'加入"
									+ entity.getGroupname() + "小组", "有用户'"
									+ currentUser.getName() + "'加入"
									+ entity.getGroupname() + "小组", "工作小组",
									currentUser);
				}

			} catch (Exception e) {
				log.error("用户申请加入小组时发送站内消息失败" + e.getMessage());
			}
		}
	}

	@Override
	public void inviteGroup(String groupId, String joinUserId, boolean isLead,
			boolean isEdit, AloneUser currentUser) {
		FarmDocgroupUser goupuser = new FarmDocgroupUser();
		goupuser.setCuser(currentUser.getId());
		goupuser.setCtime(TimeTool.getTimeDate14());
		goupuser.setCusername(currentUser.getName());
		goupuser.setEuser(currentUser.getId());
		goupuser.setEusername(currentUser.getName());
		goupuser.setEtime(TimeTool.getTimeDate14());
		// 邀请
		goupuser.setPstate("0");
		goupuser.setGroupid(groupId);
		goupuser.setUserid(joinUserId);
		goupuser.setLeadis(isLead ? "1" : "0");
		goupuser.setEditis(isEdit ? "1" : "0");
		goupuser.setShowhome("1");
		goupuser.setShowsort(10);
		farmDocgroupUserDao.insertEntity(goupuser);
	}

	@Override
	public boolean isJoinGroupByUser(String groupId, String userId) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("GROUPID", groupId, "="));
		rules.add(new DBRule("USERID", userId, "="));
		if (farmDocgroupUserDao.selectEntitys(rules).size() <= 0) {
			return false;
		}
		return true;
	}

	@Override
	public FarmDocgroupUser getFarmDocgroupUser(String groupId, String userId) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("GROUPID", groupId, "="));
		rules.add(new DBRule("USERID", userId, "="));
		return farmDocgroupUserDao.selectEntitys(rules).get(0);
	}

	@Override
	public void leaveGroup(String groupID, String userId) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("GROUPID", groupID, "="));
		rules.add(new DBRule("USERID", userId, "="));
		FarmDocgroup group = farmDocgroupDao.getEntity(groupID);
		{
			// 更新成员数量
			group
					.setUsernum(getAllUserNoApplyByGroup(group.getId()).size() - 1);
			farmDocgroupDao.editEntity(group);
		}
		farmDocgroupUserDao.deleteEntitys(rules);
		AloneUser user = FarmManager.instance().getAuthManager().getUserById(
				userId);
		for (FarmDocgroupUser node : getAllAdministratorByGroup(groupID)) {
			try {
				// 退出小组
				farmDocmessageServer.sendMessage(node.getUserid(), "用户'"
						+ user.getName() + "'退出" + group.getGroupname() + "小组",
						"有用户'" + user.getName() + "'退出" + group.getGroupname()
								+ "小组", "工作小组", user);

			} catch (Exception e) {
				log.error("用户退出小组时发送站内消息失败" + e.getMessage());
			}
		}
	}

	@Override
	public List<FarmDocgroupUser> getAllAdministratorByGroup(String groupId) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("GROUPID", groupId, "="));
		rules.add(new DBRule("LEADIS", "1", "="));
		return farmDocgroupUserDao.selectEntitys(rules);
	}

	@Override
	public List<FarmDocgroupUser> getAllUserByGroup(String groupId) {
		// 1在用，0邀请，3申请
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("GROUPID", groupId, "="));
		return farmDocgroupUserDao.selectEntitys(rules);
	}

	@Override
	public List<FarmDocgroupUser> getAllUserNoApplyByGroup(String groupId) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("GROUPID", groupId, "="));
		rules.add(new DBRule("PSTATE", "1", "="));
		return farmDocgroupUserDao.selectEntitys(rules);
	}

	@Override
	public void agreeJoinApply(String groupUserId, AloneUser currentUser) {
		FarmDocgroupUser gu = farmDocgroupUserDao.getEntity(groupUserId);
		if (!isAdminForGroup(currentUser.getId(), gu.getGroupid())) {
			throw new RuntimeException("没有操作权限");
		}
		gu.setPstate("1");
		{
			// 更新成员数量
			FarmDocgroup group = farmDocgroupDao.getEntity(gu.getGroupid());
			group
					.setUsernum(getAllUserNoApplyByGroup(group.getId()).size() + 1);
			farmDocgroupDao.editEntity(group);
		}
		farmDocgroupUserDao.editEntity(gu);
		try {
			farmDocmessageServer.sendMessage(gu.getUserid(), "见主题", "小组‘"
					+ getFarmDocgroupEntity(gu.getGroupid()).getGroupname()
					+ "’已经同意你的加入申请", null, currentUser);
		} catch (Exception e) {
			log.error("用户申请加入小组时发送站内消息失败" + e.getMessage());
		}

	}

	@Override
	public void refuseJoinApply(String groupUserId, AloneUser currentUser) {
		FarmDocgroupUser gu = farmDocgroupUserDao.getEntity(groupUserId);
		if (!isAdminForGroup(currentUser.getId(), gu.getGroupid())) {
			throw new RuntimeException("没有操作权限");
		}
		farmDocgroupUserDao.deleteEntity(gu);
	}

	@Override
	public void leaveGroup(String groupUserId, AloneUser currentUser) {
		FarmDocgroupUser gu = farmDocgroupUserDao.getEntity(groupUserId);
		if (!isAdminForGroup(currentUser.getId(), gu.getGroupid())) {
			throw new RuntimeException("没有操作权限");
		}
		{
			// 更新成员数量
			FarmDocgroup group = farmDocgroupDao.getEntity(gu.getGroupid());
			group
					.setUsernum(getAllUserNoApplyByGroup(group.getId()).size() - 1);
			farmDocgroupDao.editEntity(group);
		}
		farmDocgroupUserDao.deleteEntity(gu);
	}

	@Override
	public void setAdminForGroup(String groupUserId, AloneUser currentUser) {
		FarmDocgroupUser gu = farmDocgroupUserDao.getEntity(groupUserId);
		if (!isAdminForGroup(currentUser.getId(), gu.getGroupid())) {
			throw new RuntimeException("没有操作权限");
		}
		gu.setLeadis("1");
		farmDocgroupUserDao.editEntity(gu);
	}

	@Override
	public void setEditorForGroup(String groupUserId, AloneUser currentUser) {
		FarmDocgroupUser gu = farmDocgroupUserDao.getEntity(groupUserId);
		if (!isAdminForGroup(currentUser.getId(), gu.getGroupid())) {
			throw new RuntimeException("没有操作权限");
		}
		gu.setEditis("1");
		farmDocgroupUserDao.editEntity(gu);
	}

	@Override
	public void wipeAdminFromGroup(String groupUserId, AloneUser currentUser) {
		FarmDocgroupUser gu = farmDocgroupUserDao.getEntity(groupUserId);
		if (!isAdminForGroup(currentUser.getId(), gu.getGroupid())) {
			throw new RuntimeException("没有操作权限");
		}
		gu.setLeadis("0");
		farmDocgroupUserDao.editEntity(gu);
	}

	@Override
	public void wipeEditorForGroup(String groupUserId, AloneUser currentUser) {
		FarmDocgroupUser gu = farmDocgroupUserDao.getEntity(groupUserId);
		if (!isAdminForGroup(currentUser.getId(), gu.getGroupid())) {
			throw new RuntimeException("没有操作权限");
		}
		gu.setEditis("0");
		farmDocgroupUserDao.editEntity(gu);
	}

	@Override
	public boolean isAdminForGroup(String userid, String groupId) {
		FarmDocgroupUser groupUser = getFarmDocgroupUser(groupId, userid);
		if (groupUser == null) {
			return false;
		}
		if (groupUser.getLeadis().equals("1")) {
			return true;
		}
		return false;
	}

	@Override
	public void setGroupHomeHide(String groupId, String userId) {
		FarmDocgroupUser groupUser = getFarmDocgroupUser(groupId, userId);
		groupUser.setShowhome("0");
		farmDocgroupUserDao.editEntity(groupUser);
	}

	@Override
	public void setGroupHomeShow(String groupId, String userId) {
		FarmDocgroupUser groupUser = getFarmDocgroupUser(groupId, userId);
		groupUser.setShowhome("1");
		farmDocgroupUserDao.editEntity(groupUser);
	}

	@Override
	public void setGroupSortUp(String groupId, String userId) {
		List<FarmDocgroupUser> lists = getAllGroupUserByUser(userId);
		Collections.sort(lists, new Comparator<FarmDocgroupUser>() {
			@Override
			public int compare(FarmDocgroupUser o1, FarmDocgroupUser o2) {
				if (o1.getShowsort().compareTo(o2.getShowsort()) == 0) {
					return o1.getCtime().compareTo(o2.getCtime());
				} else {
					return o1.getShowsort().compareTo(o2.getShowsort());
				}
			}
		});
		int n = 0;
		int index = 0;
		for (FarmDocgroupUser node : lists) {
			node.setShowsort(n);
			if (node.getGroupid().equals(groupId)) {
				index = n;
			}
			farmDocgroupUserDao.editEntity(node);
			n++;
		}
		if (index == 0) {
			return;
		}
		int temp = 0;
		temp = lists.get(index).getShowsort();
		lists.get(index).setShowsort(lists.get(index - 1).getShowsort());
		lists.get(index - 1).setShowsort(temp);
		farmDocgroupUserDao.editEntity(lists.get(index));
		farmDocgroupUserDao.editEntity(lists.get(index - 1));
	}

	@Override
	public List<FarmDocgroupUser> getAllGroupUserByUser(String userId) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("USERID", userId, "="));
		return farmDocgroupUserDao.selectEntitys(rules);
	}

	@Override
	public List<Map<String, Object>> getEditorGroupByUser(String userId) {
		DataQuery query = DataQuery
				.getInstance("1", "b.groupNAME AS NAME,b.ID AS id",
						"FARM_DOCGROUP_USER a  LEFT JOIN farm_docgroup b ON a.GROUPID=b.ID");
		query.setPagesize(20);
		DataResult result = null;
		// /a.EDITIS='1' AND b.PSTATE='1' AND a.USERID=''
		query.addRule(new DBRule("a.EDITIS", "1", "="));
		query.addRule(new DBRule("b.PSTATE", "1", "="));
		query.addRule(new DBRule("a.USERID", userId, "="));
		try {
			result = query.search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result.getResultList();
	}

	@Override
	public boolean isGroupEditor(String docgroupid, String userId) {
		if (!isJoinGroupByUser(docgroupid, userId)) {
			return false;
		}
		if (getFarmDocgroupUser(docgroupid, userId).getEditis().equals("1")) {
			return true;
		}
		return false;
	}

	@Override
	public DataQuery getGroupNewDocQuery(DataQuery query, String groupId,
			AloneUser currentUser) {
		DataQuerys.wipeVirus(groupId);
		StringBuffer sqlform = new StringBuffer();
		sqlform.append("(SELECT DISTINCT ");
		sqlform
				.append("A.ID AS ID,B.EVALUATE as EVALUATE,ANSWERINGNUM, A.DOMTYPE AS DOMTYPE,A.ETIME as ETIME, A.TITLE AS TITLE, A.DOCDESCRIBE AS DOCDESCRIBE,A.AUTHOR AS AUTHOR, A.PUBTIME AS PUBTIME, A.TAGKEY  AS TAGKEY, A.IMGID AS IMGID, B.VISITNUM    AS VISITNUM, B.PRAISEYES   AS PRAISEYES, B.PRAISENO    AS PRAISENO, B.HOTNUM      AS HOTNUM, D.NAME        AS TYPENAME ");
		sqlform
				.append(" FROM farm_doc a LEFT JOIN farm_docruninfo b ON a.RUNINFOID = b.ID LEFT JOIN farm_rf_doctype c ON c.DOCID = a.ID LEFT JOIN farm_doctype d   ON d.ID = c.TYPEID LEFT JOIN farm_docgroup_user e   ON e.GROUPID=a.DOCGROUPID  ");
		sqlform
				.append(" WHERE 1 = 1 AND A.STATE = '1'  AND DOCGROUPID = '"
						+ groupId
						+ "' AND (a.READPOP = '1'   OR (a.READPOP = '2' AND e.USERID = '"
						+ currentUser.getId()
						+ "')  OR (a.READPOP = '0' AND a.CUSER = '"
						+ currentUser.getId() + "'))");
		sqlform.append(" ) ");
		query = DataQuery
				.init(
						query,
						sqlform.toString() + " a",
						"ID,DOMTYPE,TITLE,DOCDESCRIBE,AUTHOR,PUBTIME,ANSWERINGNUM,TAGKEY,IMGID, VISITNUM,PRAISEYES,PRAISENO,EVALUATE, HOTNUM, TYPENAME,ETIME");
		query.addSort(new DBSort("ETIME", "desc"));
		query.setPagesize(10);
		return query;
	}

	@Override
	public DataQuery getGroupGoodDocQuery(DataQuery query, String groupId,
			AloneUser currentUser) {
		DataQuerys.wipeVirus(groupId);
		StringBuffer sqlform = new StringBuffer();
		sqlform.append("(SELECT DISTINCT ");
		sqlform
				.append("A.ID AS ID,B.EVALUATE as EVALUATE,ANSWERINGNUM, A.DOMTYPE AS DOMTYPE,A.ETIME as ETIME, A.TITLE AS TITLE, A.DOCDESCRIBE AS DOCDESCRIBE,A.AUTHOR AS AUTHOR, A.PUBTIME AS PUBTIME, A.TAGKEY  AS TAGKEY, A.IMGID AS IMGID, B.VISITNUM    AS VISITNUM, B.PRAISEYES   AS PRAISEYES, B.PRAISENO    AS PRAISENO, B.HOTNUM      AS HOTNUM, D.NAME        AS TYPENAME ");
		sqlform
				.append(" FROM farm_doc a LEFT JOIN farm_docruninfo b ON a.RUNINFOID = b.ID LEFT JOIN farm_rf_doctype c ON c.DOCID = a.ID LEFT JOIN farm_doctype d   ON d.ID = c.TYPEID LEFT JOIN farm_docgroup_user e   ON e.GROUPID=a.DOCGROUPID  ");
		sqlform
				.append(" WHERE 1 = 1 AND A.STATE = '1'  AND DOCGROUPID = '"
						+ groupId
						+ "' AND (a.READPOP = '1'   OR (a.READPOP = '2' AND e.USERID = '"
						+ currentUser.getId()
						+ "')  OR (a.READPOP = '0' AND a.CUSER = '"
						+ currentUser.getId() + "'))");
		sqlform.append(" ) ");
		query = DataQuery
				.init(
						query,
						sqlform.toString() + " a",
						"ID,DOMTYPE,TITLE,DOCDESCRIBE,AUTHOR,PUBTIME,ANSWERINGNUM,TAGKEY,IMGID, VISITNUM,PRAISEYES,PRAISENO,EVALUATE, HOTNUM, TYPENAME,ETIME");
		query.addSort(new DBSort("EVALUATE", "desc"));
		query.setPagesize(10);
		return query;
	}

	@Override
	public DataQuery getGroupHotDocQuery(DataQuery query, String groupId,
			AloneUser currentUser) {
		StringBuffer sqlform = new StringBuffer();
		DataQuerys.wipeVirus(groupId);
		sqlform.append("(SELECT DISTINCT ");
		sqlform
				.append("A.ID AS ID,B.EVALUATE as EVALUATE, A.DOMTYPE AS DOMTYPE,ANSWERINGNUM,A.ETIME as ETIME, A.TITLE AS TITLE, A.DOCDESCRIBE AS DOCDESCRIBE,A.AUTHOR AS AUTHOR, A.PUBTIME AS PUBTIME, A.TAGKEY  AS TAGKEY, A.IMGID AS IMGID, B.VISITNUM    AS VISITNUM, B.PRAISEYES   AS PRAISEYES, B.PRAISENO    AS PRAISENO, B.HOTNUM      AS HOTNUM, D.NAME        AS TYPENAME ");
		sqlform
				.append(" FROM farm_doc a LEFT JOIN farm_docruninfo b ON a.RUNINFOID = b.ID LEFT JOIN farm_rf_doctype c ON c.DOCID = a.ID LEFT JOIN farm_doctype d   ON d.ID = c.TYPEID LEFT JOIN farm_docgroup_user e   ON e.GROUPID=a.DOCGROUPID  ");
		sqlform
				.append(" WHERE 1 = 1 AND A.STATE = '1'  AND DOCGROUPID = '"
						+ groupId
						+ "' AND (a.READPOP = '1'   OR (a.READPOP = '2' AND e.USERID = '"
						+ currentUser.getId()
						+ "')  OR (a.READPOP = '0' AND a.CUSER = '"
						+ currentUser.getId() + "'))");
		sqlform.append(" ) ");
		query = DataQuery
				.init(
						query,
						sqlform.toString() + " a",
						"ID,DOMTYPE,TITLE,DOCDESCRIBE,AUTHOR,PUBTIME,TAGKEY,IMGID,ANSWERINGNUM, VISITNUM,PRAISEYES,PRAISENO,EVALUATE, HOTNUM, TYPENAME,ETIME");
		query.addSort(new DBSort("HOTNUM", "desc"));
		query.setPagesize(10);
		return query;
	}

	@Override
	public int getGroupDocNum(String groupId) {
		return farmDocgroupDao.getGroupDocNum(groupId);
	}

	@Override
	public FarmDocgroupUser editMinFarmDocgroupUserEntity(
			FarmDocgroupUser entity, AloneUser currentUser) {
		FarmDocgroupUser entity2 = farmDocgroupUserDao
				.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPstate(entity.getPstate());
		// entity2.setPcontent(entity.getPcontent());
		// entity2.setGroupid(entity.getGroupid());
		// entity2.setUserid(entity.getUserid());
		entity2.setLeadis(entity.getLeadis());
		entity2.setEditis(entity.getEditis());
		entity2.setShowhome(entity.getShowhome());
		// entity2.setShowsort(entity.getShowsort());
		farmDocgroupUserDao.editEntity(entity2);
		return entity2;
	}

	@Override
	public DataQuery createMyGroupDocQuery(DataQuery query, String userid) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT A.ID  AS ID,GROUPNAME,f.id as GROUPID, B.EVALUATE    AS EVALUATE, A.DOMTYPE     AS DOMTYPE, A.ETIME       AS ETIME, A.TITLE       AS TITLE, A.DOCDESCRIBE AS DOCDESCRIBE,");
		sqlBuffer
				.append("A.AUTHOR AS AUTHOR,e.SHOWHOME as SHOWHOME, A.PUBTIME     AS PUBTIME, A.TAGKEY      AS TAGKEY, A.IMGID       AS IMGID, B.VISITNUM    AS VISITNUM, B.PRAISEYES   AS PRAISEYES, B.PRAISENO    AS PRAISENO, B.HOTNUM      AS HOTNUM, ");
		sqlBuffer
				.append(" D.NAME        AS TYPENAME, f.GROUPIMG    AS groupIMG");
		sqlBuffer
				.append(" FROM farm_doc a LEFT JOIN farm_docruninfo b ON a.RUNINFOID = b.ID LEFT JOIN farm_rf_doctype c ON c.DOCID = a.ID");
		sqlBuffer
				.append("  LEFT JOIN farm_doctype d ON d.ID = c.TYPEID LEFT JOIN farm_docgroup_user e ON e.GROUPID = a.DOCGROUPID LEFT JOIN farm_docgroup f ON f.ID=e.GROUPID");
		sqlBuffer.append(" WHERE 1 = 1  AND A.STATE = '1' AND e.USERID='"
				+ userid + "'");
		sqlBuffer
				.append("   AND (a.READPOP = '1'  OR (a.READPOP = '2' AND e.USERID = '"
						+ userid
						+ "') OR (a.READPOP = '0' AND a.CUSER = '"
						+ userid + "'))");
		query = DataQuery
				.init(
						query,
						"(" + sqlBuffer.toString() + ") a",
						"ID,EVALUATE,DOMTYPE,ETIME,SHOWHOME,GROUPNAME,GROUPID,TITLE,DOCDESCRIBE,AUTHOR,PUBTIME,TAGKEY,IMGID,VISITNUM,PRAISEYES,PRAISENO,HOTNUM,TYPENAME,GROUPIMG");
		query.setPagesize(10);
		return query;
	}

	@Override
	public FarmDocgroup editDocgroupJoinCheck(boolean isJoinCheck,
			String groupId, AloneUser currentUser) {
		FarmDocgroup entity2 = farmDocgroupDao.getEntity(groupId);
		entity2.setEuser(currentUser.getId());
		entity2.setEusername(currentUser.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setJoincheck(isJoinCheck ? "1" : "0");
		farmDocgroupDao.editEntity(entity2);
		return entity2;
	}

	@Override
	public DataQuery getGroupBadDocQuery(DataQuery query, String groupId,
			AloneUser currentUser) {
		DataQuerys.wipeVirus(groupId);
		StringBuffer sqlform = new StringBuffer();
		sqlform.append("(SELECT DISTINCT ");
		sqlform
				.append("A.ID AS ID,B.EVALUATE as EVALUATE,ANSWERINGNUM, A.DOMTYPE AS DOMTYPE,A.ETIME as ETIME, A.TITLE AS TITLE, A.DOCDESCRIBE AS DOCDESCRIBE,A.AUTHOR AS AUTHOR, A.PUBTIME AS PUBTIME, A.TAGKEY  AS TAGKEY, A.IMGID AS IMGID, B.VISITNUM    AS VISITNUM, B.PRAISEYES   AS PRAISEYES, B.PRAISENO    AS PRAISENO, B.HOTNUM      AS HOTNUM, D.NAME        AS TYPENAME ");
		sqlform
				.append(" FROM farm_doc a LEFT JOIN farm_docruninfo b ON a.RUNINFOID = b.ID LEFT JOIN farm_rf_doctype c ON c.DOCID = a.ID LEFT JOIN farm_doctype d   ON d.ID = c.TYPEID LEFT JOIN farm_docgroup_user e   ON e.GROUPID=a.DOCGROUPID  ");
		sqlform
				.append(" WHERE 1 = 1 AND A.STATE = '1'  AND DOCGROUPID = '"
						+ groupId
						+ "' AND (a.READPOP = '1'   OR (a.READPOP = '2' AND e.USERID = '"
						+ currentUser.getId()
						+ "')  OR (a.READPOP = '0' AND a.CUSER = '"
						+ currentUser.getId() + "'))");
		sqlform.append(" ) ");
		query = DataQuery
				.init(
						query,
						sqlform.toString() + " a",
						"ID,DOMTYPE,TITLE,DOCDESCRIBE,AUTHOR,PUBTIME,ANSWERINGNUM,TAGKEY,IMGID, VISITNUM,PRAISEYES,PRAISENO,EVALUATE, HOTNUM, TYPENAME,ETIME");
		query.addSort(new DBSort("EVALUATE", "asc"));
		query.setPagesize(10);
		return query;
	}
}
