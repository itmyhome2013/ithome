package com.ithome.pcs.business.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ithome.pcs.business.dao.ActExTaskDaoInter;
import com.ithome.pcs.comm.entity.ActExTask;


/**实体管理
 * @author 赵克俭(新增)
 */
public class ActExTaskManagerImpl implements ActExTaskManagerInter{
	private ActExTaskDaoInter  actExTaskDao;

	@Override
	public void insertActExTaskEntity(ActExTask entity) {
		actExTaskDao.insertEntity(entity);
	}
	
	@Override
	public void deleteAllActExTaskEntity() {
		actExTaskDao.deleteAllEntity();
	}
	
	@Override
	public List<ActExTask> findByProcessKeyAndVersion(String processKey,int version) {
		Map<String,String> conditions = new HashMap<String,String>();
		conditions.put("processKey", processKey);
		conditions.put("version", String.valueOf(version));
		return actExTaskDao.findByConditions(conditions);
	}
	
	@Override
	public void deleteByProcessKey(String processKey) {
		List<ActExTask> taskList = actExTaskDao.findByCondition("processKey", processKey);
		if(taskList != null && taskList.size() > 0){
			for(int i = 0; i < taskList.size(); i ++){
				ActExTask task = taskList.get(i);
				if(!task.getFlag().equals("1")){
					task.setFlag("1");
					actExTaskDao.updateEntity(task);
				}
			}
		}
	}


	//----------------------------------------------------------------------------------
	public ActExTaskDaoInter getActExTaskDao() {
		return actExTaskDao;
	}

	public void setActExTaskDao(ActExTaskDaoInter actExTaskDao) {
		this.actExTaskDao = actExTaskDao;
	}

}
