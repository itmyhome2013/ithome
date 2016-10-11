package com.ithome.pcs.business.server;

import java.util.List;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import com.ithome.pcs.comm.entity.ActExTask;

/**实体管理
 * @author 赵克俭(新增)
 *
 */
public interface ActExTaskManagerInter {
	/**
	 *新增实体管理实体
	 * 
	 * @param entity
	 */
	public void insertActExTaskEntity(ActExTask entity);
	
	/**
	 * 清空实体
	 */
	public void deleteAllActExTaskEntity();
	
	/**
	 * 根据流程key和版本号查询数据
	 * @param processKey
	 * @param version
	 * @return
	 */
	public List<ActExTask> findByProcessKeyAndVersion(String processKey,int version);
	
	/**
	 * 根据流程key和版本号删除数据
	 * @param processKey
	 */
	public void deleteByProcessKey(String processKey);
	
	
}