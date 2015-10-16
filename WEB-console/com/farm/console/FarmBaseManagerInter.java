package com.farm.console;

import java.util.List;
import java.util.Map;


import com.farm.console.contain.exception.UserNoExistException;
import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneRolegroup;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.console.server.contain.ActionManagerInter;
import com.farm.console.server.contain.MenuManagerInter;
import com.farm.console.server.contain.OrganizationManagerInter;
import com.farm.console.server.contain.RolegroupManagerInter;
import com.farm.console.server.contain.UserManagerInter;
import com.farm.core.auth.AuthenticateInter;
import com.farm.web.adapter.AuthAdapterInter;

/**
 * platForm门面类
 * 
 * @author 王东
 * 
 */
public interface FarmBaseManagerInter {
	public AuthAdapterInter getAuthManager();
	/**
	 * 加解密工具类
	 * 
	 * @return加解密工具类
	 */
	public AuthenticateInter getAuthUtil();

	/**
	 * 获得系统所有受检权限
	 * 
	 * @return map《URL-index，urlId》
	 */
	public Map<String, String> getAllAction();
	/** 通过字段名获得数据字典的键值对儿
	 * @param index 字段
	 * @return map<类型,类型title>
	 */
	public Map<String, String> findDicTitleForIndex(String index);
	
	/** 通过字段名获得数据字典的键值对儿 有序
	 * @param index 字段
	 * @return String[类型,类型title,sort]
	 */
	public List<Map<String, Object>> findDicTitleForIndeHasSort(String index);
	/**
	 * 设置系统绝对路径
	 * 
	 * @param str
	 */
	public void setRealPath(String str);

	/**
	 * 获得系统绝对路径
	 * example: E:\servertomcat\newServer\apache-tomcat-6.0.16\apache-tomcat-6.0.16\webapps\MAC
	 * @return str
	 */
	public String getRealPath();

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
	/**由数据字典分类ID获得一个分类实体
	 * @param typeId 分类的ID
	 * @return 返回实体对象
	 */
	public AloneDictionaryType getDictionaryType(String typeId);
}
