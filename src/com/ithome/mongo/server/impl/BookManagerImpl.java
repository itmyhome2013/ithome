package com.ithome.mongo.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.ithome.mongo.dao.BookDao;
import com.ithome.mongo.domain.Book;
import com.ithome.mongo.server.BookManagerInter;

public class BookManagerImpl implements BookManagerInter {

	private BookDao bookDao;
	
	@Override
	public List<Book> find() {
		// TODO Auto-generated method stub
		return bookDao.find();
	}
	
	@Override
	public Book findById(String id) {
		// TODO Auto-generated method stub
		return bookDao.findById(id);
	}
	
	@Override
	public void del(String id) {
		// TODO Auto-generated method stub
		bookDao.del(id);
	}

	@Override
	public void insert(Book book) {
		// TODO Auto-generated method stub
		bookDao.insert(book);
	}

	/*@Override
	public Page<Book> paginationQuery(Integer pageNum, String personId) {
		// TODO Auto-generated method stub
		return bookDao.paginationQuery(pageNum, personId);
	}*/

	@Override
	public Page<Map<String,Object>> paginationQuery(Integer pageNum, String personId) {
		// TODO Auto-generated method stub
		return bookDao.paginationQuery(pageNum, personId);
	}
	
	@Override
	public void update(Book book) {
		// TODO Auto-generated method stub
		bookDao.update(book);
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

}
