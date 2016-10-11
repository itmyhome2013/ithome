package com.ithome.pcs.handler;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import com.ithome.pcs.business.server.ActExTaskusercfgManagerInter;

import com.farm.web.spring.BeanFactory;

/**
 * 
*    
* 项目名称：pcsnew   
* 类名称：ManagerTaskHandler   
* 类描述：   根据节点动态分配节点人员
* 创建人：张晓亮   
* 创建时间：2015年7月2日 上午10:36:11   
* 修改人：张晓亮   
* 修改时间：2015年7月2日 上午10:36:11   
* 修改备注：   
* @version    
*
 */
@SuppressWarnings("serial")
public class CreateTaskUserHandler implements TaskListener {
	
	public void notify(DelegateTask delegateTask) {
		String taskKey=delegateTask.getTaskDefinitionKey();
		//从web中获取spring容器
		ActExTaskusercfgManagerInter actExTaskusercfgManagerInter = (ActExTaskusercfgManagerInter) BeanFactory.getBean("ActExTaskusercfg_ProxyId");
	   List<String> userIdList= actExTaskusercfgManagerInter.getActExTaskurgBytaskKey(taskKey);
		if(userIdList.size()==1){
			delegateTask.setAssignee(userIdList.get(0));
		}else{	
		   for(String userId : userIdList){
					delegateTask.addCandidateUser(userId);
				}
		}
	}


}
