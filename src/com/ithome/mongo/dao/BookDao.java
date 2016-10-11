package com.ithome.mongo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.ithome.mongo.domain.Book;
import com.ithome.mongo.domain.Person;
import com.farm.core.sql.query.DataQuery;


public interface BookDao {

	
	/**
	 * 分页查询 
	 * @param pageNum  开始页
	 * @return
	 */
	//public Page<Book> paginationQuery(Integer pageNum,String personId); 
	
	public Page<Map<String,Object>> paginationQuery(Integer pageNum,String personId); 
	
	/**
	 * 查询
	 * @return
	 */
	public List<Book> find();
	
	/**
	 * 根据ID查询Book对象
	 * @param id
	 * @return
	 */
	public Book findById(String id);
	
	/**
	 * 新增
	 * @param book
	 */
	public void insert(Book book);
	
	/**
	 * 修改
	 * @param book
	 */
	public void update(Book book);
	
	/**
	 * 删除
	 * @param id
	 */
	public void del(String id);

}
