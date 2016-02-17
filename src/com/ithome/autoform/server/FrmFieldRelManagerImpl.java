package com.ithome.autoform.server;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.ithome.autoform.dao.FrmFieldRelDaoInter;
import com.ithome.autoform.domain.FrmFieldRel;
import com.ithome.autoform.server.FrmFieldRelManagerInter;
import com.farm.core.sql.query.DataQuery;
 /**
 * 实体管理类功能说明
 *
 * @author 作者:自动生成
 * @version 日期(用日期代替版本号)+版本备注
 */ 
public class FrmFieldRelManagerImpl implements FrmFieldRelManagerInter{
	private FrmFieldRelDaoInter  frmFieldRelDao;
	private static final Logger log = Logger.getLogger(FrmFieldRelManagerImpl.class);
	public FrmFieldRel insertFrmFieldRelEntity(FrmFieldRel entity,AloneUser user) {
		//entity.setCuser(user.getId());
		//entity.setCtime(TimeTool.getTimeDate14());
		//entity.setCusername(user.getName());
		//entity.setEuser(user.getId()); 
		//entity.setEusername(user.getName());
		//entity.setEtime(TimeTool.getTimeDate14());
		//entity.setPstate("1");
		return frmFieldRelDao.insertEntity(entity);
	}
	public FrmFieldRel editFrmFieldRelEntity(FrmFieldRel entity,AloneUser user) {
		FrmFieldRel entity2 = frmFieldRelDao.getEntity(entity.getId());
		//entity2.setEuser(user.getId());
		//entity2.setEusername(user.getName());
		//entity2.setEtime(TimeTool.getTimeDate14()); 
   		entity2.setId(entity.getId());
   		entity2.setFieldid(entity.getFieldid());
   		entity2.setFieldname(entity.getFieldname());
   		entity2.setTablename(entity.getTablename());
   		entity2.setCondition(entity.getCondition());
		frmFieldRelDao.editEntity(entity2);
		return entity2;
	}
	public void deleteFrmFieldRelEntity(String entity,AloneUser user) {
		frmFieldRelDao.deleteEntity(frmFieldRelDao.getEntity(entity));
	}
	public FrmFieldRel getFrmFieldRelEntity(String id) {
		if (id == null){return null;}
		return frmFieldRelDao.getEntity(id);
	}
	@Override
	public DataQuery createFrmFieldRelSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"FRM_FIELD_REL",
						"id,ID,FIELDID,FIELDNAME,TABLENAME,CONDITION");
		return dbQuery;
	}
	//----------------------------------------------------------------------------------
	public FrmFieldRelDaoInter getfrmFieldRelDao() {
		return frmFieldRelDao;
	}

	public void setfrmFieldRelDao(FrmFieldRelDaoInter dao) {
		this.frmFieldRelDao = dao;
	}

}
