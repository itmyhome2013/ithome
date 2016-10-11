package com.ithome.pcs.business.server;

import org.apache.log4j.Logger;
import com.ithome.pcs.business.dao.ActExProcessDaoInter;
import com.ithome.pcs.comm.entity.ActExProcess;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 */
public class ActExProcessManagerImpl implements ActExProcessManagerInter{
	private ActExProcessDaoInter  actExProcessDao;
	private static final Logger log = Logger.getLogger(ActExProcessManagerImpl.class);
	public ActExProcess insertActExProcessEntity(ActExProcess entity,AloneUser user) {
		return actExProcessDao.insertEntity(entity);
	}
	public ActExProcess editActExProcessEntity(ActExProcess entity,AloneUser user) {
		actExProcessDao.editEntity(entity);
		return entity;
	}
	public void deleteActExProcessEntity(String id,AloneUser user) {
		actExProcessDao.deleteEntity(actExProcessDao.getEntity(id));
	}
	public ActExProcess getActExProcessEntity(String id) {
		if (id == null){return null;}
		return actExProcessDao.getEntity(id);
	}
	@Override
	public DataQuery createActExProcessSimpleQuery(DataQuery query) {
		/*DataQuery dbQuery = DataQuery
				.init(
						query,
						"POPU_ADDRESS_INFO",
						"'1',ADDRESSID,ADDRESSPARENTID,ADDRESSNAME,COMMUNITYID,CREATEUSERID,UPDATEUSERID,CREATEDATE,UPDATEDATE,ADDRESSNOTE,FK_QLQ_MAP,LEVELS,BUILDINGCODE_3D,BUILDINGCODE_2D,BUILDINGTYPE,FANGCODE,EXCELID,GRIDCODE");
		return dbQuery;*/
		return null;
	}
	//----------------------------------------------------------------------------------
	public ActExProcessDaoInter getActExProcessDao() {
		return actExProcessDao;
	}
	public void setActExProcessDao(ActExProcessDaoInter actExProcessDao) {
		this.actExProcessDao = actExProcessDao;
	}
	

}
