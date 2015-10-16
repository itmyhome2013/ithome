package com.farm.console.server.contain;

import java.util.List;
import java.util.Map;


import com.farm.console.contain.exception.HaveIndexException;
import com.farm.console.prisist.domain.AloneParameter;
import com.farm.console.prisist.domain.AloneUser;


public interface ParameterManagerInter {

	public final String CURRENCY_YES = "0";
	public final String CURRENCY_USER = "1";
	public final String TYPE_GENERAL = "0";
	public final String TYPE_DECORDE = "1";
	public final String TYPE_MD5 = "2";

	/**
	 * 强制刷新配置集合
	 */
	public boolean refreshConfigMap();

	public void deleteEntity(String entity, AloneUser user);

	public AloneParameter editEntity(AloneParameter entity,
			AloneUser aloneUser);
	
	public AloneParameter getEntity(String id);

	/**
	 * 可以为参数选择一个域（相当于一个参数分类）
	 * 
	 * @param entity
	 * @param domain
	 * @throws HaveIndexException
	 */
	public AloneParameter insertEntity(AloneParameter entity, String domain,
			AloneUser aloneUser) throws HaveIndexException;

	/**
	 * 获得所有系统参数从数据库中只有通用的
	 * 
	 * @return
	 */
	public Map<String, AloneParameter> getAllParameter();

	/**
	 * 获得所有系统参数从数据库中只有用户相关的
	 * 
	 * @return
	 */
	public AloneParameter getParameterValueForUser(String key, String user);

	/**
	 * 根据参数域类型获取转换好的list集合。如：将字符串解析为list，页面组合为下拉选。
	 * 
	 * @author zhang_hc
	 * @param domainType
	 *            ：参数域类型（1：系统配置页；2：应用参数页）。说明：“系统参数管理”页面按导航栏分开
	 * @time 2012-8-28 下午02:59:08
	 */
	public List<Map<String, Object>> getTransParamList(String domainType);

	/**
	 * 验证是否是重复key
	 * 
	 * @param paramKey ：“参数键”名称
	 * @param excludeParamId  ：要排除的系统参数ID
	 * @return 重复：true；不重复：false
	 * @author zhang_hc
	 * @time 2012-9-7 下午01:00:14
	 */
	public boolean isRepeatKey(String paramKey, String excludeParamId);
	
	/**
	 * 修改系统参数的值
	 * 
	 * @param paramId ：系统参数ID
	 * @param pValue ：参数值
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-9 上午09:41:08
	 */
	public AloneParameter editSubmitByPValue(String paramId, String pValue, AloneUser aloneUser);

	/**
	 * 根据key查找实体
	 * 
	 * @param paramKey 参数键
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-12 下午05:59:18
	 */
	public AloneParameter findEntityByKey(String paramKey);
}
