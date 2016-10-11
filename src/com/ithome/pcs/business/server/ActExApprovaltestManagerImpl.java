package com.ithome.pcs.business.server;

import org.apache.log4j.Logger;
import com.ithome.pcs.business.dao.ActExApprovaltestDaoInter;
import com.ithome.pcs.business.dao.ActExCompletedformDaoInter;
import com.ithome.pcs.comm.entity.ActExApprovaltest;
import com.ithome.pcs.comm.entity.ActExCompletedform;
import com.ithome.pcs.comm.entity.ActExLeavetest;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 */
public class ActExApprovaltestManagerImpl implements ActExApprovaltestManagerInter{
	private ActExApprovaltestDaoInter  actExApprovaltestDao;
	private ActExCompletedformDaoInter  actExCompletedformDao;
	private static final Logger log = Logger.getLogger(ActExApprovaltestManagerImpl.class);
	
	/**
	 * 
	 * @方法名称: insertLeavetestandCompletedform
	 * @描述：TODO(新增实体管理实体并把信息填写到业务流程已填表单) 
	 * @返回值类型： void  
	 * @param actExLeavetest
	 * @param actExCompletedform
	 * @param user
	 */
	public void insertApprovaltestandCompletedform(ActExApprovaltest actExApprovaltest,ActExCompletedform actExCompletedform,AloneUser user){
		this.actExApprovaltestDao.insertEntity(actExApprovaltest);
		this.actExCompletedformDao.insertEntity(actExCompletedform);
	}
	public ActExApprovaltest insertActExApprovaltestEntity(ActExApprovaltest entity,AloneUser user) {
		return actExApprovaltestDao.insertEntity(entity);
	}
	public ActExApprovaltest editActExApprovaltestEntity(ActExApprovaltest entity,AloneUser user) {
		actExApprovaltestDao.editEntity(entity);
		return entity;
	}
	public void deleteActExApprovaltestEntity(String id,AloneUser user) {
		actExApprovaltestDao.deleteEntity(actExApprovaltestDao.getEntity(id));
	}
	public ActExApprovaltest getActExApprovaltestEntity(String id) {
		if (id == null){return null;}
		return actExApprovaltestDao.getEntity(id);
	}
	@Override
	public DataQuery createActExApprovaltestSimpleQuery(DataQuery query) {
		return null;
	}
	//----------------------------------------------------------------------------------
	public ActExApprovaltestDaoInter getActExApprovaltestDao() {
		return actExApprovaltestDao;
	}
	public void setActExApprovaltestDao(
			ActExApprovaltestDaoInter actExApprovaltestDao) {
		this.actExApprovaltestDao = actExApprovaltestDao;
	}
	public ActExCompletedformDaoInter getActExCompletedformDao() {
		return actExCompletedformDao;
	}
	public void setActExCompletedformDao(
			ActExCompletedformDaoInter actExCompletedformDao) {
		this.actExCompletedformDao = actExCompletedformDao;
	}
	

}
