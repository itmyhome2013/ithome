package com.ithome.mongo.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.data.domain.Page;

import com.ithome.mongo.dao.impl.BookDaoImpl;
import com.ithome.mongo.dao.impl.PersonDaoImpl;
import com.ithome.mongo.domain.Book;
import com.ithome.mongo.domain.Person;
import com.ithome.mongo.server.BookManagerInter;
import com.ithome.mongo.server.PersonManagerInter;
import com.ithome.popu.domain.PopuCitizenInfo;
import com.ithome.popu.domain.PopuHuInfo;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;

public class ActionBookQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private Book entity; // 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private Map<String, Object> node; 
	
	private String personId;  //人员ID


	public String personBookqueryAll() {
	
		query = EasyUiUtils.formatGridQuery(getRequest(), query);
		List<Book> count = aloneIMP.find(); //总记录数
		
		//Page<Book> persons = aloneIMP.paginationQuery(Integer.parseInt(query.getCurrentPage()),ids);
		jsonResult = new HashMap<String, Object>();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		
		/*Iterator<Book> ite = persons.iterator();
		while (ite.hasNext()) {
			Book b = ite.next();
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("ID", b.getId());
			maps.put("BOOKNAME", b.getBookName());
			maps.put("AUTHOR", b.getAuthor());
			maps.put("PRESS", b.getPress());
			maps.put("ISBN", b.getIsbn());
			maps.put("NAME", "haha");
			lists.add(maps);
		}*/
		
		
		Page<Map<String,Object>> ps = aloneIMP.paginationQuery(Integer.parseInt(query.getCurrentPage()),ids);
		Iterator<Map<String,Object>> map = ps.iterator();
		while(map.hasNext()){
			Map<String,Object> m = map.next();
			lists.add(m);
		}
		
		jsonResult.put("total", count.size()); 
		jsonResult.put("rows", lists);
		
		return SUCCESS;
	}
	
	/**
	 * 新增
	 * @return
	 */
	public String addSubmit(){
		
		try {
			
			if(entity.getId()!=null && !"".equals(entity.getId())){  //更新
				aloneIMP.update(entity);
			}else{  //新增
				aloneIMP.insert(entity);
				/*List<Book> books = aloneIMP.findByPersonId(personId); 
				books.add(entity);*/
				alonePersonIMP.update(personId,entity); //更新Person中的Book
			}
			
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.ADD,
					CommitType.FALSE, e);
		}
		
		System.out.println("add submit");
		return SUCCESS;
	}
	
	public String delSubmit() {
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				alonePersonIMP.removeBookRef(personId,id);
				aloneIMP.del(id);
			}
			/*String personId = "57abf072c23114c4ba15b989";
			String bookId = "57abf072c23114c4ba15b988";*/
			
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.DEL,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				entity = new Book();
				
				return SUCCESS;
			}
			case (0): {// 展示
				entity = aloneIMP.findById(ids);
				return SUCCESS;
			}
			case (2): {// 修改
				entity = aloneIMP.findById(ids);
				return SUCCESS;
			}
			default:
				break;
			}
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.OTHER,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}
	
	

	/**
	 * 跳转
	 * 
	 * @return
	 */
	public String forSend() {
		
		return SUCCESS;
	}

	
	private final static BookManagerInter aloneIMP = (BookManagerInter) BeanFactory
			.getBean("bookProxyId");
	
	private final static PersonManagerInter alonePersonIMP = (PersonManagerInter) BeanFactory
			.getBean("personProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}


	public Book getEntity() {
		return entity;
	}

	public void setEntity(Book entity) {
		this.entity = entity;
	}

	public PageSet getPageset() {
		return pageset;
	}

	public void setPageset(PageSet pageset) {
		this.pageset = pageset;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}

	public Map<String, Object> getNode() {
		return node;
	}

	public void setNode(Map<String, Object> node) {
		this.node = node;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}


	private static final Logger log = Logger.getLogger(ActionBookQuery.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 加载树节点 public String loadTreeNode() { treeNodes =
	 * EasyUiTreeNode.formatAjaxTree(EasyUiTreeNode .queryTreeNodeOne(id,
	 * "SORT", "alone_menu", "ID", "PARENTID", "NAME").getResultList(),
	 * EasyUiTreeNode .queryTreeNodeTow(id, "SORT", "alone_menu", "ID",
	 * "PARENTID", "NAME").getResultList(), "PARENTID", "ID", "NAME"); return
	 * SUCCESS; }
	 * 
	 * @return
	 */
}
