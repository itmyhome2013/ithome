package com.farm.doc.server;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.farm.doc.dao.FarmDocDaoInter;
import com.farm.doc.dao.FarmDocenjoyDaoInter;
import com.farm.doc.dao.FarmDocfileDaoInter;
import com.farm.doc.dao.FarmDocgroupDaoInter;
import com.farm.doc.dao.FarmDocmessageDaoInter;
import com.farm.doc.dao.FarmDocruninfoDaoInter;
import com.farm.doc.dao.FarmDocruninfoDetailDaoInter;
import com.farm.doc.dao.FarmDoctextDaoInter;
import com.farm.doc.dao.FarmDoctypeDaoInter;
import com.farm.doc.dao.FarmRfDoctextfileDaoInter;
import com.farm.doc.dao.FarmRfDoctypeDaoInter;
import com.farm.doc.domain.FarmDoc;
import com.farm.doc.domain.FarmDocenjoy;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.domain.FarmDocruninfo;
import com.farm.doc.domain.FarmDoctext;
import com.farm.doc.domain.FarmDoctype;
import com.farm.doc.domain.FarmRfDoctextfile;
import com.farm.doc.domain.FarmRfDoctype;
import com.farm.doc.exception.CanNoDeleteException;
import com.farm.doc.exception.CanNoReadException;
import com.farm.doc.exception.CanNoWriteException;
import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.commons.DocumentConfig;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.util.web.FarmproHotnum;
import com.farm.util.web.WebVisitBuff;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;

/**
 * 文档管理
 * 
 * @author MAC_wd
 */
public class FarmDocManagerImpl implements FarmDocManagerInter {
	private FarmDocDaoInter farmDocDao;
	private FarmDocfileDaoInter farmDocfileDao;
	private FarmDoctextDaoInter farmDoctextDao;
	private FarmRfDoctextfileDaoInter farmRfDoctextfileDao;
	private FarmRfDoctypeDaoInter farmRfDoctypeDao;
	private FarmDoctypeDaoInter farmDoctypeDao;
	private FarmDocmessageDaoInter farmDocmessageDao;
	private FarmDocruninfoDaoInter farmDocruninfoDao;
	private FarmDocenjoyDaoInter farmDocenjoyDao;
	private FarmDocOperateRightInter farmDocOperate;
	private FarmDocgroupDaoInter farmDocgroupDao;
	private FarmDocruninfoDetailDaoInter farmDocruninfoDetailDao;

	public FarmDoctypeDaoInter getFarmDoctypeDao() {
		return farmDoctypeDao;
	}

	public void setFarmDoctypeDao(FarmDoctypeDaoInter farmDoctypeDao) {
		this.farmDoctypeDao = farmDoctypeDao;
	}

	// ----------------------------------------------------------------------------------
	public FarmDocDaoInter getfarmDocDao() {
		return farmDocDao;
	}

	public void setfarmDocDao(FarmDocDaoInter dao) {
		this.farmDocDao = dao;
	}

	public FarmDocDaoInter getFarmDocDao() {
		return farmDocDao;
	}

	public void setFarmDocDao(FarmDocDaoInter farmDocDao) {
		this.farmDocDao = farmDocDao;
	}

	public FarmDocfileDaoInter getFarmDocfileDao() {
		return farmDocfileDao;
	}

	public void setFarmDocfileDao(FarmDocfileDaoInter farmDocfileDao) {
		this.farmDocfileDao = farmDocfileDao;
	}

	public FarmDoctextDaoInter getFarmDoctextDao() {
		return farmDoctextDao;
	}

	public void setFarmDoctextDao(FarmDoctextDaoInter farmDoctextDao) {
		this.farmDoctextDao = farmDoctextDao;
	}

	public FarmRfDoctextfileDaoInter getFarmRfDoctextfileDao() {
		return farmRfDoctextfileDao;
	}

	public void setFarmRfDoctextfileDao(
			FarmRfDoctextfileDaoInter farmRfDoctextfileDao) {
		this.farmRfDoctextfileDao = farmRfDoctextfileDao;
	}

	public FarmRfDoctypeDaoInter getFarmRfDoctypeDao() {
		return farmRfDoctypeDao;
	}

	public void setFarmRfDoctypeDao(FarmRfDoctypeDaoInter farmRfDoctypeDao) {
		this.farmRfDoctypeDao = farmRfDoctypeDao;
	}

	public FarmDocmessageDaoInter getFarmDocmessageDao() {
		return farmDocmessageDao;
	}

	public FarmDocgroupDaoInter getFarmDocgroupDao() {
		return farmDocgroupDao;
	}

	public void setFarmDocgroupDao(FarmDocgroupDaoInter farmDocgroupDao) {
		this.farmDocgroupDao = farmDocgroupDao;
	}

	public FarmDocruninfoDetailDaoInter getFarmDocruninfoDetailDao() {
		return farmDocruninfoDetailDao;
	}

	public void setFarmDocruninfoDetailDao(
			FarmDocruninfoDetailDaoInter farmDocruninfoDetailDao) {
		this.farmDocruninfoDetailDao = farmDocruninfoDetailDao;
	}

	public void setFarmDocmessageDao(FarmDocmessageDaoInter farmDocmessageDao) {
		this.farmDocmessageDao = farmDocmessageDao;
	}

	public FarmDocruninfoDaoInter getFarmDocruninfoDao() {
		return farmDocruninfoDao;
	}

	public void setFarmDocruninfoDao(FarmDocruninfoDaoInter farmDocruninfoDao) {
		this.farmDocruninfoDao = farmDocruninfoDao;
	}

	public FarmDocenjoyDaoInter getFarmDocenjoyDao() {
		return farmDocenjoyDao;
	}

	public void setFarmDocenjoyDao(FarmDocenjoyDaoInter farmDocenjoyDao) {
		this.farmDocenjoyDao = farmDocenjoyDao;
	}

	private static final Logger log = Logger
			.getLogger(FarmDocManagerImpl.class);

	public FarmDoc createDoc(FarmDoc entity, AloneUser user) {
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setCuser(user.getId());
		entity.setEuser(user.getId());
		entity.setCusername(user.getName());
		entity.setEusername(user.getName());
		// entity.setWritepop("");
		// entity.setReadpop("1");
		entity.setPubtime(entity.getPubtime().replaceAll("-", "").replaceAll(
				":", "").replaceAll(" ", ""));
		// 保存用量信息
		FarmDocruninfo runinfo = new FarmDocruninfo();
		{
			runinfo.setHotnum(0);
			runinfo.setLastvtime(TimeTool.getTimeDate14());
			runinfo.setPraiseno(0);
			runinfo.setPraiseyes(0);
			runinfo.setVisitnum(0);
			runinfo.setAnsweringnum(0);
			runinfo.setEvaluate(0);
			runinfo = farmDocruninfoDao.insertEntity(runinfo);
			entity.setRuninfoid(runinfo.getId());
		}

		// 先保存text
		{
			if (entity.getDocdescribe() == null) {
				String html = FarmDocFiles.HtmlRemoveTag(entity.getTexts()
						.getText1());
				entity.setDocdescribe(html.length() > 250 ? html.substring(0,
						250) : html);
			}
			entity.getTexts().setPstate("1");// 1在用内容。2.版本存档
			entity.getTexts().setPcontent("CREAT");
			entity.setTexts(farmDoctextDao.insertEntity(entity.getTexts()));
			entity.setTextid(entity.getTexts().getId());
			entity = farmDocDao.insertEntity(entity);
		}
		// 保存分类信息
		{
			for (FarmDoctype type : entity.getTypes()) {
				farmRfDoctypeDao.insertEntity(new FarmRfDoctype(type.getId(),
						entity.getId()));
			}

		}
		entity = farmDocDao.insertEntity(entity);
		// 保存关联附件信息（中间表）
		{
			List<String> files = FarmDocFiles.getFilesId(entity.getTexts()
					.getText1());
			for (String id : files) {
				farmRfDoctextfileDao.insertEntity(new FarmRfDoctextfile(entity
						.getId(), id));
				this.submitFile(id);
			}
		}

		return entity;
	}

	public FarmDoc editDocByUser(FarmDoc entity, String editNote, AloneUser user)
			throws CanNoWriteException {
		FarmDoc entity2 = farmDocDao.getEntity(entity.getId());
		if (!farmDocOperate.isWrite(user, entity2)) {
			throw new CanNoWriteException();
		}
		FarmDoc doc = editDoc(entity, editNote, user);
		return doc;
	}

	public FarmDoc editDoc(FarmDoc entity, String editNote, AloneUser user) {
		FarmDoc entity2 = farmDocDao.getEntity(entity.getId());
		entity2.setTitle(entity.getTitle());
		String html = FarmDocFiles.HtmlRemoveTag(entity.getTexts().getText1());
		entity2.setDocdescribe(html.length() > 250 ? html.substring(0, 250)
				: html);
		entity2.setAuthor(entity.getAuthor());
		entity2.setPubtime(entity.getPubtime());
		entity2.setDomtype(entity.getDomtype());
		entity2.setShorttitle(entity.getShorttitle());
		entity2.setTagkey(entity.getTagkey());
		entity2.setSource(entity.getSource());
		entity2.setPubtime(entity.getPubtime().replaceAll(" ", "").replaceAll(
				"-", "").replaceAll(":", ""));
		entity2.setTopleve(entity.getTopleve());
		entity2.setImgid(entity.getImgid());
		entity2.setState(entity.getState());
		entity2.setPcontent(entity.getPcontent());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		initDoc(entity2, user);
		farmDocDao.editEntity(entity2);
		{// 直接更新，处理text和版本信息,
			FarmDoctext text = farmDoctextDao.getEntity(entity2.getTextid());
			try {
				FarmDoctext histext = (FarmDoctext) BeanUtils.cloneBean(text);
				histext.setId(null);
				histext.setPstate("2");
				histext.setDocid(entity2.getId());
				farmDoctextDao.insertEntity(histext);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			text.setText1(entity.getTexts().getText1());
			text.setCtime(TimeTool.getTimeDate14());
			text.setEtime(TimeTool.getTimeDate14());
			text.setCuser(user.getId());
			text.setPcontent(editNote);
			text.setCusername(user.getName());
			text.setEuser(user.getId());
			text.setEusername(user.getName());
			farmDoctextDao.editEntity(text);
		}
		List<String> files = FarmDocFiles.getFilesId(entity.getTexts()
				.getText1());
		for (String id : files) {
			// 删除掉重复的附件
			List<DBRule> listRules = new ArrayList<DBRule>();
			listRules.add(new DBRule("FILEID", id, "="));
			listRules.add(new DBRule("DOCID", entity2.getId(), "="));
			farmRfDoctextfileDao.deleteEntitys(listRules);
			farmRfDoctextfileDao.insertEntity(new FarmRfDoctextfile(entity2
					.getId(), id));
			// 处理附件
			this.submitFile(id);
		}
		return entity2;
	}

	public void deleteDoc(String entity, AloneUser user)
			throws CanNoDeleteException {
		FarmDoc doc = farmDocDao.getEntity(entity);
		if (!farmDocOperate.isDel(user, doc)) {
			throw new CanNoDeleteException();
		}
		FarmDoctext text = farmDoctextDao.getEntity(doc.getTextid());
		// 删除收藏
		List<DBRule> joylist = new ArrayList<DBRule>();
		joylist.add(new DBRule("DOCID", doc.getId(), "="));
		farmDocenjoyDao.deleteEntitys(joylist);
		// 删除分类中间表
		List<DBRule> rulesDelType = new ArrayList<DBRule>();
		rulesDelType.add(new DBRule("DOCID", doc.getId(), "="));
		farmRfDoctypeDao.deleteEntitys(rulesDelType);
		// 删除文档
		farmDocDao.deleteEntity(farmDocDao.getEntity(entity));
		// 删除附件中间表
		List<DBRule> rulesDelFile = new ArrayList<DBRule>();
		rulesDelFile.add(new DBRule("DOCID", text.getId(), "="));
		List<FarmRfDoctextfile> files = farmRfDoctextfileDao
				.selectEntitys(rulesDelFile);
		for (FarmRfDoctextfile node : files) {
			this.delFile(node.getFileid(), user);
		}
		// 删除中间表文档和附件表
		List<DBRule> rulesDelText = new ArrayList<DBRule>();
		rulesDelText.add(new DBRule("DOCID", doc.getId(), "="));
		farmRfDoctextfileDao.deleteEntitys(rulesDelText);
		// 删除附件
		if (doc.getImgid() != null && doc.getImgid().trim().length() > 0) {
			farmDocfileDao.deleteEntity(farmDocfileDao
					.getEntity(doc.getImgid()));
		}
		// 删除用量明细
		{
			List<DBRule> rulesDelruninfoDetail = new ArrayList<DBRule>();
			rulesDelruninfoDetail.add(new DBRule("RUNINFOID", doc
					.getRuninfoid(), "="));
			farmDocruninfoDetailDao.deleteEntitys(rulesDelruninfoDetail);
		}
		// 删除用量信息
		farmDocruninfoDao.deleteEntity(farmDocruninfoDao.getEntity(doc
				.getRuninfoid()));
		// 删除大文本信息
		farmDoctextDao.deleteEntity(text);
	}

	@Override
	public DataQuery createSimpleDocQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"FARM_DOC  A LEFT JOIN FARM_RF_DOCTYPE B ON B.DOCID =A.ID LEFT JOIN FARM_DOCTYPE C ON C.ID=B.TYPEID",
						"A.ID AS ID,A.TITLE AS TITLE,A.AUTHOR AS AUTHOR,A.PUBTIME AS PUBTIME,A.DOMTYPE AS DOMTYPE,A.SHORTTITLE AS SHORTTITLE,A.TAGKEY AS TAGKEY,A.STATE AS STATE ");
		return dbQuery;
	}

	@Override
	public FarmDoc getDoc(String id) {
		if (id == null) {
			return null;
		}
		FarmDoc doc = farmDocDao.getEntity(id);
		doc.setTexts(farmDoctextDao.getEntity(doc.getTextid()));
		doc.setTypes(farmRfDoctypeDao.getDocType(id));
		if (doc.getTypes().size() > 0) {
			doc.setCurrenttypes(this.getTypeAllParent(doc.getTypes().get(0)
					.getId()));
		}
		doc.setRuninfo(farmDocruninfoDao.getEntity(doc.getRuninfoid()));
		if (doc.getTagkey() != null) {
			String tags = doc.getTagkey();
			String[] tags1 = tags.trim().replaceAll("，", ",").replaceAll("、",
					",").split(",");
			doc.setTags(Arrays.asList(tags1));
		}
		if (doc.getDocgroupid() != null) {
			doc.setGroup(farmDocgroupDao.getEntity(doc.getDocgroupid()));
			doc.getGroup().setImgurl(getFileURL(doc.getGroup().getGroupimg()));
		}
		// 处理附件
		List<FarmDocfile> files = farmDocfileDao.getEntityByDocId(id);
		for (FarmDocfile file : files) {
			file.setUrl(this.getFileURL(file.getId()));
		}
		doc.setFiles(files);
		return doc;
	}

	@Override
	public FarmDoc getDoc(String id, AloneUser user) throws CanNoReadException {
		FarmDoc docbean = farmDocDao.getEntity(id);
		if (!farmDocOperate.isRead(user, docbean)) {
			throw new CanNoReadException();
		}
		FarmDoc doc = getDoc(id);
		return doc;
	}
	
	@Override
	public FarmDocfile getFarmDocfile(String id) {
		FarmDocfile farmDocfile = farmDocfileDao.getEntity(id);
		
		return farmDocfile;
	}

	@Override
	public FarmDoc getDocOnlyBean(String id) {
		return farmDocDao.getEntity(id);
	}

	@Override
	public String saveFile(File file, String type, String title,
			AloneUser user) {
		String exName = FarmDocFiles.getExName(title);
		/*if (exName.trim().toUpperCase().replace(".", "").equals("ZIP")) {
			type = FILE_TYPE.RESOURCE_ZIP;
		}*/
		FarmDocfile docfile = new FarmDocfile(FarmDocFiles.generateDir(), UUID
				.randomUUID().toString().replaceAll("-", ""), type,
				title, file.getName(), TimeTool.getTimeDate14(), TimeTool
						.getTimeDate14(), user.getName(), user.getId(), user
						.getName(), user.getId(), "0", null, exName, Float
						.valueOf(String.valueOf(file.length())));
		if (user == null || user.getName() == null) {
			docfile.setCusername("none");
			docfile.setEusername("none");
		}
		FarmDocFiles.copyFile(file,title, DocumentConfig.getString("config.doc.dir")
				+ docfile.getDir());
		docfile = farmDocfileDao.insertEntity(docfile);

		return docfile.getId();
	}

	@Override
	public String getFileURL(String fileid) {
		String url = DocumentConfig.getString("config.doc.download.url")
				+ fileid;
		return url;
	}

	@Override
	public FarmDocfile getFile(String fileid) {
		FarmDocfile file = farmDocfileDao.getEntity(fileid);
		if (file == null) {
			return null;
		}
		file.setFile(new File(DocumentConfig.getString("config.doc.dir")
				+ File.separator + file.getDir() + file.getFilename()));
		// 如果文件是大小是0的话就刷新文件大小
		if (file.getLen() == 0) {
			file.setLen(Float.valueOf(String.valueOf(file.getFile().length())));
			if (file.getLen() == 0) {
				file.setLen(Float.valueOf(-1));
			}
			farmDocfileDao.editEntity(file);
		}
		return file;
	}

	@Override
	public void submitFile(String fileId) {
		FarmDocfile file = farmDocfileDao.getEntity(fileId);
		file.setPstate("1");
		file.setEtime(TimeTool.getTimeDate14());
		farmDocfileDao.editEntity(file);
	}

	@Override
	public void cancelFile(String fileId) {
		FarmDocfile file = farmDocfileDao.getEntity(fileId);
		if (file == null) {
			return;
		}
		file.setPstate("0");
		farmDocfileDao.editEntity(file);
	}

	@Override
	public void delFile(String fileId, AloneUser user) {
		FarmDocfile docfile = farmDocfileDao.getEntity(fileId);
		if (docfile == null) {
			return;
		}
		File file = this.getFile(fileId).getFile();
		farmDocfileDao.deleteEntity(docfile);
		if (file.exists()) {
			if (file.delete()) {
				log.info("删除成功！");
			} else {
				log.info("删除失败！");
			}
		}
	}

	public FarmDoctype insertType(FarmDoctype entity, AloneUser user) {
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setCuser(user.getId());
		entity.setEuser(user.getId());
		entity.setCusername(user.getName());
		entity.setEusername(user.getName());
		if (entity.getParentid() == null
				|| entity.getParentid().trim().length() <= 0) {
			entity.setParentid("NONE");
		}
		entity.setTreecode("NONE");
		entity = farmDoctypeDao.insertEntity(entity);
		if (entity.getParentid().equals("NONE")) {
			entity.setTreecode(entity.getId());
		} else {
			entity.setTreecode(farmDoctypeDao.getEntity(entity.getParentid())
					.getTreecode()
					+ entity.getId());
		}
		return farmDoctypeDao.insertEntity(entity);
	}

	public FarmDoctype editType(FarmDoctype entity, AloneUser user) {
		FarmDoctype entity2 = farmDoctypeDao.getEntity(entity.getId());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setName(entity.getName());
		entity2.setTypemod(entity.getTypemod());
		entity2.setContentmod(entity.getContentmod());
		entity2.setSort(entity.getSort());
		entity2.setType(entity.getType());
		entity2.setMetatitle(entity.getMetatitle());
		entity2.setMetakey(entity.getMetakey());
		entity2.setMetacontent(entity.getMetacontent());
		entity2.setLinkurl(entity.getLinkurl());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		farmDoctypeDao.editEntity(entity2);
		return entity2;
	}

	public void deleteType(String typeId, AloneUser user) {
		// 删除分类中间表
		List<DBRule> rulesDelType = new ArrayList<DBRule>();
		rulesDelType.add(new DBRule("TYPEID", typeId, "="));
		farmRfDoctypeDao.deleteEntitys(rulesDelType);
		farmDoctypeDao.deleteEntity(farmDoctypeDao.getEntity(typeId));
	}

	public FarmDoctype getType(String id) {
		if (id == null) {
			return null;
		}
		return farmDoctypeDao.getEntity(id);
	}

	@Override
	public DataQuery createSimpleTypeQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"farm_doctype a LEFT JOIN farm_doctype b ON a.PARENTID=b.id",
						"a.ID AS id,a.NAME AS NAME,a.TYPEMOD AS TYPEMOD,a.CONTENTMOD AS CONTENTMOD,a.SORT AS SORT,a.TYPE AS TYPE, a.METATITLE AS METATITLE,a.METAKEY AS METAKEY,a.METACONTENT AS METACONTENT,a.LINKURL AS LINKURL,a.PCONTENT AS PCONTENT,a.PSTATE AS PSTATE");
		return dbQuery;
	}

	@Override
	public FarmDoc initDoc(FarmDoc doc, AloneUser currentUser) {
		if (doc.getAuthor() == null) {
			doc.setAuthor(currentUser.getName());
		}
		if (doc.getCtime() == null) {
			doc.setCtime(TimeTool.getTimeDate14());
		}
		if (doc.getCuser() == null) {
			doc.setCuser(currentUser.getId());
		}
		if (doc.getCusername() == null) {
			doc.setCusername(currentUser.getName());
		}
		if (doc.getDocdescribe() == null) {
			doc.setDocdescribe(null);
		}
		if (doc.getDomtype() == null) {
			doc.setDomtype("1");
		}
		if (doc.getDocgroupid() != null && doc.getDocgroupid().length() < 32) {
			doc.setDocgroupid(null);
		}
		if (doc.getEtime() == null) {
			doc.setEtime(TimeTool.getTimeDate14());
		}
		if (doc.getEuser() == null) {
			doc.setEuser(currentUser.getId());
		}
		if (doc.getEusername() == null) {
			doc.setEusername(currentUser.getName());
		}
		if (doc.getImgid() == null) {
		} else {
			if (doc.getImgid().trim().length() <= 0) {
				doc.setImgid(null);
			}
		}
		if (doc.getFiles() == null) {
			doc.setFiles(null);
		}
		if (doc.getPcontent() == null) {
			doc.setPcontent(null);
		}
		if (doc.getPubtime() == null) {
			doc.setPubtime(TimeTool.getTimeDate14());
		}
		if (doc.getReadpop() == null) {
			doc.setReadpop("1");
		}
		if (doc.getShorttitle() == null) {
			doc.setShorttitle(null);
		}
		if (doc.getSource() == null) {
			doc.setSource("原创");
		}
		if (doc.getState() == null) {
			doc.setState("1");
		}
		if (doc.getTagkey() == null) {
			doc.setTagkey(null);
		}
		if (doc.getTextid() == null) {
			doc.setTextid(null);
		}
		if (doc.getTexts() == null) {
			// doc.setTexts(null);
		}
		if (doc.getTitle() == null) {
			doc.setTitle(null);
		}
		if (doc.getTopleve() == null) {
			doc.setTopleve(10);
		}
		if (doc.getTypes() == null) {
			doc.setTypes(null);
		}
		if (doc.getWritepop() == null) {
			doc.setWritepop("1");
		}
		return doc;
	}

	@Override
	public FarmDoc createDoc(FarmDoc entity, FarmDoctype type,
			AloneUser currentUser) {
		List<FarmDoctype> list = new ArrayList<FarmDoctype>();
		list.add(type);
		entity.setTypes(list);
		return this.createDoc(entity, currentUser);
	}

	@Override
	public FarmDoc createDoc(FarmDoc entity, List<FarmDoctype> type,
			AloneUser currentUser) {
		entity.setTypes(type);
		return this.createDoc(entity, currentUser);
	}

	@Override
	public void updateDocTypeOnlyOne(String docid, String typeId) {
		List<DBRule> list = new ArrayList<DBRule>();
		list.add(new DBRule("DOCID", docid, "="));
		farmRfDoctypeDao.deleteEntitys(list);
		farmRfDoctypeDao.insertEntity(new FarmRfDoctype(typeId, docid));
	}

	@Override
	public FarmDocfile openFile(String exname, String content, AloneUser user) {
		FILE_TYPE type = FILE_TYPE.OHTER;
		String filename = UUID.randomUUID().toString();
		FarmDocfile docfile = new FarmDocfile(FarmDocFiles.generateDir(), UUID
				.randomUUID().toString().replaceAll("-", ""), type.getValue(),
				filename + "." + exname, filename + ".tmp", TimeTool
						.getTimeDate14(), TimeTool.getTimeDate14(), user
						.getName(), user.getId(), user.getName(), user.getId(),
				"0", content, exname, Float.valueOf(0));

		File file = new File(DocumentConfig.getString("config.doc.dir")
				+ docfile.getDir() + File.separator + docfile.getFilename());
		try {
			if (!file.createNewFile()) {
				throw new RuntimeException("文件创建失败!");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		docfile = farmDocfileDao.insertEntity(docfile);
		docfile.setFile(file);
		return docfile;
	}

	@Override
	public List<FarmDoctype> getTypeAllParent(String typeid) {
		String id = typeid;
		List<FarmDoctype> types = new ArrayList<FarmDoctype>();
		while (id != null) {
			FarmDoctype centity = farmDoctypeDao.getEntity(id);
			if (centity == null || centity.getParentid() == null
					|| centity.getParentid().trim().length() <= 0) {
				id = null;
			} else {
				id = centity.getParentid();

			}
			if (centity != null) {
				types.add(centity);
			}
		}
		Collections.reverse(types);
		return types;
	}

	// WHERE PSTATE='2' AND DOCID=''
	@Override
	public DataQuery getDocVersions(String docId) {
		DataQuery dbQuery = DataQuery.getInstance("1",
				"ID,ETIME,CUSERNAME,DOCID,PCONTENT,PSTATE", "farm_doctext");
		dbQuery.addRule(new DBRule("PSTATE", "1", "!="));
		dbQuery.addRule(new DBRule("DOCID", docId, "="));
		dbQuery.setPagesize(100);
		dbQuery.addSort(new DBSort("CTIME", "DESC"));
		return dbQuery;
	}

	@Override
	public FarmDoc getDocVersion(String textId, AloneUser currentUser) {
		if (textId == null) {
			return null;
		}
		FarmDoctext text = farmDoctextDao.getEntity(textId);
		FarmDoc doc = farmDocDao.getEntity(text.getDocid());
		if (doc.getReadpop().equals("0")
				&& !doc.getCuser().equals(currentUser.getId())) {
			throw new RuntimeException("没有阅读权限");
		}
		doc.setTexts(text);
		doc.setTypes(farmRfDoctypeDao.getDocType(doc.getId()));
		if (doc.getTypes().size() > 0) {
			doc.setCurrenttypes(this.getTypeAllParent(doc.getTypes().get(0)
					.getId()));
		}
		if (doc.getTagkey() != null) {
			String tags = doc.getTagkey();
			String[] tags1 = tags.trim().replaceAll("，", ",").replaceAll("、",
					",").split(",");
			doc.setTags(Arrays.asList(tags1));
		}
		return doc;
	}

	public FarmDocOperateRightInter getFarmDocOperate() {
		return farmDocOperate;
	}

	public void setFarmDocOperate(FarmDocOperateRightInter farmDocOperate) {
		this.farmDocOperate = farmDocOperate;
	}

	@Override
	public FarmDoc editDocRight(FarmDoc entity, AloneUser currentUser) {
		FarmDoc entity2 = farmDocDao.getEntity(entity.getId());
		entity2.setWritepop(entity.getWritepop());
		entity2.setReadpop(entity.getReadpop());
		farmDocDao.editEntity(entity2);
		return entity2;
	}

	@Override
	public void addFileForDoc(String docid, String fileId, AloneUser user) {
		farmRfDoctextfileDao.insertEntity(new FarmRfDoctextfile(docid, fileId));
	}

	@Override
	public void delFileForDoc(String docid, String fileId, AloneUser user) {
		List<DBRule> list = new ArrayList<DBRule>();
		list.add(new DBRule("FILEID", fileId, "="));
		list.add(new DBRule("DOCID", docid, "="));
		farmRfDoctextfileDao.deleteEntitys(list);
	}

	@Override
	public List<FarmDocfile> getAllFileForDoc(String docid) {
		List<FarmDocfile> refiles = farmDocfileDao.getEntityByDocId(docid);
		for (FarmDocfile file : refiles) {
			file = getFile(file.getId());
		}
		return refiles;
	}

	@Override
	public List<FarmDocfile> getAllTypeFileForDoc(String docid, String exname) {
		List<FarmDocfile> refiles = farmDocfileDao.getEntityByDocId(docid);
		List<FarmDocfile> newrefiles = new ArrayList<FarmDocfile>();
		for (FarmDocfile file : refiles) {
			if (file.getExname().toUpperCase().equals(exname.toUpperCase())) {
				file = getFile(file.getId());
				newrefiles.add(file);
			}
		}
		return newrefiles;
	}

	@Override
	public boolean containFileByDoc(String docid, String fileId) {
		List<FarmDocfile> list = farmDocfileDao.getEntityByDocId(docid);
		for (FarmDocfile node : list) {
			if (node.getId().equals(fileId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void delAllFileForDoc(String docid, String exname,
			AloneUser aloneUser) {
		List<FarmDocfile> refiles = farmDocfileDao.getEntityByDocId(docid);
		for (FarmDocfile file : refiles) {
			if (file.getExname().toUpperCase().equals(exname.toUpperCase())) {
				delFileForDoc(docid, file.getId(), aloneUser);
			}
		}

	}

	@Override
	public void flyDoc(String id, AloneUser currentUser) {
		FarmDoc doc = getDocOnlyBean(id);
		if (farmDocOperate.isDel(currentUser, doc)) {
			flyDoc(doc);
		} else {
			throw new RuntimeException("您没有此权限");
		}
	}

	@Override
	public void flyDoc(FarmDoc doc) {
		doc.setDocgroupid(null);
		doc.setReadpop(POP_TYPE.PUB.getValue());
		doc.setWritepop(POP_TYPE.PUB.getValue());
		farmDocDao.editEntity(doc);
	}

}
