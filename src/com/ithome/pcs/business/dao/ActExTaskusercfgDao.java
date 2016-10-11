package  com.ithome.pcs.business.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ithome.pcs.comm.entity.ActExTaskusercfg;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

/**实体管理
 * @author MAC_wd
 * 
 */
public class ActExTaskusercfgDao implements  ActExTaskusercfgDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<ActExTaskusercfg> sqlTools ;

	public void deleteEntity(ActExTaskusercfg entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from ACT_EX_TASKUSERCFG");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public ActExTaskusercfg getEntity(String id) {
		Session session= sessionFatory.getCurrentSession();
		return (ActExTaskusercfg)session.get(ActExTaskusercfg.class, id);
	}
	public ActExTaskusercfg insertEntity(ActExTaskusercfg entity) {
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	public void editEntity(ActExTaskusercfg entity) {
		Session session= sessionFatory.getCurrentSession();
		session.update(entity);
	}
	
	@Override
	public Session getSession() {
		return sessionFatory.getCurrentSession();
	}
	public DataResult runSqlQuery(DataQuery query){
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public void deleteEntitys(List<DBRule> rules) {
		sqlTools.deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<ActExTaskusercfg> selectEntitys(List<DBRule> rules) {
		return sqlTools.selectSqlFromFunction(
				sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		sqlTools.updataSqlFromFunction(sessionFatory.getCurrentSession(),
				values, rules);
	}
	
	
	
	@Override
	public List<String> getActExTaskurgBytaskKey(String taskKey) {
		Session session = sessionFatory.getCurrentSession();
		String sql=" select b.USERID "+
					"  from act_ex_taskusercfg a, act_ex_groupuser b "+
					" where a.selectivetype = '1' "+
					"   and a.taskkey =? "+
					"   and a.userorgroupid = b.groupid "+
					" union "+
					" select a.USERORGROUPID as  userid "+
					"  from act_ex_taskusercfg a "+
					" where a.selectivetype = '0' "+
					"   and a.taskkey =? ";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setParameter(0, taskKey);
		sqlQuery.setParameter(1, taskKey);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List getList = sqlQuery.list();
		Map<String,String> retMap = new HashMap<String, String>();
		List<String> retList = new ArrayList<String>();
		  try {    
			  if (null != retMap) {  
			      Map recMap = null;    
			    for (int i = 0; i < getList.size(); i++) {  
			       recMap = (Map)getList.get(i);  
			       retList.add(recMap.get("USERID").toString());
			    }  
			  }  
			}   
			catch (Exception e) {
				e.printStackTrace();
			}
		  return retList;
	}
	
	
	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}
	public HibernateSQLTools<ActExTaskusercfg> getSqlTools() {
		return sqlTools;
	}
	public void setSqlTools(HibernateSQLTools<ActExTaskusercfg> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
