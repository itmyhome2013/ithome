package com.ithome.autoform.server;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.ithome.autoform.dao.FrmSquDaoInter;
import com.ithome.autoform.domain.FrmSqu;
import com.ithome.autoform.server.FrmSquManagerInter;
import com.farm.core.sql.query.DataQuery;
 /**
 * 实体管理类功能说明
 *
 * @author 作者:自动生成
 * @version 日期(用日期代替版本号)+版本备注
 */ 
public class FrmSquManagerImpl implements FrmSquManagerInter{
	private FrmSquDaoInter  frmSquDao;
	private static final Logger log = Logger.getLogger(FrmSquManagerImpl.class);
	public FrmSqu insertFrmSquEntity(FrmSqu entity,AloneUser user) {
		//entity.setCuser(user.getId());
		//entity.setCtime(TimeTool.getTimeDate14());
		//entity.setCusername(user.getName());
		//entity.setEuser(user.getId()); 
		//entity.setEusername(user.getName());
		//entity.setEtime(TimeTool.getTimeDate14());
		//entity.setPstate("1");
		return frmSquDao.insertEntity(entity);
	}
	public FrmSqu editFrmSquEntity(FrmSqu entity,AloneUser user) {
		FrmSqu entity2 = frmSquDao.getEntity(entity.getId());
		//entity2.setEuser(user.getId());
		//entity2.setEusername(user.getName());
		//entity2.setEtime(TimeTool.getTimeDate14()); 
   		entity2.setId(entity.getId());
   		entity2.setType(entity.getType());
   		entity2.setNum(entity.getNum());
		frmSquDao.editEntity(entity2);
		return entity2;
	}
	public void deleteFrmSquEntity(String entity,AloneUser user) {
		frmSquDao.deleteEntity(frmSquDao.getEntity(entity));
	}
	public FrmSqu getFrmSquEntity(String id) {
		if (id == null){return null;}
		return frmSquDao.getEntity(id);
	}
	@Override
	public DataQuery createFrmSquSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"FRM_SQU",
						"id,ID,TYPE,NUM");
		return dbQuery;
	}
	//----------------------------------------------------------------------------------
	public FrmSquDaoInter getfrmSquDao() {
		return frmSquDao;
	}

	public void setfrmSquDao(FrmSquDaoInter dao) {
		this.frmSquDao = dao;
	}

}
