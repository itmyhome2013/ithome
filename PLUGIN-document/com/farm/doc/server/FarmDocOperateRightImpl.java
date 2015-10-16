package com.farm.doc.server;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.doc.dao.FarmDocDaoInter;
import com.farm.doc.domain.FarmDoc;

public class FarmDocOperateRightImpl implements FarmDocOperateRightInter {
	private FarmDocDaoInter farmDocDao;
	private FarmDocgroupManagerInter farmdocgroupServer;

	@Override
	public boolean isDel(AloneUser user, FarmDoc doc) {
		if (user == null) {
			return false;
		}
		// 非小组权限只允许本人删除
		if (!doc.getWritepop().equals("2")
				&& doc.getCuser().equals(user.getId())) {
			return true;
		}
		// 如果编辑权限是小组的，只有管理员可以删除
		if (doc.getWritepop().equals("2")) {
			if (doc.getDocgroupid() == null) {
				return false;
			}
			// 本人可以删除
			// if(doc.getCuser().equals(user.getId())){
			// return true;
			// }
			if (farmdocgroupServer.isAdminForGroup(user.getId(), doc
					.getDocgroupid())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isRead(AloneUser user, FarmDoc doc) {
		// 公开权限允许任何人阅读
		if (doc.getReadpop().equals("1")) {
			return true;
		}
		if (user == null) {
			return false;
		}
		// 权限是本人的时候,允许本人阅读
		if ((doc.getReadpop().equals("0") && doc.getCuser()
				.equals(user.getId()))) {
			return true;
		}
		// 当阅读权限被指定到小组，且用户加入小组时允许其阅读该文档
		if (doc.getReadpop().equals("2")) {
			if (doc.getDocgroupid() != null
					&& farmdocgroupServer.isJoinGroupByUser(
							doc.getDocgroupid(), user.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAllUserRead(FarmDoc doc) {
		return isRead(null, doc);
	}

	@Override
	public boolean isWrite(AloneUser user, FarmDoc doc) {
		if (user == null) {
			return false;
		}
		// 公开写权限或权限是本人的时候
		if (doc.getWritepop().equals("1")
				|| (doc.getWritepop().equals("0") && doc.getCuser().equals(
						user.getId()))) {
			return true;
		}
		// 小组文档且文档赋予了小组编辑权限时，如果当前用户拥有小组编辑权限则允许其修改文档
		if (doc.getWritepop().equals("2")) {
			if (doc.getDocgroupid() != null
					&& farmdocgroupServer.isGroupEditor(doc.getDocgroupid(),
							user.getId())) {
				return true;
			}
		}
		return false;
	}

	public FarmDocDaoInter getFarmDocDao() {
		return farmDocDao;
	}

	public void setFarmDocDao(FarmDocDaoInter farmDocDao) {
		this.farmDocDao = farmDocDao;
	}

	public FarmDocgroupManagerInter getFarmdocgroupServer() {
		return farmdocgroupServer;
	}

	public void setFarmdocgroupServer(
			FarmDocgroupManagerInter farmdocgroupServer) {
		this.farmdocgroupServer = farmdocgroupServer;
	}

}
