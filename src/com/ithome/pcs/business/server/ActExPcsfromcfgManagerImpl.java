package com.ithome.pcs.business.server;

import org.apache.log4j.Logger;
import com.ithome.pcs.business.dao.ActExPcsfromcfgDaoInter;
import com.ithome.pcs.comm.entity.ActExPcsfromcfg;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 */
public class ActExPcsfromcfgManagerImpl implements ActExPcsfromcfgManagerInter{
	private ActExPcsfromcfgDaoInter  actExPcsfromcfgDao;
	private static final Logger log = Logger.getLogger(ActExPcsfromcfgManagerImpl.class);
	public ActExPcsfromcfg insertActExPcsfromcfgEntity(ActExPcsfromcfg entity,AloneUser user) {
		return actExPcsfromcfgDao.insertEntity(entity);
	}
	public ActExPcsfromcfg editActExPcsfromcfgEntity(ActExPcsfromcfg entity,AloneUser user) {
		actExPcsfromcfgDao.editEntity(entity);
		return entity;
	}
	public void deleteActExPcsfromcfgEntity(String id,AloneUser user) {
		actExPcsfromcfgDao.deleteEntity(actExPcsfromcfgDao.getEntity(id));
	}
	public ActExPcsfromcfg getActExPcsfromcfgEntity(String id) {
		if (id == null){return null;}
		return actExPcsfromcfgDao.getEntity(id);
	}
	@Override
	public DataQuery createActExPcsfromcfgSimpleQuery(DataQuery query) {
		return null;
	}
	//----------------------------------------------------------------------------------
	public ActExPcsfromcfgDaoInter getActExPcsfromcfgDao() {
		return actExPcsfromcfgDao;
	}
	public void setActExPcsfromcfgDao(ActExPcsfromcfgDaoInter actExPcsfromcfgDao) {
		this.actExPcsfromcfgDao = actExPcsfromcfgDao;
	}
	

}
