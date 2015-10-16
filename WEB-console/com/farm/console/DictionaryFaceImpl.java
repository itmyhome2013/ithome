package com.farm.console;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.farm.console.prisist.domain.AloneDictionaryEntity;
import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.server.contain.DictionaryEntityManagerInter;
import com.farm.console.server.contain.DictionaryTypeManagerInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;

class DictionaryFaceImpl implements DictionaryFaceInter {
	private DictionaryEntityManagerInter entityUmi;
	private DictionaryTypeManagerInter typeUmi;

	/**
	 * 将一个pageDomain的List集合转换为map
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, String> getMapKeyValueByListEntity(
			List<Map<String, Object>> list) {
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator
				.hasNext();) {
			Map<String, Object> entityMap = iterator.next();
			map.put(entityMap.get("entitytype".toUpperCase()).toString(),
					entityMap.get("name".toUpperCase()).toString());
		}
		return map;
	}

	@Override
	public Map<String, String> findTitleForIndex(String index) {
		return getMapKeyValueByListEntity(findTitleForIndeHasSort(index));
	}

	public DictionaryEntityManagerInter getEntityUmi() {
		return entityUmi;
	}

	public void setEntityUmi(DictionaryEntityManagerInter entityUmi) {
		this.entityUmi = entityUmi;
	}

	public DictionaryTypeManagerInter getTypeUmi() {
		return typeUmi;
	}

	public void setTypeUmi(DictionaryTypeManagerInter typeUmi) {
		this.typeUmi = typeUmi;
	}

	private static Map<String, List<Map<String, Object>>> dictinaryMap = null;

	@Override
	public List<Map<String, Object>> findTitleForIndeHasSort(String index) {
		if (dictinaryMap != null) {
			// 先从缓存中取
			List<Map<String, Object>> re = dictinaryMap.get(index);
			if (re != null) {
				return re;
			}
		}
		DataQuery query = DataQuery
				.getInstance(
						"1",
						"b.entitytype as entitytype,b.name as name",
						"alone_dictionary_entity a "
								+ "inner join alone_dictionary_type b on a.id=b.entity");
		query.addRule(new DBRule("a.entityindex", index, "="));
		query.addSort(new DBSort("b.sort", "ASC"));
		query.setPagesize(100);
		try {
			return query.search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean loadAllDicToCache() {
		if (dictinaryMap == null) {
			dictinaryMap = new HashMap<String, List<Map<String, Object>>>();
		}
		List<AloneDictionaryEntity> list = entityUmi.getAllEntity();
		boolean ok = true;
		for (AloneDictionaryEntity entity : list) {
			try {
				DataQuery query = DataQuery
						.getInstance(
								"1",
								"b.entitytype as entitytype,b.name as name",
								"alone_dictionary_entity a "
										+ "inner join alone_dictionary_type b on a.id=b.entity");
				query.addRule(new DBRule("a.entityindex", entity
						.getEntityindex(), "="));
				query.addSort(new DBSort("b.sort", "ASC"));
				query.setPagesize(100);

				dictinaryMap.put(entity.getEntityindex(), query.search()
						.getResultList());
			} catch (Exception e) {
				ok = false;
			}
		}
		return ok;
	}

	@Override
	public AloneDictionaryType getTypeByTypeId(String typeId) {
		return typeUmi.getEntity(typeId);
	}

}
