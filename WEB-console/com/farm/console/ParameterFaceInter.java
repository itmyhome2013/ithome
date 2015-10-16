package com.farm.console;


/**
 * 系统参数门面类
 * 
 * @author 王东
 * 
 */
 interface ParameterFaceInter {
	
	/**
	 * 绝对路径的参数标示符
	 */
	public final String PARA_REALPATH= "[REALPATH]";
	
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
}
