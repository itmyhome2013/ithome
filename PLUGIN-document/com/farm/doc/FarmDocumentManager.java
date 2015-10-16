package com.farm.doc;

import java.io.File;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.FarmDocManagerInter.FILE_TYPE;
import com.farm.web.spring.BeanFactory;

/**
 * 文档插件的服务face(逐步完善该类)
 * 
 * @author wangdong 2014
 * 
 */
public class FarmDocumentManager {
	private FarmDocumentManager() {
	}

	private static FarmDocumentManager obj;
	private final static FarmDocManagerInter docImpl = (FarmDocManagerInter) BeanFactory
			.getBean("farm_docProxyId");

	public static FarmDocManagerInter getDocimpl() {
		return docImpl;
	}

	/**
	 * 获得服务实例
	 * 
	 * @return
	 */
	public static FarmDocumentManager inctance() {
		if (obj == null) {
			obj = new FarmDocumentManager();
		}
		return obj;
	}

	
}
