/** 
 * Date:2017年3月19日下午9:11:55 
 * Copyright (c) 2017, BingLi All Rights Reserved. 
 * 
*/  
  
package com.laoxin.core.vo;

import java.util.List;


public class PageVO<T> {
	private int offset;

	private int pageSize;

	private long total;

	private List<T> list;

	public PageVO() {

	}

	public PageVO(int offset, int pageSize, long total, List<T> list) {
		this.offset = offset;
		this.pageSize = pageSize;
		this.total = total;
		this.list = list;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
