package  com.ithome.popu.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import com.ithome.popu.domain.PopuHuInfo;

import java.util.List;
import java.util.Map;

/**房屋基础信息
 * @author MAC_wd
 * 
 */
public class PopuHuInfoDao implements  PopuHuInfoDaoInter {
	private SessionFactory sessionFatory;
	private HibernateSQLTools<PopuHuInfo> sqlTools ;

	public void deleteEntity(PopuHuInfo entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from POPU_HU_INFO");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public PopuHuInfo getEntity(BigDecimal id) {
		Session session= sessionFatory.getCurrentSession();
		return (PopuHuInfo)session.get(PopuHuInfo.class, id);
	}
	public PopuHuInfo insertEntity(PopuHuInfo entity) {
		entity.setHuid(sqlTools.getPrimary());
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	public void editEntity(PopuHuInfo entity) {
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
	public List<PopuHuInfo> selectEntitys(List<DBRule> rules) {
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
	public HibernateSQLTools<PopuHuInfo> getSqlTools() {
		return sqlTools;
	}
	public void setSqlTools(HibernateSQLTools<PopuHuInfo> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
