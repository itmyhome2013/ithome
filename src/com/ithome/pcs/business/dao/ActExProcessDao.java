package  com.ithome.pcs.business.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ithome.pcs.comm.entity.ActExProcess;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

/**实体管理
 * @author MAC_wd
 * 
 */
public class ActExProcessDao implements  ActExProcessDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<ActExProcess> sqlTools ;

	public void deleteEntity(ActExProcess entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from ACT_EX_PROCESS");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public ActExProcess getEntity(String id) {
		Session session= sessionFatory.getCurrentSession();
		return (ActExProcess)session.get(ActExProcess.class, id);
	}
	public ActExProcess insertEntity(ActExProcess entity) {
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	public void editEntity(ActExProcess entity) {
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
	public List<ActExProcess> selectEntitys(List<DBRule> rules) {
		return sqlTools.selectSqlFromFunction(
				sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		sqlTools.updataSqlFromFunction(sessionFatory.getCurrentSession(),
				values, rules);
	}
	
	
	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}
	public HibernateSQLTools<ActExProcess> getSqlTools() {
		return sqlTools;
	}
	public void setSqlTools(HibernateSQLTools<ActExProcess> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
