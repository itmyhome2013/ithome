package com.ithome.pcs.business.server;

import org.apache.log4j.Logger;
import com.ithome.pcs.business.dao.ActExApprovalDaoInter;
import com.ithome.pcs.comm.entity.ActExApproval;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
/**实体管理
 * @author MAC_wd
 */
public class ActExApprovalManagerImpl implements ActExApprovalManagerInter{
	private ActExApprovalDaoInter  actExApprovalDao;
	private static final Logger log = Logger.getLogger(ActExApprovalManagerImpl.class);
	public ActExApproval insertActExApprovalEntity(ActExApproval entity,AloneUser user) {
		return actExApprovalDao.insertEntity(entity);
	}
	public ActExApproval editActExApprovalEntity(ActExApproval entity,AloneUser user) {
		actExApprovalDao.editEntity(entity);
		return entity;
	}
	public void deleteActExApprovalEntity(String id,AloneUser user) {
		actExApprovalDao.deleteEntity(actExApprovalDao.getEntity(id));
	}
	public ActExApproval getActExApprovalEntity(String id) {
		if (id == null){return null;}
		return actExApprovalDao.getEntity(id);
	}
	@Override
	public DataQuery createActExApprovalSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"ACT_EX_APPROVAL",
						"'1',APPROVALID,PROCESSID,APPROVALUSERNAME,APPROVALDATE,APPROVALMESSAGE,APPROVALSTATE,SUBMITNODEKNAME,APPROVALTASKNAME");
		return dbQuery;
	}
	//----------------------------------------------------------------------------------
	public ActExApprovalDaoInter getActExApprovalDao() {
		return actExApprovalDao;
	}
	public void setActExApprovalDao(ActExApprovalDaoInter actExApprovalDao) {
		this.actExApprovalDao = actExApprovalDao;
	}
	

}
