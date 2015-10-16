package com.farm.console.server.contain;

import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.prisist.domain.AloneUser;


public interface DictionaryTypeManagerInter {
	/**
	 * 通过一个键获得一个应用配置字符串值
	 */
	public String getConfigValue(String key);

	/**
	 * 初始化应用配置集合
	 */
	public boolean initConfig();

	/**
	 * 强制刷新配置集合
	 */
	public boolean refreshConfigMap();

	public void deleteEntity(String entity, AloneUser user);

	public void editEntity(AloneDictionaryType entity);

	public AloneDictionaryType editEntity(AloneDictionaryType entity,
			AloneUser user);

	public AloneDictionaryType getEntity(String id);

	public AloneDictionaryType insertEntity(AloneDictionaryType entity,
			AloneUser user);

}
