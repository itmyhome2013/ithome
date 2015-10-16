package com.farm.console;

import java.util.HashMap;
import java.util.Map;


import com.farm.console.prisist.domain.AloneParameter;
import com.farm.console.server.contain.ParameterManagerInter;
import com.farm.core.auth.AuthenticateInter;


 class ParameterFaceImpl implements ParameterFaceInter {
	private AuthenticateInter auth;
	private static String RealPath;

	private ParameterManagerInter paraApi;

	private final static Map<String, AloneParameter> parameterMap = new HashMap<String, AloneParameter>();

	@Override
	public String getConfigValue(String key) {
		AloneParameter ap = parameterMap.get(key);
		String value = null;
		if (ap != null) {
			value = ap.getPvalue().replace(PARA_REALPATH, getRealPath());
			return value;
		}
		return null;
	}


	@Override
	public boolean initConfig() {
		parameterMap.clear();
		parameterMap.putAll(paraApi.getAllParameter());
		return true;
	}

	@Override
	public boolean refreshConfigMap() {
		initConfig();
		return true;
	}

	public ParameterManagerInter getParaApi() {
		return paraApi;
	}

	public void setParaApi(ParameterManagerInter paraApi) {
		this.paraApi = paraApi;
	}


	public AuthenticateInter getAuth() {
		return auth;
	}

	public void setAuth(AuthenticateInter auth) {
		this.auth = auth;
	}

	@Override
	public String getRealPath() {
		return RealPath;
	}

	@Override
	public void setRealPath(String str) {
		RealPath = str;
	}
}
