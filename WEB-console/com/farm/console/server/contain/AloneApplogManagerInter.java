package com.farm.console.server.contain;

import com.farm.console.prisist.domain.AloneApplog;
import com.farm.console.prisist.domain.AloneUser;


/**系统日志
 * @author MAC_wd
 *
 */
public interface AloneApplogManagerInter {
	/**
	 *新增实体
	 * 
	 * @param entity
	 */
	public AloneApplog insertEntity(AloneApplog entity,AloneUser user);
	/**
	 *修改实体
	 * 
	 * @param entity
	 */
	public AloneApplog editEntity(AloneApplog entity,AloneUser user);
	/**
	 *删除实体
	 * 
	 * @param entity
	 */
	public void deleteEntity(String entity,AloneUser user);
	/**
	 *获得实体
	 * 
	 * @param id
	 * @return
	 */
	public AloneApplog getEntity(String id);
	
	/**写入日志
	 * @param describes 内容
	 * @param appuser 操作人
	 * @param level 日志等级
	 * @param method 方法
	 * @param classname 类
	 * @param ip 
	 * @return
	 */
	public AloneApplog log(String describes,String appuser,String level,String method,String classname,String ip);
}