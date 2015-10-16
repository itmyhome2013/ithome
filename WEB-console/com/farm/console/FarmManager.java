package com.farm.console;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.core.auth.AuthenticateInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.adapter.AuthAdapterInter;
import com.farm.web.spring.BeanFactory;

public class FarmManager implements FarmBaseManagerInter {
	private static final FarmManager alonemanager = new FarmManager();

	public static FarmManager instance() {
		return alonemanager;
	}

	@Override
	public AuthenticateInter getAuthUtil() {
		return (AuthenticateInter) BeanFactory.getBean("AUTH_ALONE_COMMON");
	}

	private DictionaryFaceInter getDictionaryFace() {
		return (DictionaryFaceInter) BeanFactory
				.getBean("DICTIONARY_MANAGER_COMMON");
	}

	private ParameterFaceInter getParameterFace() {
		return (ParameterFaceInter) BeanFactory
				.getBean("PARAMETER_FACE_COMMON");
	}

	@Override
	public Map<String, String> getAllAction() {
		Map<String, String> map = new HashMap<String, String>();
		DataQuery query = DataQuery.getInstance("1", "id,url", "alone_action");
		query.setPagesize(1000);
		query.addRule(new DBRule("state", "1", "="));
		query.addRule(new DBRule("ischeck", "1", "="));
		DataResult result = null;
		try {
			result = query.search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Map<String, Object> node : result.getResultList()) {
			String url = node.get("URL").toString();
			String id = node.get("ID").toString();
			map.put(url, id);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> findDicTitleForIndeHasSort(String index) {
		return getDictionaryFace().findTitleForIndeHasSort(index);
	}

	@Override
	public Map<String, String> findDicTitleForIndex(String index) {
		return getDictionaryFace().findTitleForIndex(index);
	}

	@Override
	public String getConfigValue(String key) {
		return getParameterFace().getConfigValue(key);
	}

	@Override
	public String getRealPath() {
		return getParameterFace().getRealPath();
	}

	@Override
	public boolean initConfig() {
		return getParameterFace().initConfig();
	}

	@Override
	public boolean refreshConfigMap() {
		return getParameterFace().refreshConfigMap();
	}

	@Override
	public void setRealPath(String str) {
		getParameterFace().setRealPath(str);
	}

	@Override
	public AuthAdapterInter getAuthManager() {
		return (AuthAdapterInter) BeanFactory.getBean("AuthAdapterId");
	}

	@Override
	public AloneDictionaryType getDictionaryType(String typeId) {
		return getDictionaryFace().getTypeByTypeId(typeId);
	}
}
