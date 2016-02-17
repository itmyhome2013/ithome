package com.ithome.autoform.server;

import java.util.List;
import java.util.Map;

import com.farm.console.FarmManager;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.ithome.autoform.dao.FrmFieldDaoInter;
import com.ithome.autoform.dao.FrmTableDaoInter;
import com.ithome.autoform.domain.CompletedForm;
import com.ithome.autoform.domain.FrmField;
import com.ithome.autoform.domain.FrmTable;
import com.ithome.autoform.server.FrmFieldManagerInter;

import com.farm.core.autoform.FieldModel;
import com.farm.core.autoform.TableTools;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
 /**
 * 实体管理类功能说明
 *
 * @author 作者:自动生成
 * @version 日期(用日期代替版本号)+版本备注
 */ 
public class FrmFieldManagerImpl implements FrmFieldManagerInter{
	private FrmFieldDaoInter  frmFieldDao;
	private FrmTableDaoInter frmTableDao;

	private static final Logger log = Logger.getLogger(FrmFieldManagerImpl.class);
	
	public FrmField insertFrmFieldEntity(FrmField entity,AloneUser user) {
		entity.setCretime(TimeTool.getTimeDate14());
		
		//添加字段
		FrmTable frmTable = frmTableDao.getEntity(entity.getTableid());
		String fieldType = DataResult.runDictionarySingle(FarmManager.instance().findDicTitleForIndex("FILEDTYPE"), entity.getFiledtype());
		FieldModel model = new FieldModel(frmTable.getEnname(),entity.getEnname(),fieldType,entity.getLen(),entity.getIsnull());
		TableTools.addField(model);
		
		return frmFieldDao.insertEntity(entity);
	}
	
	public FrmField insertFrmOperateFieldEntity(Map<String,String> map,CompletedForm completedForm,AloneUser user) {
		
		String tableName = "";
		for(Map.Entry<String, String> entry : map.entrySet()){
			if("tableid".equals(entry.getKey())){
				FrmTable frmTable = frmTableDao.getEntity(entry.getValue());
				tableName = frmTable.getEnname();
			}
		}
		
		String dataid = TableTools.insertFrmForm(tableName,map);
		//TableTools.insertCompletedForm(dataid,completedForm);
		
		return null;
	}
	
	public FrmField editFrmOperateFieldEntity(Map<String,String> map,String completedformid,AloneUser user) {
		
		String tableName = "";
		for(Map.Entry<String, String> entry : map.entrySet()){
			if("tableid".equals(entry.getKey())){
				FrmTable frmTable = frmTableDao.getEntity(entry.getValue());
				tableName = frmTable.getEnname();
			}
		}
		
		TableTools.editFrmForm(tableName,map);
		//TableTools.updateCompletedForm(completedformid);
		
		return null;
	}
	
	public FrmField editFrmFieldEntity(FrmField entity,AloneUser user) {
		FrmField entity2 = frmFieldDao.getEntity(entity.getId());
		
		//同步修改数据库表字段
		FrmTable frmTable = frmTableDao.getEntity(entity.getTableid());
		TableTools.updateField(frmTable.getEnname(),entity2, entity);
		
   		entity2.setId(entity.getId());
   		entity2.setTableid(entity.getTableid());
   		entity2.setCnname(entity.getCnname());
   		entity2.setEnname(entity.getEnname());
   		entity2.setFiledtype(entity.getFiledtype());
   		entity2.setLen(entity.getLen());
   		entity2.setSort(entity.getSort());
   		entity2.setInputtype(entity.getInputtype());
   		entity2.setState(entity.getState());
   		entity2.setCretime(entity.getCretime());
   		entity2.setUpdtime(entity.getUpdtime());
   		entity2.setIsrel(entity.getIsrel());
   		entity2.setIsnull(entity.getIsnull());
   		entity2.setIsrequired(entity.getIsrequired());
   		entity2.setNote(entity.getNote());
   		entity2.setLabeltype(entity.getLabeltype());
   		entity2.setValidaInfo(entity.getValidaInfo());
   		entity2.setConstant(entity.getConstant());
   		
		frmFieldDao.editEntity(entity2);
		return entity2;
	}
	public void deleteFrmFieldEntity(String entity,AloneUser user) {
		//删除表字段 
		FrmField frmField = frmFieldDao.getEntity(entity);
		FrmTable frmTable = frmTableDao.getEntity(frmField.getTableid());
		TableTools.delField(frmTable.getEnname(), frmField.getEnname());
		
		frmFieldDao.deleteEntity(frmFieldDao.getEntity(entity));
	}
	
	public void deleteFrmOperateFieldEntity(String tableid,String id) {
		FrmTable frmTable = frmTableDao.getEntity(tableid);
		TableTools.delFrmForm(frmTable.getEnname(),id);
	}
	
	public FrmField getFrmFieldEntity(String id) {
		if (id == null){return null;}
		return frmFieldDao.getEntity(id);
	}
	@Override
	public DataQuery createFrmFieldSimpleQuery(DataQuery query,String tableid) {
		query.addDefaultSort(new DBSort("sort", "asc"));
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"FRM_FIELD",
						"ID,TABLEID,CNNAME,ENNAME,FILEDTYPE,LEN,SORT,LABELTYPE,INPUTTYPE,STATE,CRETIME,UPDTIME,ISREL,ISNULL");
		query.addUserWhere(" and tableid = '"+tableid+"'");
		return dbQuery;
	}
	
	public DataQuery createFrmOperateFormSimpleQuery(DataQuery query,String tableName,String fields){
		DataQuery dbQuery = DataQuery
				.init(
						query,
						""+tableName+"",
						""+fields+"");
		return dbQuery;
	}
	
	public List<FrmField> querFrmFieldsByTableId(String tableid){
		return frmFieldDao.querFrmFieldsByTableId(tableid);
	}
	//----------------------------------------------------------------------------------
	public FrmFieldDaoInter getfrmFieldDao() {
		return frmFieldDao;
	}

	public void setfrmFieldDao(FrmFieldDaoInter dao) {
		this.frmFieldDao = dao;
	}
	public FrmTableDaoInter getFrmTableDao() {
		return frmTableDao;
	}
	public void setFrmTableDao(FrmTableDaoInter frmTableDao) {
		this.frmTableDao = frmTableDao;
	}

}
