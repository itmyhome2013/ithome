package com.farm.web.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.farm.core.config.AppConfig;
import com.farm.web.spring.BeanFactory;
import com.farm.web.task.impl.ServerAUTHInit;
import com.farm.web.task.impl.ServerAppInit;
import com.farm.web.task.impl.ServerLicenceInit;

public class SysInit extends HttpServlet {

	/**
	 * 任务集合
	 */
	private  static List<ServletInitJobInter> list = new ArrayList<ServletInitJobInter>();
	private static final long serialVersionUID = 1L;
	// 配置系统所有默认启动任务

	public SysInit() {
		super();
	}

	private final Logger log = Logger.getLogger(this.getClass());

	public void destroy() {
		super.destroy();
	}

	public void init() throws ServletException {
		list= ((TaskListInter)BeanFactory.getBean("startServerTasksId")).getTasks();
		log.info(AppConfig.getString("config.sys.title") + "--系统准备运行"
				+ list.size() + "项");
		int n = 0;
		try {
			for (Iterator<ServletInitJobInter> iterator = list.iterator(); iterator
					.hasNext();) {
				n++;
				ServletInitJobInter name = (ServletInitJobInter) iterator
						.next();
				name.execute(this.getServletContext());
			}
		} catch (Exception e) {
			log.error("第" + n + "项任务启动失败：" + e.getMessage());
		}
	}
}
