package com.thinkgem.jeesite.modules.web.entity.pay;

public class Pagination {
	protected Integer start;
	protected Integer pageSize;
	protected Boolean enable;

	public Pagination() {
		this.enable = true;
	}

	public boolean isEnable() {
		return this.enable;
	}

	public void setEnable(final boolean enable) {
		this.enable = enable;
	}

	public Integer getStart() {
		return this.start;
	}

	public void setStart(final Integer start) {
		this.start = start;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
	}
}
