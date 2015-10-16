package com.farm.console.server.contain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


import com.farm.console.prisist.dao.DictionaryEntityDaoInter;
import com.farm.console.prisist.dao.DictionaryTypeDaoInter;
import com.farm.console.prisist.domain.AloneDictionaryEntity;
import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;

public class DictionaryEntityManagerImpl implements
		DictionaryEntityManagerInter {
	private DictionaryEntityDaoInter dictionaryentityDao;
	private DictionaryTypeDaoInter dictionarytypeDao;

	public void deleteEntity(String entity, AloneUser user) {
		List<AloneDictionaryType> list = dictionarytypeDao
				.getListByEntityId(entity);
		for (Iterator<AloneDictionaryType> iterator = list.iterator(); iterator
				.hasNext();) {
			AloneDictionaryType aloneDictionaryType = (AloneDictionaryType) iterator
					.next();
			dictionarytypeDao.deleteEntity(aloneDictionaryType);
		}
		dictionaryentityDao.deleteEntity(dictionaryentityDao.getEntity(entity));
	}

	public AloneDictionaryEntity editEntity(AloneDictionaryEntity entity,
			AloneUser user) {
		if (validateIsRepeatKey(entity.getEntityindex(), entity.getId())) {
			throw new IllegalArgumentException("字典KEY已经存在");
		}
		AloneDictionaryEntity entity2 = getEntity(entity.getId());
		entity2.setComments(entity.getComments());
		entity2.setName(entity.getName().trim());
		entity2.setEntityindex(entity.getEntityindex().trim());// 必须去空格
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setMuser(user.getId());
		entity2.setType(entity.getType());
		dictionaryentityDao.editEntity(entity2);
		return entity2;
	}

	public List<AloneDictionaryEntity> getAllEntity() {
		return dictionaryentityDao.getAllEntity();
	}

	public int getAllListNum() {
		return 0;
	}

	public AloneDictionaryEntity getEntity(String id) {
		if (id == null)
			return null;
		return dictionaryentityDao.getEntity(id);
	}

	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity entity,
			AloneUser user) {
		if (validateIsRepeatKey(entity.getEntityindex(), null)) {
			throw new IllegalArgumentException("字典KEY已经存在");
		}
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setCuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setMuser(user.getId());
		entity.setState("1");
		entity.setName(entity.getName().trim());
		entity.setEntityindex(entity.getEntityindex().trim());// 必须去空格
		return dictionaryentityDao.insertEntity(entity);
	}


	public DictionaryEntityDaoInter getdictionaryentityDao() {
		return dictionaryentityDao;
	}

	@Override
	public boolean validateIsRepeatKey(String key, String exId) {
		List<AloneDictionaryEntity> list = null;
		if (exId == null || exId.trim().equals("")) {
			list = dictionaryentityDao.findEntityByKey(key.trim());
		} else {
			list = dictionaryentityDao.findEntityByKey(key.trim(), exId.trim());
		}
		return list.size() > 0;
	}

	@Override
	public void editComments(String id) {
		if (id == null || id.equals("")) {
			return;
		}

		AloneDictionaryEntity dicEntity = dictionaryentityDao.getEntity(id);
		// if(dicEntity.getComments()!=null&&!dicEntity.getComments().equals("")){
		// return;
		// }

		List<AloneDictionaryType> dictypeList = dictionarytypeDao
				.getListByEntityId(id);
		if (dictypeList.isEmpty()) {
			return;
		}

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("{");
		for (AloneDictionaryType itDictype : dictypeList) {
			String name = itDictype.getName();
			String entitytype = itDictype.getEntitytype();
			sBuilder.append(name).append(":").append(entitytype).append(", ");
		}
		sBuilder.delete(sBuilder.length() - 2, sBuilder.length());
		sBuilder.append("}");
		if(sBuilder.toString()!=null&&sBuilder.toString().length()>100){
			dicEntity.setComments(sBuilder.toString().substring(0, 100)+"...");
		}else{
			dicEntity.setComments(sBuilder.toString());
		}
		dictionaryentityDao.editEntity(dicEntity);
	}

	public void setdictionaryentityDao(
			DictionaryEntityDaoInter dictionaryentityDao) {
		this.dictionaryentityDao = dictionaryentityDao;
	}

	public DictionaryTypeDaoInter getDictionarytypeDao() {
		return dictionarytypeDao;
	}

	public void setDictionarytypeDao(DictionaryTypeDaoInter dictionarytypeDao) {
		this.dictionarytypeDao = dictionarytypeDao;
	}
}
