package  com.ithome.autoform.dao;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import com.ithome.autoform.domain.FrmField;

import java.util.List;
import java.util.Map;

/**实体管理
 * @author MAC_wd
 * 
 */
public class FrmFieldDao implements  FrmFieldDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<FrmField> sqlTools ;

	public void deleteEntity(FrmField entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from FRM_FIELD");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public FrmField getEntity(String id) {
		Session session= sessionFatory.getCurrentSession();
		return (FrmField)session.get(FrmField.class, id);
	}
	public FrmField insertEntity(FrmField entity) {
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	public void editEntity(FrmField entity) {
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
	public List<FrmField> selectEntitys(List<DBRule> rules) {
		return sqlTools.selectSqlFromFunction(
				sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		sqlTools.updataSqlFromFunction(sessionFatory.getCurrentSession(),
				values, rules);
	}
	
	@Override
	public int countEntitys(List<DBRule> rules) {
		return sqlTools.countSqlFromFunction(sessionFatory.getCurrentSession(),
				rules);
	}
	
	@Override
	public List<FrmField> querFrmFieldsByTableId(String tableid){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select * from FRM_FIELD where tableid = '"+tableid+"' order by sort");
		sqlquery.addEntity(FrmField.class);
		List<FrmField> fields = sqlquery.list();
		return fields;
	}
	
	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}
	public HibernateSQLTools<FrmField> getSqlTools() {
		return sqlTools;
	}
	public void setSqlTools(HibernateSQLTools<FrmField> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
