package com.farm.console.server.contain;

import com.farm.console.prisist.dao.AloneApplogDaoInter;
import com.farm.console.prisist.domain.AloneApplog;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;

/**
 * 系统日志
 * 
 * @author MAC_wd
 */
public class AloneApplogManagerImpl implements AloneApplogManagerInter {
	private AloneApplogDaoInter aloneApplogDao;

	public AloneApplog insertEntity(AloneApplog entity, AloneUser user) {
		// entity.setCtime(TimeTool.getTimeDate12());
		// entity.setEtime(TimeTool.getTimeDate12());
		// entity.setCuser(user.getId());
		// entity.setEuser(user.getId());
		return aloneApplogDao.insertEntity(entity);
	}

	public AloneApplog editEntity(AloneApplog entity, AloneUser user) {
		AloneApplog entity2 = aloneApplogDao.getEntity(entity.getId());
		// entity2.setEtime(TimeTool.getTimeDate12());
		// entity2.setEuser(user.getId());
		entity2.setCtime(entity.getCtime());
		entity2.setDescribes(entity.getDescribes());
		entity2.setAppuser(entity.getAppuser());
		entity2.setLevel(entity.getLevel());
		entity2.setMethod(entity.getMethod());
		entity2.setClassname(entity.getClassname());
		aloneApplogDao.editEntity(entity2);
		return entity2;
	}

	public void deleteEntity(String entity, AloneUser user) {
		aloneApplogDao.deleteEntity(aloneApplogDao.getEntity(entity));
	}

	public AloneApplog getEntity(String id) {
		if (id == null) {
			return null;
		}
		return aloneApplogDao.getEntity(id);
	}

	// ----------------------------------------------------------------------------------
	public AloneApplogDaoInter getaloneApplogDao() {
		return aloneApplogDao;
	}

	public void setaloneApplogDao(AloneApplogDaoInter dao) {
		this.aloneApplogDao = dao;
	}

	@Override
	public AloneApplog log(String describes, String appuser, String level,
			String method, String classname, String ip) {
		return aloneApplogDao.insertEntity(new AloneApplog(TimeTool
				.getTimeDate14(), describes, appuser, level, method, classname,
				ip));
	}

}
