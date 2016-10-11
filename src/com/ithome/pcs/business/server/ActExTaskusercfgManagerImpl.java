package com.ithome.pcs.business.server;

import java.util.List;

import org.apache.log4j.Logger;
import com.ithome.pcs.business.dao.ActExTaskusercfgDaoInter;
import com.ithome.pcs.comm.entity.ActExTaskusercfg;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 */
public class ActExTaskusercfgManagerImpl implements ActExTaskusercfgManagerInter{
	private ActExTaskusercfgDaoInter  actExTaskusercfgDao;
	private static final Logger log = Logger.getLogger(ActExTaskusercfgManagerImpl.class);
	public ActExTaskusercfg insertActExTaskusercfgEntity(ActExTaskusercfg entity,AloneUser user) {
		return actExTaskusercfgDao.insertEntity(entity);
	}
	public ActExTaskusercfg editActExTaskusercfgEntity(ActExTaskusercfg entity,AloneUser user) {
		actExTaskusercfgDao.editEntity(entity);
		return entity;
	}
	public void deleteActExTaskusercfgEntity(String id,AloneUser user) {
		actExTaskusercfgDao.deleteEntity(actExTaskusercfgDao.getEntity(id));
	}
	public ActExTaskusercfg getActExTaskusercfgEntity(String id) {
		if (id == null){return null;}
		return actExTaskusercfgDao.getEntity(id);
	}
	@Override
	public DataQuery createActExTaskusercfgSimpleQuery(DataQuery query) {
		/*DataQuery dbQuery = DataQuery
				.init(
						query,
						"POPU_ADDRESS_INFO",
						"'1',ADDRESSID,ADDRESSPARENTID,ADDRESSNAME,COMMUNITYID,CREATEUSERID,UPDATEUSERID,CREATEDATE,UPDATEDATE,ADDRESSNOTE,FK_QLQ_MAP,LEVELS,BUILDINGCODE_3D,BUILDINGCODE_2D,BUILDINGTYPE,FANGCODE,EXCELID,GRIDCODE");
		return dbQuery;*/
		return null;
	}
	
	@Override
	public List<String> getActExTaskurgBytaskKey(String taskKey) {
		return this.actExTaskusercfgDao.getActExTaskurgBytaskKey(taskKey);
	}
	
	//----------------------------------------------------------------------------------
	public ActExTaskusercfgDaoInter getActExTaskusercfgDao() {
		return actExTaskusercfgDao;
	}
	public void setActExTaskusercfgDao(ActExTaskusercfgDaoInter actExTaskusercfgDao) {
		this.actExTaskusercfgDao = actExTaskusercfgDao;
	}
	
	
	

}
