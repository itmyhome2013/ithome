package com.farm.web.spring;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.InputSource;

import com.farm.core.config.AppConfig;
import com.farm.web.local.ConfDirHandle;
import com.farm.web.local.ConfHandleInter;
import com.opensymphony.xwork2.util.DomHelper;

public class BeanFactory {
	private static String PATHS_ONE = "conf/applicationContext.xml";
	private static String[] PATHS;
	private static ApplicationContext appContext;
	private final Logger log = Logger.getLogger(BeanFactory.class);

	public static Object getBean(String beanId) {
		if (appContext == null) {
			initPaths();
			appContext = new ClassPathXmlApplicationContext(PATHS);
		}
		return appContext.getBean(beanId);
	}

	@SuppressWarnings("unchecked")
	public static Class getBeanClass(String beanId) {
		if (appContext == null) {
			initPaths();
			appContext = new ClassPathXmlApplicationContext(PATHS);
		}
		return appContext.getType(beanId);
	}

	public static void initPaths() {
		List<String> list = new ArrayList<String>();
		list.add(PATHS_ONE);
		// 用户插件包索引*************************开始
		String UserConfPack = AppConfig.getString("config.plugin.dir");
		if (UserConfPack != null && (UserConfPack.trim().length() > 0)) {
			ConfDirHandle.findDirForConf(Thread.currentThread()
					.getContextClassLoader().getResource("").getPath(),
					UserConfPack.split(","), new ConfHandleInter() {
						@Override
						public void execute(Object para, File file) {
							Logger log = Logger.getLogger(BeanFactory.class);
							List<String> list = (List<String>) para;
							String strFileName = file.getAbsolutePath()
									.toLowerCase();
							if (strFileName.endsWith("context.xml")) {
								String path = file.getPath();
								int n = path.indexOf("classes");
								path = path.substring(n + "classes".length()
										+ 1);
								list.add(path);
								log.info(AppConfig.getString("config.sys.title")
										+ "--发现模块:" + path);
							}
						}
					}, list);
		}
		// 用户插件包索引*************************结束
		String[] strings = new String[list.size()];
		PATHS = list.toArray(strings);
	}
}
