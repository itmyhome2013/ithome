package com.farm.console.server.contain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import com.farm.console.contain.exception.HaveIndexException;
import com.farm.console.prisist.dao.ParameterDaoInter;
import com.farm.console.prisist.domain.AloneParameter;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;

public class ParameterManagerImpl implements ParameterManagerInter {
	private ParameterDaoInter parameterDao;
	private static final List<AloneParameter> ParameterList = new Vector<AloneParameter>();

	public boolean initConfig() {
		return false;
	}

	public boolean refreshConfigMap() {
		return false;
	}

	public void deleteEntity(String entity, AloneUser user) {
		parameterDao.deleteEntity(parameterDao.getEntity(entity));
	}

	@Override
	public AloneParameter insertEntity(AloneParameter entity, String domain,
			AloneUser aloneUser) throws HaveIndexException {
		// 如果“参数键”重复，抛异常
		if (isRepeatKey(entity.getPkey())) {
			throw new RuntimeException("参数键：" + entity.getPkey() + "，已存在！");
		}

		// 如果参数类型选择的是“文本”，则“枚举规则“为空。出现的情况为：选择”枚举“，
		// 填入”枚举规则“，后又选择文本，”枚举规则“在页面看不见，但有值。
		if (entity.getVtype().equals("1")) {
			entity.setRules("");
		}

		// 新增实体
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setCuser(aloneUser.getId());
		entity.setMuser(aloneUser.getId());
		entity.setPvalue("无");// 默认没有值
		entity.setState("1");// 默认启用（暂无用）
		parameterDao.insertEntity(entity);
		return entity;
	}

	@Override
	public AloneParameter editEntity(AloneParameter entity, AloneUser aloneUser) {
		// 如果key被修改，并且key已存在，抛异常。
		AloneParameter entity2 = getEntity(entity.getId());
		if (!entity2.getPkey().equals(entity.getPkey())
				&& isRepeatKey(entity.getPkey())) {
			throw new RuntimeException("参数键：" + entity.getPkey() + "，已存在！");
		}

		// 如果参数类型选择的是“文本”，则“枚举规则“为空。为什么？参考insertEntity()方法
		if (entity.getVtype().equals("1")) {
			entity.setRules("");
		}

		// 修改实体
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setMuser(aloneUser.getId());
		entity2.setName(entity.getName());
		// entity2.setState(entity.getState());//暂无用
		entity2.setPkey(entity.getPkey());
		// entity2.setPvalue(entity.getPvalue());//系统参数管理页面编辑
		entity2.setRules(entity.getRules());
		entity2.setDomain(entity.getDomain());
		entity2.setComments(entity.getComments());
		entity2.setVtype(entity.getVtype());
		parameterDao.editEntity(entity2);

		parameterDao.editEntity(entity2);
		return entity2;
	}

	@Override
	public AloneParameter editSubmitByPValue(String paramId, String pValue, AloneUser aloneUser) {
		AloneParameter entity = getEntity(paramId);
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setMuser(aloneUser.getId());
		entity.setPvalue(pValue);
		parameterDao.editEntity(entity);
		return entity;
	}

	public boolean isRepeatKey(String paramKey, String excludeParamId) {
		List<AloneParameter> paramList = null;
		if (excludeParamId == null || excludeParamId.equals("")) {
			paramList = parameterDao.findListByKey(paramKey.trim());
		} else {
			paramList = parameterDao.findListByKeyAndExcludeParamId(paramKey
					.trim(), excludeParamId);
		}
		return paramList.size() > 0;
	}

	public boolean isRepeatKey(String paramKey) {
		return isRepeatKey(paramKey, null);
	}

	public AloneParameter getEntity(String id) {
		if (id == null) {
			return null;
		}
		return parameterDao.getEntity(id);
	}

	public static List<AloneParameter> getParameterList() {
		return ParameterList;
	}

	public ParameterDaoInter getParameterDao() {
		return parameterDao;
	}

	public void setParameterDao(ParameterDaoInter parameterDao) {
		this.parameterDao = parameterDao;
	}

	@Override
	public Map<String, AloneParameter> getAllParameter() {
		Map<String, AloneParameter> map = new HashMap<String, AloneParameter>();
		for (AloneParameter node : parameterDao.getAllEntity()) {
			map.put(node.getPkey(), node);
		}
		return map;
	}

	@SuppressWarnings("null")
	@Override
	public AloneParameter getParameterValueForUser(String key, String user) {
		List<AloneParameter> listEntity = null;
		if (listEntity.size() > 0) {
			return listEntity.get(0);
		} else {
			return null;
		}

	}

	@Override
	/**
	 * 返回格式：[{VTYPE=2, NAME=中文2, ID=402881eb396c880101396c8ab7ef0001, 
	 * 					RULES=早班:zhao,中班:zhong,夜班:ye, PVALUE=无, 
	 * 					ENUMVALUE=[
	 * 								[早班, zhao], 
	 * 								[中班, zhong], 
	 * 								[夜班, ye]]
	 * 			 }, 
	 * 			 {VTYPE=1, NAME=中文注释, ID=402881eb39676b6b013967b2c4bf0009, 
	 * 					RULES=null, PVALUE=none}, null, null, null, null, null, 
	 * 					null, null, null]
	 */
	public List<Map<String, Object>> getTransParamList(String domainType) {
		// 获取参数list
		String type = "";
		if (domainType.equals("1")) {
			type = "alone";
		} else if (domainType.equals("2")) {
			type = "app";
		}else if (domainType.equals("3")) {
			type = "ccs";
		}
		List<Map<String, Object>> list = parameterDao.getListByDomainType(type);

		// 遍历集合，如果参数类型（ALONE_PARAMETER.VTYPE）是枚举值，取出枚举值转换成list集合并放入map中
		for (Map<String, Object> map : list) {
			if (map.get("VTYPE").equals('2')) {// 如果是枚举值；数据库类型为“CHAR(1)”，hibernate返回的是字符
				ArrayList<List<String>> enumList = new ArrayList<List<String>>();

				for (String enumStr : (map.get("RULES") + "").split(",")) {
					ArrayList<String> kvList = new ArrayList<String>();
					for (String kvStr : enumStr.split(":")) {
						kvList.add(kvStr);
					}
					enumList.add(kvList);
				}
				map.put("ENUMVALUE", enumList);
			}
		}
		return list;
	}

	@Override
	public AloneParameter findEntityByKey(String paramKey) {
		return parameterDao.findEntityByKey(paramKey.trim());
	}
}
