package  com.ithome.pcs.business.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ithome.pcs.comm.entity.ActExPcsfromcfg;
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
public class ActExPcsfromcfgDao implements  ActExPcsfromcfgDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<ActExPcsfromcfg> sqlTools ;

	public void deleteEntity(ActExPcsfromcfg entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from ACT_EX_PCSFROMCFG");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public ActExPcsfromcfg getEntity(String id) {
		Session session= sessionFatory.getCurrentSession();
		return (ActExPcsfromcfg)session.get(ActExPcsfromcfg.class, id);
	}
	public ActExPcsfromcfg insertEntity(ActExPcsfromcfg entity) {
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	public void editEntity(ActExPcsfromcfg entity) {
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
	public List<ActExPcsfromcfg> selectEntitys(List<DBRule> rules) {
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
	public HibernateSQLTools<ActExPcsfromcfg> getSqlTools() {
		return sqlTools;
	}
	public void setSqlTools(HibernateSQLTools<ActExPcsfromcfg> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
