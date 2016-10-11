package com.ithome.pcs.business.server;

import org.apache.log4j.Logger;
import com.ithome.pcs.business.dao.ActExCompletedformDaoInter;
import com.ithome.pcs.business.dao.ActExLeavetestDaoInter;
import com.ithome.pcs.comm.entity.ActExCompletedform;
import com.ithome.pcs.comm.entity.ActExLeavetest;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 */
public class ActExLeavetestManagerImpl implements ActExLeavetestManagerInter{
	private ActExLeavetestDaoInter  actExLeavetestDao;
	private ActExCompletedformDaoInter  actExCompletedformDao;
	private static final Logger log = Logger.getLogger(ActExLeavetestManagerImpl.class);
	
	public void insertLeavetestandCompletedform(ActExLeavetest actExLeavetest,ActExCompletedform actExCompletedform,AloneUser user){
		this.actExLeavetestDao.insertEntity(actExLeavetest);
		this.actExCompletedformDao.insertEntity(actExCompletedform);
	}
	
	public ActExLeavetest insertActExLeavetestEntity(ActExLeavetest entity,AloneUser user) {
		return actExLeavetestDao.insertEntity(entity);
	}
	public ActExLeavetest editActExLeavetestEntity(ActExLeavetest entity,AloneUser user) {
		actExLeavetestDao.editEntity(entity);
		return entity;
	}
	public void deleteActExLeavetestEntity(String id,AloneUser user) {
		actExLeavetestDao.deleteEntity(actExLeavetestDao.getEntity(id));
	}
	public ActExLeavetest getActExLeavetestEntity(String id) {
		if (id == null){return null;}
		return actExLeavetestDao.getEntity(id);
	}
	@Override
	public DataQuery createActExLeavetestSimpleQuery(DataQuery query) {
	     return null;
	}
	//----------------------------------------------------------------------------------
	public ActExLeavetestDaoInter getActExLeavetestDao() {
		return actExLeavetestDao;
	}
	public void setActExLeavetestDao(ActExLeavetestDaoInter actExLeavetestDao) {
		this.actExLeavetestDao = actExLeavetestDao;
	}
	public ActExCompletedformDaoInter getActExCompletedformDao() {
		return actExCompletedformDao;
	}
	public void setActExCompletedformDao(
			ActExCompletedformDaoInter actExCompletedformDao) {
		this.actExCompletedformDao = actExCompletedformDao;
	}

}
