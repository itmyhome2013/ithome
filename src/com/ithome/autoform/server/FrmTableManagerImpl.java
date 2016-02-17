package com.ithome.autoform.server;


import java.util.ArrayList;
import java.util.List;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.autoform.TableTools;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.ithome.autoform.dao.FrmFieldDaoInter;
import com.ithome.autoform.dao.FrmTableDaoInter;
import com.ithome.autoform.domain.FrmTable;

/**
 * 表基础信息
 * 
 * @author hxx
 */
public class FrmTableManagerImpl implements FrmTableManagerInter {
	private FrmTableDaoInter frmTableDao;
	private FrmFieldDaoInter frmFieldDao;

	private static final Logger log = Logger
			.getLogger(FrmTableManagerImpl.class);

	public FrmTable insertFrmTableEntity(FrmTable entity, AloneUser user) {
		entity.setState("1");
		entity.setCretime(TimeTool.getTimeDate14());
		FrmTable frmTable = frmTableDao.insertEntity(entity);
		
		//创建表
		TableTools.generateTable(entity.getEnname());
		
		return frmTable;
	}

	public FrmTable editFrmTableEntity(FrmTable entity, AloneUser user) {
		String oldTableName = frmTableDao.getEntity(entity.getId()).getEnname();
		FrmTable entity2 = frmTableDao.getEntity(entity.getId());
		entity2.setCnname(entity.getCnname());
		entity2.setEnname(entity.getEnname());
		entity2.setFlag(entity.getFlag());
		entity2.setOwner(entity.getOwner());
		entity2.setSort(entity.getSort());
		entity2.setType(entity.getType());
		entity2.setUrl(entity.getUrl());

		frmTableDao.editEntity(entity2);
		
		//更改表名
		TableTools.renameTable(oldTableName, entity.getEnname());
		
		return entity2;
	}
	
	@Override
	public DataQuery createFrmTableSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"FRM_TABLE",
						"'1',ID,ENNAME,CNNAME,STATE,TYPE,URL,SORT,OWNER,FLAG,CRETIME")
						.addSort(new DBSort("STATE","DESC"))
						.addSort(new DBSort("ENNAME","ASC"));
						//.addRule(new DBRule("STATE", "1", "="));
		return dbQuery;
	}

	public void deleteFrmTableEntity(String id, String state, AloneUser user) {
		FrmTable entity2 = frmTableDao.getEntity(id);
		
		if("0".equals(state)){  //禁用 
			entity2.setState("0");
			frmTableDao.editEntity(entity2);
		}else if("2".equals(state)){ //删除
			//删除表
			TableTools.delTable(entity2.getEnname());
			
			List<DBRule> rules = new ArrayList<DBRule>();
			rules.add(new DBRule("TABLEID",entity2.getId(),"="));
			frmFieldDao.deleteEntitys(rules); //删除字段信息
			frmTableDao.deleteEntity(frmTableDao.getEntity(id));
		}
	}

	@Override
	public void deleteFrmTableEntityByLogic(String id) {
		FrmTable entity2 = frmTableDao.getEntity(id);
		entity2.setState("0");
		frmTableDao.editEntity(entity2);
	}
	
	public FrmTable getFrmTableEntity(String id) {
		if (id == null) {
			return null;
		}
		return frmTableDao.getEntity(id);
	}

	// ----------------------------------------------------------------------------------

	public FrmTableDaoInter getFrmTableDao() {
		return frmTableDao;
	}

	public void setFrmTableDao(FrmTableDaoInter frmTableDao) {
		this.frmTableDao = frmTableDao;
	}

	public FrmFieldDaoInter getFrmFieldDao() {
		return frmFieldDao;
	}

	public void setFrmFieldDao(FrmFieldDaoInter frmFieldDao) {
		this.frmFieldDao = frmFieldDao;
	}

}
