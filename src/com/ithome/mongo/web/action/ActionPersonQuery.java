package com.ithome.mongo.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.data.domain.Page;

import com.ithome.mongo.dao.impl.PersonDaoImpl;
import com.ithome.mongo.domain.Book;
import com.ithome.mongo.domain.Person;
import com.ithome.mongo.server.PersonManagerInter;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.spring.BeanFactory;

public class ActionPersonQuery extends WebSupport {
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private Person entity;// 实体封装
	private Book book;
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private Map<String, Object> node; 


	public String queryall() {
	
		query = EasyUiUtils.formatGridQuery(getRequest(), query);
		List<Person> count = aloneIMP.find(); //总记录数
		
		Page<Person> persons = aloneIMP.paginationQuery(query);
		jsonResult = new HashMap<String, Object>();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		
		Iterator<Person> ite = persons.iterator();
		while (ite.hasNext()) {
			Person p = ite.next();
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("ID", p.getId());
			maps.put("NAME", p.getName());
			maps.put("MOBILE", p.getMobile());
			maps.put("ADDRESS", p.getAddress());
			maps.put("NOTE", p.getNote());
			lists.add(maps);
		}
		jsonResult.put("total", count.size()); 
		jsonResult.put("rows", lists);
		
		
		return SUCCESS;
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public String addSubmit() {
		try {
			
			if(entity.getId()!=null && !"".equals(entity.getId())){  //更新
				aloneIMP.update(entity);
			}else{
				aloneIMP.insert(entity);
				System.out.println("插入成功!");	
			}
			
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.ADD,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	public String delSubmit() {
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				aloneIMP.del(id);
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.DEL,
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

	@Test
	public void t(){
		System.out.println("hello");
	}
	
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
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

	private final static PersonManagerInter aloneIMP = (PersonManagerInter) BeanFactory
			.getBean("personProxyId");

	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public Person getEntity() {
		return entity;
	}

	public void setEntity(Person entity) {
		this.entity = entity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
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

	private static final Logger log = Logger.getLogger(ActionPersonQuery.class);
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
