package com.farm.doc.server;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.doc.domain.FarmDoc;

/**
 * 文档权限操作接口
 * 
 * @author Administrator
 * 
 */
public interface FarmDocOperateRightInter {

	/**
	 * 是否允许读取文档
	 * 
	 * @param user
	 * @param doc 传入docBean即可
	 * @return
	 */
	public boolean isRead(AloneUser user, FarmDoc doc);

	/**
	 * 是否允许未登陆用户及所有人读取文档
	 * 
	 * @param doc 传入docBean即可
	 * @return
	 */
	public boolean isAllUserRead(FarmDoc doc);

	/**
	 * 是否允许编辑文档
	 * 
	 * @param user
	 * @param doc 传入docBean即可
	 * @return
	 */
	public boolean isWrite(AloneUser user, FarmDoc doc);

	/**
	 * 是否允许删除文档
	 * 
	 * @param user
	 * @param doc 传入docBean即可
	 * @return
	 */
	public boolean isDel(AloneUser user, FarmDoc doc);

}
