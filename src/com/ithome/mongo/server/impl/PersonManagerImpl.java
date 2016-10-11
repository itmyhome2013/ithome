package com.ithome.mongo.server.impl;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ithome.mongo.dao.PersonDao;
import com.ithome.mongo.domain.Book;
import com.ithome.mongo.domain.Person;
import com.ithome.mongo.server.PersonManagerInter;
import com.farm.core.sql.query.DataQuery;

public class PersonManagerImpl implements PersonManagerInter {

	private PersonDao personDao;

	@Override
	public void insert(Person person) {
		personDao.insert(person);
	}

	@Override
	public void del(String id) {
		// TODO Auto-generated method stub
		personDao.del(id);
	}
	
	@Override
	public void removeBookRef(String personId, String bookId) {
		// TODO Auto-generated method stub
		personDao.removeBookRef(personId, bookId);
	}
	
	@Override
	public Person findById(String personid) {
		// TODO Auto-generated method stub
		return personDao.findById(personid);
	}

	@Override
	public List<Person> find() {
		// TODO Auto-generated method stub
		return personDao.find();
	}
	
	@Override
	public Page<Person> paginationQuery(DataQuery query) {
		// TODO Auto-generated method stub
		return personDao.paginationQuery(query);
	}

	@Override
	public void update(String personId, Book book) {
		// TODO Auto-generated method stub
		personDao.update(personId, book);
	}

	@Override
	public void update(Person person) {
		// TODO Auto-generated method stub
		personDao.update(person);
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}


}
