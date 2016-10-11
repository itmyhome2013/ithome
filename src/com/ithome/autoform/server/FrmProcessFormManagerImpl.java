package com.ithome.autoform.server;

import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.sql.query.DataQuery;
import org.apache.log4j.Logger;
import com.ithome.autoform.dao.FrmProcessFormDaoInter;
import com.ithome.autoform.domain.FrmProcessForm;

/**
 * 表基础信息
 * 
 * @author hxx
 */
public class FrmProcessFormManagerImpl implements FrmProcessFormManagerInter {
	private FrmProcessFormDaoInter frmProcessFormDao;

	private static final Logger log = Logger
			.getLogger(FrmProcessFormManagerImpl.class);

	public FrmProcessForm insertFrmProcessFormEntity(FrmProcessForm entity, AloneUser user) {
		//entity.setState("1");
		//entity.setCretime(TimeTool.getTimeDate14());
		if("2".equals(entity.getFromtype())){ //如果表单类型为Word
			entity.setFormurl(entity.getWordurl());
			entity.setFormtablename(entity.getWord());
		}
		
		FrmProcessForm frmTable = frmProcessFormDao.insertEntity(entity);
		
		//创建表
		//TableTools.generateTable(entity.getEnname());
		
		return frmTable;
	}

	public FrmProcessForm editFrmProcessFormEntity(FrmProcessForm entity, AloneUser user) {
		FrmProcessForm entity2 = frmProcessFormDao.getEntity(entity.getPcsfromcfgid());
		entity2.setTaskkey(entity.getTaskkey());
		entity2.setFormname(entity.getFormname());
		entity2.setFromtype(entity.getFromtype());
		entity2.setIsrequired(entity.getIsrequired());
		entity2.setFormtableid(entity.getFormtableid());
		
		if("2".equals(entity.getFromtype())){ //如果表单类型为Word
			entity2.setFormurl(entity.getWordurl());
			entity2.setFormtablename(entity.getWord());
		}else{
			entity2.setFormtablename(entity.getFormtablename());
			entity2.setFormurl(entity.getFormurl());
		}
		
		//entity2.setWordurl(entity.getWordurl());
		//entity2.setWord(entity.getWord());
		entity2.setIsdisable(entity.getIsdisable());
		entity2.setTaskname(entity.getTaskname());

		frmProcessFormDao.editEntity(entity2);
		
		//更改表名
		//TableTools.renameTable(oldTableName, entity.getEnname());
		
		return entity2;
	}
	
	@Override
	public DataQuery createFrmProcessFormSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"ACT_EX_PCSFROMCFG",
						"'1',PCSFROMCFGID as ID,TASKKEY,FORMNAME,FROMTYPE,ISREQUIRED,FORMTABLENAME,FORMURL,ISDISABLE,TASKNAME");
						//.addRule(new DBRule("STATE", "1", "="));
		return dbQuery;
	}

	public void deleteFrmTableEntity(String id, String state, AloneUser user) {
		FrmProcessForm entity2 = frmProcessFormDao.getEntity(id);
		
		if("0".equals(state)){  //禁用 
			//entity2.setState("0");
			frmProcessFormDao.editEntity(entity2);
		}else if("2".equals(state)){ //删除
			//删除表
			//TableTools.delTable(entity2.getEnname());
			
			/*List<DBRule> rules = new ArrayList<DBRule>();
			rules.add(new DBRule("TABLEID",entity2.getId(),"="));
			frmProcessFormDao.deleteEntitys(rules); //删除字段信息
			frmProcessFormDao.deleteEntity(frmTableDao.getEntity(id));*/
		}
	}

	
	@Override
	public void deleteFrmProcessFormEntityByLogic(String id) {
		FrmProcessForm entity2 = frmProcessFormDao.getEntity(id);
		//entity2.setState("0");
		frmProcessFormDao.editEntity(entity2);
	}
	
	public FrmProcessForm getFrmProcessFormEntity(String id) {
		if (id == null) {
			return null;
		}
		return frmProcessFormDao.getEntity(id);
	}

	public FrmProcessFormDaoInter getFrmProcessFormDao() {
		return frmProcessFormDao;
	}

	public void setFrmProcessFormDao(FrmProcessFormDaoInter frmProcessFormDao) {
		this.frmProcessFormDao = frmProcessFormDao;
	}

	@Override
	public void deleteFrmProcessFormEntity(String id) {
		FrmProcessForm entity2 = frmProcessFormDao.getEntity(id);
		frmProcessFormDao.deleteEntity(entity2);
		
	}

	// ----------------------------------------------------------------------------------

	

}
