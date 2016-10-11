package com.ithome.pcs.comm;
/**
 * 分页实例类
 * @author 张晓亮
 *
 */
public class Pager {
	private int firstResult;
	private int maxResults;

	public Pager() {
	};

	public Pager(int firstResult, int maxResults) {
		this.firstResult = firstResult;
		this.maxResults = maxResults;
	}
	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

}
