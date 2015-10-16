package com.farm.console.server.contain;

import com.farm.console.prisist.domain.AloneMenuRolegroup;


public interface MenuRolegroupManagerInter {
	

	public void deleteEntity(String entity);

	public void editEntity(AloneMenuRolegroup entity);

	public AloneMenuRolegroup getEntity(String id);

	public void insertEntity(AloneMenuRolegroup entity);

}
