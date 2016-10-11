package com.ithome.mongo.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.ithome.mongo.dao.PersonDao;
import com.ithome.mongo.domain.Book;
import com.ithome.mongo.domain.Person;
import com.ithome.mongo.server.SpringDataPageable;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Repository
public class PersonDaoImpl implements PersonDao {

	private static MongoTemplate mongoTemplate = null;

	@Override
	public DataQuery createMongoSimpleQuery(DataQuery query) {
		return null;
	}

	@Override
	public Page<Person> paginationQuery(DataQuery query) {

		/*DBObject dbObject = new BasicDBObject();
		
		BasicDBObject fieldsObject=new BasicDBObject();  
		fieldsObject.put("name", true);  
		fieldsObject.put("mobile", true);  
		
		Query que = new BasicQuery(dbObject,fieldsObject);*/
		
		Query que = new Query();
		
		//查询条件
		List<DBRule> rules = query.getQueryRule();
		for(int i=0;i<rules.size();i++){
			DBRule rule = rules.get(i);
			Pattern pattern = Pattern.compile("^.*"+ rule.getValue() + ".*$");  
			que.addCriteria(Criteria.where(rule.getKey().toLowerCase()).regex(pattern));
		}
			
		SpringDataPageable pageable = new SpringDataPageable();
		
		//List<Order> orders = new ArrayList<Order>(); // 排序
		// orders.add(new Order(Direction.DESC, "age"));
		// Sort sort = new Sort(orders);

		// 开始页
		pageable.setPagenumber(Integer.parseInt(query.getCurrentPage()));
		// 每页条数
		pageable.setPagesize(10);
		// 排序
		// pageable.setSort(sort);
		// 查询出一共的条数
		Long count = mongoTemplate.count(que, Person.class);
		// 查询
		List<Person> list = mongoTemplate.find(que.with(pageable),
				Person.class);
		// 将集合与分页结果封装
		Page<Person> pagelist = new PageImpl<Person>(list, pageable, count);

		return pagelist;
	}

	@Override
	public List<Person> find() {
		Query query = new Query();
		// query.skip(skip);
		// query.limit(limit);
		return mongoTemplate.find(query, Person.class);
	}
	
	public Person findById(String personid) {
		Query query = new Query();
		Criteria criteria = Criteria.where("id").in(personid);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Person.class);
	}

	public void saveBook(List<Book> books) {
		for(Book book : books){
			mongoTemplate.save(book, "book");	
		}
		
	}
	
	public void dbRefBOok(){
		String personId = "57a9551b95c7f22588bbd673";
		/*Book book = mongoTemplate.findOne(new Query(Criteria.where("name").is("武器1")),Book.class);
		Query query = new Query(Criteria.where("id").is(personId).and("book").is(book));
		Person queryResult = mongoTemplate.findOne(query, Person.class);*/
		
		Query query = new Query(Criteria.where("id").is(personId));
		Person person = mongoTemplate.findOne(query, Person.class);
		//Book book = person.getBook();
		System.out.println(person);
	}
	
	@Override
	public void insert(Person person) {
		person.setId(null);
		mongoTemplate.save(person, "person");
	}
	
	@Override
	public void update(Person person) {
		Query query = new Query(Criteria.where("id").is(person.getId()));
		Update update = new Update();
		update.set("name", person.getName());
		update.set("mobile", person.getMobile());
		update.set("address", person.getAddress());
		update.set("note", person.getNote());
		mongoTemplate.updateFirst(query, update, Person.class);
	}
	
	@Override
	public void update(String personId,Book book) {
		Query query = new Query(Criteria.where("_id").is(personId));
		Person p = mongoTemplate.findOne(query, Person.class);
		Update update = new Update();
		update.addToSet("book", book); //添加一个子数据 DBRef 格式
		/*List<Book> boo = p.getBook();
		boo.add(book);
		update.set("book", boo);*/
		//mongoTemplate.updateMulti(query, update, "person");
		mongoTemplate.findAndModify(query, update, Person.class);
	}
	
	public void removeBookRef(String personId,String bookId){
		
		Query query = new Query(Criteria.where("_id").is(personId));
		Person p = mongoTemplate.findOne(query, Person.class);
		
		Query queryBook = new Query(Criteria.where("id").is(bookId));
		Book b = mongoTemplate.findOne(queryBook, Book.class);
		
		List<Book> books = p.getBook();
		
		/*for(Iterator<Book> ite = books.iterator();ite.hasNext();){  
			
			Book boo = ite.next();
			if(boo.getId().equals(bookId)){
				ite.remove();
			}
        }*/  
		Update update = new Update();
		for(Iterator<Book> ite = books.iterator();ite.hasNext();){  
			Book book = ite.next();
			
			if(book.getId().equals(bookId)){
				update.pull("book", book);
			}
			
			//update.addToSet("book", book); //添加一个子数据 DBRef 格式
		}
		
		mongoTemplate.findAndModify(query, update, Person.class);
		
		/*Query query = Query.query(Criteria.where("id").is(personId));
        Update update = new Update();
        update.pull("book",new BasicDBObject("id",bookId));
        mongoTemplate.updateFirst(query, update, Person.class);*/
		
		
		
		/*Query query2 = new Query(Criteria.where("id").is(bookId));
		Book book = mongoTemplate.findOne(query2, Book.class);
		
		List<Book> books = person.getBook();
		books.remove(book);
		
		person.setBook(books);
		
		Update update = new Update();
		update.unset("57abe6d1c231ddcef18c1f49");
		
		mongoTemplate.findAndModify(query, update, Person.class);*/
	}

	@Override
	public void del(String id) {
		Criteria criteria = Criteria.where("id").in(id);

		if (criteria != null) {
			Query query = new Query(criteria);
			if (query != null && mongoTemplate.find(query, Person.class) != null) {
				mongoTemplate.remove(mongoTemplate.findOne(query, Person.class));
			}

		}

	}

	public static MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public static void setMongoTemplate(MongoTemplate mongoTemplate) {
		PersonDaoImpl.mongoTemplate = mongoTemplate;
	}

}
