package com.ithome.pcs.utils;

import com.ithome.pcs.comm.Pager;

import com.farm.core.sql.query.DataQuery;
/**
 * 分页工具类
 * @author 张晓亮
 *
 */
public class PageUtil {
	
    private PageUtil(){};
	
	public static Pager getPager(DataQuery query){
		int firstResult=query.getPagesize()*(Integer.valueOf(query.getCurrentPage())-1);
		int maxResults=query.getPagesize()*Integer.valueOf(query.getCurrentPage());
	     return new Pager(firstResult,maxResults);
	}
	
	
 
}
