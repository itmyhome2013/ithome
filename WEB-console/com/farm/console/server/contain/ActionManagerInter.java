package com.farm.console.server.contain;

import java.util.Map;


import com.farm.console.contain.exception.HaveReferenceException;
import com.farm.console.prisist.domain.AloneAction;
import com.farm.console.prisist.domain.AloneUser;

public interface ActionManagerInter {

	public void deleteEntity(String entity) throws HaveReferenceException;

	public AloneAction editEntity(AloneAction entity);

	public AloneAction getEntity(String id);

	public Map<String, AloneAction> getAllEntity();

	public Map<String, AloneAction> getAllcheckEntity();

	public AloneAction insertEntity(AloneAction entity);

	public AloneAction insertEntity(AloneAction entity, AloneUser currentUser);

	public AloneAction editEntity(AloneAction entity, AloneUser currentUser);

}
