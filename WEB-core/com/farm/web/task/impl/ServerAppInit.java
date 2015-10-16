package com.farm.web.task.impl;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
import com.farm.core.config.AppConfig;
import com.farm.web.spring.BeanFactory;
import com.farm.web.task.ServletInitJobInter;

public class ServerAppInit implements ServletInitJobInter {
	private final static Logger log = Logger.getLogger(ServerAppInit.class);

	@Override
	public void execute(ServletContext context) {
		log.info(AppConfig.getString("config.sys.title") + "--加载系统插件... ...");
		BeanFactory.getBean("dataSource");
		FarmManager.instance().setRealPath(
				context.getRealPath(""));
	}
}
