package com.farm.web.task.impl;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.farm.console.FarmManager;
import com.farm.core.config.AppConfig;
import com.farm.web.constant.FarmConstant;
import com.farm.web.task.ServletInitJobInter;

public class ServerAUTHInit implements ServletInitJobInter {
	private final static Logger log = Logger.getLogger(ServerAUTHInit.class);

	@Override
	public void execute(ServletContext context) {
		log.info(AppConfig.getString("config.sys.title")
				+ "--初始化基础数据... ...");
		FarmManager.instance().initConfig();
		context.setAttribute(FarmConstant.CONTEXT_ALLACTION, FarmManager
				.instance().getAllAction());
	}
}
