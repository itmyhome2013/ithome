package  com.ithome.pcs.business.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ithome.pcs.comm.entity.ActExApprovaltest;
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
public class ActExApprovaltestDao implements  ActExApprovaltestDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<ActExApprovaltest> sqlTools ;

	public void deleteEntity(ActExApprovaltest entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from ACT_EX_APPROVALTEST");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public ActExApprovaltest getEntity(String id) {
		Session session= sessionFatory.getCurrentSession();
		return (ActExApprovaltest)session.get(ActExApprovaltest.class, id);
	}
	public ActExApprovaltest insertEntity(ActExApprovaltest entity) {
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	public void editEntity(ActExApprovaltest entity) {
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
	public List<ActExApprovaltest> selectEntitys(List<DBRule> rules) {
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
	public HibernateSQLTools<ActExApprovaltest> getSqlTools() {
		return sqlTools;
	}
	public void setSqlTools(HibernateSQLTools<ActExApprovaltest> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
