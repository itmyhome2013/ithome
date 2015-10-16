package com.farm.doc.server;

import java.io.File;
import java.util.List;

import com.farm.doc.domain.FarmDoc;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.domain.FarmDoctype;
import com.farm.doc.exception.CanNoDeleteException;
import com.farm.doc.exception.CanNoReadException;
import com.farm.doc.exception.CanNoWriteException;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;

/**
 * 文档管理
 * 
 * @author MAC_wd
 * 
 */
public interface FarmDocManagerInter {

	/**
	 * 附件类型
	 * 
	 * @author 王东
	 * 
	 */
	public enum FILE_TYPE {
		HTML_INNER_IMG("1"), RESOURCE_FILE("2"), RESOURCE_ZIP("3"), OHTER("0"), WEB_FILE(
				"4");
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		FILE_TYPE(String value) {
			this.value = value;
		}
	}

	/**
	 * 权限类型（阅读/编辑）
	 * 
	 * @author 王东
	 * 
	 */
	public enum POP_TYPE {
		/**
		 * 1公开
		 */
		PUB("1"), /**
		 * 0本人
		 */
		PRI("0"), /**
		 * 2小组
		 */
		DOCGROUP("2"), /**
		 * 3禁止编辑
		 */
		NONE("3");
		private String value;

		public String getValue() {
			return value;
		}

		/**
		 * 获得枚举对象
		 * 
		 * @param val
		 * @return
		 */
		public static POP_TYPE getEnum(String val) {
			if (val.equals("1")) {
				return POP_TYPE.PUB;
			}
			if (val.equals("3")) {
				return POP_TYPE.NONE;
			}
			if (val.equals("2")) {
				return POP_TYPE.DOCGROUP;
			}
			return POP_TYPE.PRI;
		}

		public void setValue(String value) {
			this.value = value;
		}

		POP_TYPE(String value) {
			this.value = value;
		}
	}

	/**
	 * 保存一个附件到系统中
	 * 
	 * @param file
	 * @param type
	 * @param title
	 * @return 附件ID
	 */
	public String saveFile(File file, String type, String title,
			AloneUser user);

	/**
	 * 获得一个分类的所有上层节点序列（包含该分类，有排序）
	 * 
	 * @param typeid
	 * @return
	 */
	public List<FarmDoctype> getTypeAllParent(String typeid);

	/**
	 *修改文档(不做权限控制)
	 * 
	 * @param entity
	 *            标题、发布时间、内容类型是必填 texts中的TEXT1中存放超文本内容
	 * @param editNote
	 *            修改时的注释
	 * @param user
	 *            操作用户
	 * @return
	 */
	public FarmDoc editDoc(FarmDoc entity, String editNote, AloneUser user);

	/**
	 *带权限的修改实体
	 * 
	 * @param entity
	 *            标题、发布时间、内容类型是必填 texts中的TEXT1中存放超文本内容
	 * @param editNote
	 *            修改备注(记录为啥修改)
	 * @param user
	 * @return
	 * @throws CanNoWriteException
	 */
	public FarmDoc editDocByUser(FarmDoc entity, String editNote, AloneUser user)
			throws CanNoWriteException;

	/**
	 *删除文档
	 * 
	 * @param entity
	 */
	public void deleteDoc(String entity, AloneUser user)
			throws CanNoDeleteException;

	/**
	 * 创建一个基本查询用来查询当前实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSimpleDocQuery(DataQuery query);

	/**
	 * 创建文档
	 * 
	 * @param entity
	 *            标题、发布时间、内容类型是必填 texts中的TEXT1中存放超文本内容
	 * @param currentUser
	 * @return
	 */
	public FarmDoc createDoc(FarmDoc entity, AloneUser currentUser);

	/**
	 * 创建文档
	 * 
	 * @param entity
	 *            标题、发布时间、内容类型是必填 texts中的TEXT1中存放超文本内容
	 * @param type
	 *            文档的分类
	 * @param currentUser
	 * @return
	 */
	public FarmDoc createDoc(FarmDoc entity, FarmDoctype type,
			AloneUser currentUser);

	/**
	 * 创建文档
	 * 
	 * @param entity
	 *            标题、发布时间、内容类型是必填 texts中的TEXT1中存放超文本内容
	 * @param type
	 *            文档的分类
	 * @param currentUser
	 * @return
	 */
	public FarmDoc createDoc(FarmDoc entity, List<FarmDoctype> type,
			AloneUser currentUser);

	/**
	 * 获取文档数据
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public FarmDoc getDoc(String id);

	/**
	 * 获取文档数据 判断权限
	 * 
	 * @param id
	 * @param user
	 *            阅读用户
	 * @return
	 */
	public FarmDoc getDoc(String id, AloneUser user) throws CanNoReadException;
	
	/**
	 * 获得文件实体
	 * @param id
	 * @return
	 */
	public FarmDocfile getFarmDocfile(String id);

	/**
	 * 获取文档数据
	 * 
	 * @param id
	 * @return
	 */
	public FarmDoc getDocOnlyBean(String id);

	/**
	 * 由文件id获得下载链接
	 * 
	 * @param fileid
	 *            文件id
	 * @return
	 */
	public String getFileURL(String fileid);

	/**
	 * 由文件id获得文件对象
	 * 
	 * @param fileid
	 * @return
	 */
	public FarmDocfile getFile(String fileid);

	/**
	 * 将文件状态改为提交状态，否则为临时状态
	 * 
	 * @param fileId
	 */
	public void submitFile(String fileId);

	/**
	 *将文件设置为临时状态
	 * 
	 * @param fileId
	 */
	public void cancelFile(String fileId);

	/**
	 * 删除一个文件
	 * 
	 * @param fileId
	 */
	public void delFile(String fileId, AloneUser user);

	/**
	 *新增实体
	 * 
	 * @param entity
	 */
	public FarmDoctype insertType(FarmDoctype entity, AloneUser user);

	/**
	 *修改分类
	 * 
	 * @param entity
	 */
	public FarmDoctype editType(FarmDoctype entity, AloneUser user);

	/**
	 *删除分类实体
	 * 
	 * @param entity
	 */
	public void deleteType(String entity, AloneUser user);

	/**
	 *获得分类实体
	 * 
	 * @param id
	 * @return
	 */
	public FarmDoctype getType(String id);

	/**
	 * 创建一个基本查询用来查询当前分类实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSimpleTypeQuery(DataQuery query);

	/**
	 * 为文档对象赋初始值
	 * 
	 * @param doc
	 *            文档对象
	 * @param currentUser
	 *            当前用户
	 */
	public FarmDoc initDoc(FarmDoc doc, AloneUser currentUser);

	/**
	 * 更新文档的分类（唯一分类）将会清空doc的其它分类
	 * 
	 * @param docid
	 * @param typeId
	 */
	public void updateDocTypeOnlyOne(String docid, String typeId);

	/**
	 * 创建并使用一个新的文件（并附带一个已经存在的File对象）
	 * 
	 * @param exname
	 *            扩展名
	 * @param content
	 *            备注
	 * @param user
	 * @return
	 */
	public FarmDocfile openFile(String exname, String content, AloneUser user);

	/**
	 * 获得文档的版本信息
	 * 
	 * @param docId
	 * @return ID,ETIME,CUSERNAME,DOCID,PCONTENT
	 */
	public DataQuery getDocVersions(String docId);

	/**
	 * 获得文档版本信息
	 * 
	 * @param textId
	 * @param currentUser
	 * @return
	 */
	public FarmDoc getDocVersion(String textId, AloneUser currentUser);

	/**
	 * 修改文档权限
	 * 
	 * @param entity
	 *            存入的WRITEPOP，READPOP字段将被更新
	 * @param currentUser
	 * @return
	 */
	public FarmDoc editDocRight(FarmDoc entity, AloneUser currentUser);

	/**
	 * 为文档添加一个附件
	 * 
	 * @param docid
	 * @param fileId
	 * @param user
	 */
	public void addFileForDoc(String docid, String fileId, AloneUser user);

	/**
	 * 为文档删除一个附件
	 * 
	 * @param docid
	 * @param fileId
	 * @param user
	 */
	public void delFileForDoc(String docid, String fileId, AloneUser user);

	/**
	 * 获得文档的所有附件
	 * 
	 * @param docid
	 */
	public List<FarmDocfile> getAllFileForDoc(String docid);

	/**
	 * 获得文档的某类型的所有附件
	 * 
	 * @param docid
	 * @param exname
	 *            扩展名如.doc
	 * @return
	 */
	public List<FarmDocfile> getAllTypeFileForDoc(String docid, String exname);

	/**
	 * 文档是否包含一个附件
	 * 
	 * @param id
	 * @param zipfileId
	 * @return
	 */
	public boolean containFileByDoc(String docid, String fileId);

	/**
	 * 删除一个文档的所有某类型附件
	 * 
	 * @param docid
	 *            文档id
	 * @param exname
	 *            扩展名如.doc
	 * @param aloneUser
	 */
	public void delAllFileForDoc(String docid, String exname,
			AloneUser aloneUser);

	/**公开文档（将该文档开放阅读和编辑权限，同时如果是小组文档将删除小组所有权）
	 * @param id
	 * @param currentUser
	 */
	public void flyDoc(String docID, AloneUser currentUser);
	/**公开文档（不做权限验证）
	 * @param id
	 * @param currentUser
	 */
	public void flyDoc(FarmDoc doc);
}