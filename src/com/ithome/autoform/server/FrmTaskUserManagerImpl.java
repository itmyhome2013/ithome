package com.ithome.autoform.server;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.ithome.autoform.dao.FrmTaskUserDaoInter;
import com.ithome.autoform.domain.FrmTaskUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;


/**
 * 任务节点配置
 * 
 * @author huxuxu
 */
public class FrmTaskUserManagerImpl implements FrmTaskUserManagerInter {
	private FrmTaskUserDaoInter frmTaskUserDao;

	private static final Logger log = Logger
			.getLogger(FrmTaskUserManagerImpl.class);

	public FrmTaskUserDaoInter getFrmTaskUserDao() {
		return frmTaskUserDao;
	}

	public void setFrmTaskUserDao(FrmTaskUserDaoInter frmTaskUserDao) {
		this.frmTaskUserDao = frmTaskUserDao;
	}
	
	@Override
	public DataQuery createFrmTaskUserSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"ACT_EX_TASK",
						"'1',ID as ID,taskkey as TASKKEY,TASKNAME as TASKNAME,FLAG as FLAG")
						.addRule(new DBRule("FLAG", "0", "="));
		return dbQuery;
	}
	
	@Override
	public DataQuery queryAllUser(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"alone_user",
						"'1',id as ID,name as TEXT");
		return dbQuery;
	}
	
	@Override
	public DataQuery queryAllGroup(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"v_act_ex_org",
						"'1',id as ID,name as TEXT");
		return dbQuery;
	}
	
	@Override
	public DataQuery queryAllRole(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"v_act_ex_role",
						"'1',id as ID,name as TEXT");
		return dbQuery;
	}

	@Override
	public String getUserOrgGroupIdByTaskKey(String taskkey,String selectivetype) {
		
	
		StringBuffer buf = new StringBuffer();
		
		DataQuery dbQuery = DataQuery.init(null,
				"act_ex_taskusercfg",
				"'1',taskkey,userorgroupid").addUserWhere(" and taskkey = '"+taskkey+"' and selectivetype = '"+selectivetype+"'");
		dbQuery.setPagesize(1000);
		List<Map<String, Object>> list;
		try {
			list = dbQuery.search().getResultList();
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				buf.append(map.get("USERORGROUPID").toString()).append(",");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(buf);
		return buf.toString();
	}
	
	@Override
	public FrmTaskUser insertFrmTaskUserEntity(FrmTaskUser entity) {
		
		FrmTaskUser taskUser = frmTaskUserDao.insertEntity(entity);
		return taskUser;
	}

	@Override
	public void deleteEntitys(List<DBRule> rules) {
		frmTaskUserDao.deleteEntitys(rules);
	}

	

	
	// ----------------------------------------------------------------------------------

	

}
