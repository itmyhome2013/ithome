package com.ithome.mongo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ithome.mongo.dao.BookDao;
import com.ithome.mongo.dao.PersonDao;
import com.ithome.mongo.domain.Book;
import com.ithome.mongo.domain.Person;
import com.ithome.mongo.server.SpringDataPageable;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.mongodb.BasicDBObject;


@Repository
public class BookDaoImpl implements BookDao {

	private static MongoTemplate mongoTemplate = null;

	public List<Book> find() {
		Query query = new Query();
		// query.skip(skip);
		// query.limit(limit);
		return mongoTemplate.find(query, Book.class);
	}
	public Book findById(String id){
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Book.class);
	}
	
	public List<Book> findByPersonId(String personId) {
		
		Query query = new Query(Criteria.where("id").is(personId));
		Person person = mongoTemplate.findOne(query, Person.class);
		
		List<Book> books = person.getBook();
		
		return books;
	}
	
	@Override
	public void insert(Book book) {
		book.setId(null);
		mongoTemplate.save(book, "book");
	}
	
	@Override
	public void update(Book book) {
		Query query = new Query(Criteria.where("id").is(book.getId()));
		Update update = new Update();
		update.set("bookName", book.getBookName());
		update.set("author", book.getAuthor());
		update.set("press", book.getPress());
		update.set("isbn", book.getIsbn());
		mongoTemplate.updateFirst(query, update, Book.class);
	}
	
	@Override
	public void del(String id) {
		Criteria criteria = Criteria.where("id").in(id);

		if (criteria != null) {
			Query query = new Query(criteria);
			if (query != null && mongoTemplate.find(query, Book.class) != null) {
				mongoTemplate.remove(mongoTemplate.findOne(query, Book.class));
			}

		}

	}
	
	/*public void dbRefBOok(){
		String personId = "57a9551b95c7f22588bbd673";
		
		Query query = new Query(Criteria.where("id").is(personId));
		Person person = mongoTemplate.findOne(query, Person.class);
		Book book = person.getBook();
		System.out.println(person);
	}*/
	
	
	public Page<Map<String,Object>> paginationQuery(Integer pageNum,String personId){
		Query query = new Query(Criteria.where("id").is(personId));
		SpringDataPageable pageable = new SpringDataPageable();
		// 开始页
		pageable.setPagenumber(pageNum);
		// 每页条数
		pageable.setPagesize(10);
		Long count = mongoTemplate.count(query, Book.class);
		
		
		Person person = mongoTemplate.findOne(query, Person.class);
		List<Book> list = person.getBook();
		
		if(list == null){
			list = new ArrayList<Book>();
		}
		
		List<Map<String,Object>> lis = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Book book = list.get(i);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("BOOKNAME", book.getBookName());
			map.put("AUTHOR", book.getAuthor());
			map.put("PRESS", book.getPress());
			map.put("ISBN", book.getIsbn());
			map.put("NAME", person.getName());
			lis.add(map);
		}
		//Page<Book> pagelist = new PageImpl<Book>(list, pageable, count);
		Page<Map<String,Object>> pagelist = new PageImpl<Map<String,Object>>(lis, pageable,count);
		//List<Book> books = mongoTemplate.findAll(Book.class, "book");
		
		return pagelist;
	}
	
	/*@Override
	public Page<Book> paginationQuery(Integer pageNum,String personId) {

		SpringDataPageable pageable = new SpringDataPageable();
		Query query = new Query(Criteria.where("id").is(personId));
		
		// 开始页
		pageable.setPagenumber(pageNum);
		// 每页条数
		pageable.setPagesize(10);
		// 排序
		// pageable.setSort(sort);
		// 查询出一共的条数
		Long count = mongoTemplate.count(query, Book.class);
		// 查询
		Person person = mongoTemplate.findOne(query, Person.class);
		
		List<Book> list = person.getBook();
		
		if(list == null){
			list = new ArrayList<Book>();
		}
		for(Iterator<Book> ite = list.iterator();ite.hasNext();){  
			Book b = ite.next();
			if(b == null){
				ite.remove();    //如果DBRef 中有引用 而子表中无数据 则删除
			}
        }  
		List<Book> list = mongoTemplate.find(query.with(pageable),
				Book.class);
		// 将集合与分页结果封装
		Page<Book> pagelist = new PageImpl<Book>(list, pageable, count);

		return pagelist;
	}*/

	
	public static MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public static void setMongoTemplate(MongoTemplate mongoTemplate) {
		BookDaoImpl.mongoTemplate = mongoTemplate;
	}

}
