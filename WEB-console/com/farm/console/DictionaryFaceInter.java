package com.farm.console;

import java.util.List;
import java.util.Map;

import com.farm.console.prisist.domain.AloneDictionaryType;

/**数据字典的门面类
 * @author 王东
 * @version 20141015 增加获得树的类型对象由类型ID
 *
 */
interface DictionaryFaceInter {
	/** 通过字段名获得数据字典的键值对儿
	 * @param index 字段
	 * @return map<类型,类型title>
	 */
	public Map<String, String> findTitleForIndex(String index);
	
	/** 通过字段名获得数据字典的键值对儿 有序
	 * @param index 字段
	 * @return String[类型,类型title,sort]
	 */
	public List<Map<String, Object>> findTitleForIndeHasSort(String index);
	
	/**
	 * 将所有数据字典加载到内存中
	 */
	public boolean loadAllDicToCache() ;
	
	/**由分类ID获得一个分类实体
	 * @param typeId 分类的ID
	 * @return 返回实体对象
	 */
	public AloneDictionaryType getTypeByTypeId(String typeId);
}
