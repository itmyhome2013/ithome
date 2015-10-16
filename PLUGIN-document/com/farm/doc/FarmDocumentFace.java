package com.farm.doc;

import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.FarmDocmessageManagerInter;
import com.farm.web.spring.BeanFactory;

/**
 * 文档插件的服务face(逐步完善该类)
 * 
 * @author wangdong 2014
 * 
 */
public class FarmDocumentFace {
	private FarmDocumentFace() {
	}

	private static FarmDocumentFace obj;
	private final static FarmDocManagerInter docImpl = (FarmDocManagerInter) BeanFactory
			.getBean("farm_docProxyId");
	private final static FarmDocmessageManagerInter msgIMP = (FarmDocmessageManagerInter) BeanFactory
			.getBean("farm_docmessageProxyId");

	public static FarmDocManagerInter getDocimpl() {
		return docImpl;
	}

	public static FarmDocmessageManagerInter getMegimpl() {
		return msgIMP;
	}

	/**
	 * 获得服务实例
	 * 
	 * @return
	 */
	public static FarmDocumentFace inctance() {
		if (obj == null) {
			obj = new FarmDocumentFace();
		}
		return obj;
	}

}
