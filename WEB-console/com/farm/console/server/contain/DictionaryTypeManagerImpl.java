package com.farm.console.server.contain;

import java.util.List;
import java.util.Vector;


import com.farm.console.prisist.dao.DictionaryTypeDaoInter;
import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;

public class DictionaryTypeManagerImpl implements DictionaryTypeManagerInter {
	private DictionaryTypeDaoInter dictionarytypeDao;
	private DictionaryEntityManagerInter dicManager;
	private static final List<AloneDictionaryType> DictionaryTypeList = new Vector<AloneDictionaryType>();

	public String getConfigValue(String key) {
		return null;
	}

	public boolean initConfig() {
		return false;
	}

	public boolean refreshConfigMap() {
		return false;
	}

	public void deleteEntity(String entity) {
		dictionarytypeDao.deleteEntity(dictionarytypeDao.getEntity(entity));
	}

	@Override
	public void deleteEntity(String entity, AloneUser user) {
		AloneDictionaryType dictypeEntity = dictionarytypeDao.getEntity(entity);
		
		dictionarytypeDao.deleteEntityByTreecode(dictypeEntity.getId());//先删除
		dicManager.editComments(dictypeEntity.getEntity());//后编辑
	}

	public void editEntity(AloneDictionaryType entity) {
		dictionarytypeDao.editEntity(entity);
	}

	@Override
	public AloneDictionaryType editEntity(AloneDictionaryType entity,
			AloneUser user) {
		//修改字典类型实体
		AloneDictionaryType entity2 = getEntity(entity.getId());
		entity2.setName(entity.getName());
		entity2.setEntitytype(entity.getEntitytype());
		entity2.setSort(entity.getSort());
		entity2.setState(entity.getState());
		entity2.setComments(entity.getComments());
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setMuser(user.getName());
		dictionarytypeDao.editEntity(entity2);
		//修改数据字典备注的内容
		if(entity.getEntity()==null||entity.getEntity().equals("")){
			throw new RuntimeException("无法关联数据字典！");
		}
		dicManager.editComments(entity.getEntity());
		return entity2;
	}


	public List<AloneDictionaryType> getAllEntity() {
		return null;
	}

	public int getAllListNum() {
		return 0;
	}

	public AloneDictionaryType getEntity(String id) {
		if (id == null) {
			return null;
		}
		return dictionarytypeDao.getEntity(id);
	}

	@Override
	public AloneDictionaryType insertEntity(AloneDictionaryType entity,
			AloneUser user) {
		//插入字典类型实体
		if (entity.getParentid() == null
				|| entity.getParentid().trim().length() <= 0) {
			entity.setParentid("NONE");
		}
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setCuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setMuser(user.getId());
		entity.setState("1");
		dictionarytypeDao.insertEntity(entity);
		
		// 获取新增后的实体ID，并修改树索引码
		AloneDictionaryType fatherEntity = getEntity(entity.getParentid());
		if (fatherEntity == null) {
			entity.setTreecode(entity.getId());
		} else {
			entity.setTreecode(fatherEntity.getTreecode() + entity.getId());
		}
		dictionarytypeDao.editEntity(entity);
		
		//修改数据字典备注的内容
		if(entity.getEntity()==null||entity.getEntity().equals("")){
			throw new RuntimeException("无法关联数据字典！");
		}
		dicManager.editComments(entity.getEntity());
		return entity;
	}

	public static List<AloneDictionaryType> getdictionarytypeList() {
		return DictionaryTypeList;
	}

	public DictionaryTypeDaoInter getdictionarytypeDao() {
		return dictionarytypeDao;
	}

	public void setdictionarytypeDao(DictionaryTypeDaoInter dictionarytypeDao) {
		this.dictionarytypeDao = dictionarytypeDao;
	}

	public DictionaryEntityManagerInter getDicManager() {
		return dicManager;
	}

	public void setDicManager(DictionaryEntityManagerInter dicManager) {
		this.dicManager = dicManager;
	}

}
