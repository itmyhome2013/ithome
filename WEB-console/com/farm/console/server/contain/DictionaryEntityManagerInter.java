package com.farm.console.server.contain;

import java.util.List;

import com.farm.console.prisist.domain.AloneDictionaryEntity;
import com.farm.console.prisist.domain.AloneUser;


public interface DictionaryEntityManagerInter {

	public void deleteEntity(String entity,AloneUser user);

	public AloneDictionaryEntity editEntity(AloneDictionaryEntity  entity,AloneUser user);

	public AloneDictionaryEntity  getEntity(String id);

	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity  entity,AloneUser user);

	/**
	 * 验证key是否重复
	 * 
	 * @param key 字段（ENTITYINDEX）
	 * @param exId 要排除的ID
	 * @return
	 * @author liuchao
	 * @time 2012-12-20 下午05:36:09
	 */
	public boolean validateIsRepeatKey(String key, String exId);

	/**
	 * 修改备注。如果备注内容为空，则用“字典类型”的值填充备注。
	 * 			格式为json，例：{采煤:1, 掘进:2, 开拓:3, 机电:4}
	 * 
	 * @param id 数据字典ID 
	 * @author liuchao
	 * @time 2012-12-21 上午10:55:53
	 */
	public void editComments(String id);

	/**查询所有的数据字典对象
	 * @return
	 */
	public List<AloneDictionaryEntity> getAllEntity();
	
}
