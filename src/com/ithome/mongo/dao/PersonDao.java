package com.ithome.mongo.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ithome.mongo.domain.Book;
import com.ithome.mongo.domain.Person;
import com.farm.core.sql.query.DataQuery;


public interface PersonDao {

	/**
	 * 新增
	 * @param person 
	 */
	public void insert(Person person);
	
	/**
	 * 根据PersonId 更新book实体
	 * @param person
	 */
	public void update(String personId,Book book);
	
	/**
	 * 更新
	 * @param person
	 */
	public void update(Person person);
	
	/**
	 * 根据ID查找Person实体
	 * @param personid
	 * @return
	 */
	public Person findById(String personid);
	
	/**
	 * 查询
	 * @return
	 */
	public List<Person> find();
	
	/**
	 * 删除
	 * @param id
	 */
	public void del(String id);
	
	/**
	 * 删除Person下的Book对象
	 * @param personId
	 * @param bookId
	 */
	public void removeBookRef(String personId, String bookId);
	
	/**
	 * 创建查询
	 * @param query
	 * @return
	 */
	public DataQuery createMongoSimpleQuery(DataQuery query);
	
	/**
	 * 分页查询
	 * @param pageNum  开始页
	 * @return
	 */
	public Page<Person> paginationQuery(DataQuery query); 

}
