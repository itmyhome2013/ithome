package com.ithome.pcs.business.server;

import org.apache.log4j.Logger;
import com.ithome.pcs.business.dao.ActExCompletedformDaoInter;
import com.ithome.pcs.comm.entity.ActExCompletedform;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 */
public class ActExCompletedformManagerImpl implements ActExCompletedformManagerInter{
	private ActExCompletedformDaoInter  actExCompletedformDao;
	private static final Logger log = Logger.getLogger(ActExCompletedformManagerImpl.class);
	public ActExCompletedform insertActExCompletedformEntity(ActExCompletedform entity,AloneUser user) {
		return actExCompletedformDao.insertEntity(entity);
	}
	public ActExCompletedform editActExCompletedformEntity(ActExCompletedform entity,AloneUser user) {
		ActExCompletedform entity2 = actExCompletedformDao.getEntity(entity.getCompletedformid());
		entity2.setPath(entity.getPath());
		actExCompletedformDao.editEntity(entity2);
		return entity;
	}
	public void deleteActExCompletedformEntity(String id,AloneUser user) {
		actExCompletedformDao.deleteEntity(actExCompletedformDao.getEntity(id));
	}
	public ActExCompletedform getActExCompletedformEntity(String id) {
		if (id == null){return null;}
		return actExCompletedformDao.getEntity(id);
	}
	@Override
	public DataQuery createActExCompletedformSimpleQuery(DataQuery query) {
		return null;
	}
	//----------------------------------------------------------------------------------
	public ActExCompletedformDaoInter getActExCompletedformDao() {
		return actExCompletedformDao;
	}
	public void setActExCompletedformDao(
			ActExCompletedformDaoInter actExCompletedformDao) {
		this.actExCompletedformDao = actExCompletedformDao;
	}
	

}
