package com.ithome.pcs.business.dao;

import java.util.List;
import java.util.Map;

import com.ithome.pcs.comm.entity.ActExTask;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
/**
 * 实体管理
 * @author 赵克俭(新增)
 *
 */
public class ActExTaskDao implements ActExTaskDaoInter {

	private SessionFactory sessionFatory;
	
	@Override
	public void insertEntity(ActExTask entity) {
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
	}

	@Override
	public void deleteAllEntity() {
		Session session= sessionFatory.getCurrentSession();
		Query query = session.createQuery("delete ActExTask");
		query.executeUpdate();
	}
	
	@Override
	public List<ActExTask> findByCondition(String key, String value) {
		Session session= sessionFatory.getCurrentSession();
		Query query = session.createQuery("from ActExTask where "+key+" = '"+value+"'");
		List list = query.list();
		if(list.isEmpty()){
			return null;
		}
		return (List<ActExTask>)list;
	}
	
	@Override
	public List<ActExTask> findByConditions(Map<String, String> conditions) {
		Session session= sessionFatory.getCurrentSession();
		StringBuilder hql = new StringBuilder();
		hql.append(" from ActExTask ");
		if(conditions != null && conditions.size() > 0){
			hql.append(" where 1=1 ");
			for(Map.Entry<String, String> condition : conditions.entrySet()){
				hql.append(" and ");
				String key = condition.getKey();
				String value = condition.getValue();
				hql.append(key);
				hql.append(" = '"+value+"'");
			}
		}
		Query query = session.createQuery(hql.toString());
		List list = query.list();
		if(list.isEmpty()){
			return null;
		}
		return (List<ActExTask>)list;
	}
	
	@Override
	public void deleteByConditions(Map<String, String> conditions) {
		Session session= sessionFatory.getCurrentSession();
		StringBuilder hql = new StringBuilder();
		hql.append(" update ActExTask ");
		hql.append(" set flag = '1'" );
		if(conditions != null && conditions.size() > 0){
			hql.append(" where 1=1 ");
			for(Map.Entry<String, String> condition : conditions.entrySet()){
				hql.append(" and ");
				String key = condition.getKey();
				String value = condition.getValue();
				hql.append(key);
				hql.append(" = '"+value+"'");
			}
		}
		Query query = session.createQuery(hql.toString());
		query.executeUpdate();
	}
	
	@Override
	public void updateEntity(ActExTask task) {
		Session session= sessionFatory.getCurrentSession();
		session.update(task);
	}


	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

}
