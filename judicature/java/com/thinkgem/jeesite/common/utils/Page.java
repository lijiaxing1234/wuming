/**  
 * @Title: crm_my Page.java
 * @Package com.csh.crm.component.model
 * @version V1.0  
 */
package com.thinkgem.jeesite.common.utils;

import java.util.List;

/**
 * @ClassName: Page
 * @Description: 分页模型，每个页面都是一个page对象
 * @author csh
 * @date 2016年8月5日 上午11:24:57
 * 
 */
public class Page<T> {
	/**
	 * @Fields pageNo : 当前页
	 */
	private int pageNo;
	/**
	 * @Fields pageSize : 每页数量
	 */
	private int pageSize = 10;
	/**
	 * @Fields totalPage : 总页数
	 */
	private int totalPage;
	/**
	 * @Fields totalRecordNo : 总记录数
	 */
	private long totalRecordNo;
	/**
	 * @Fields content : 页面内容
	 */
	private List<T> content;
	
	private String path;

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 传入当前页码和总记录数初始化page对象
	 * </p>
	 * 
	 * @param pageNo
	 *            当前页
	 * @param totalRecordNo
	 *            总记录数
	 */
	public Page(String pageNo, long totalRecordNo) {
		// 总记录数
		this.totalRecordNo = totalRecordNo;
		// 计算总页数
		this.totalPage = (int) (totalRecordNo / this.pageSize + ((totalRecordNo % this.pageSize) == 0 ? 0
				: 1));
		this.pageNo = 1;
		// 纠正页码
		try {
			this.pageNo = Integer.parseInt(pageNo);
		} catch (Exception e) { /* 如果页码转换失败则什么都不做 */
		}
		// 如果传入的页码大于总页数，则页码等于总页数
		if (this.pageNo > totalPage) {
			this.pageNo = totalPage;
		}
		// 如果传入的页面小于1，则页码等于1
		if (this.pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * @Title: hasPrePage
	 * @Description: 是否有上一页，如果当前页小于1说明有上一页
	 * @return boolean
	 */
	public boolean isHasPrePage() {
		return this.pageNo > 1;
	}

	/**
	 * @Title: hasNext
	 * @Description: 是否有下一页，如果当前页小于总页数说明有下一页
	 * @return
	 */
	public boolean isHasNextPage() {
		return this.pageNo < this.totalPage;
	}

	/**
	 * @Title: getPrePageNo
	 * @Description: 获取上一页
	 * sdfsdfs
	 * @return int
	 */
	public int getPrePageNo() {
		return this.pageNo - 1;
	}

	/**
	 * @Title: getNextPageNo
	 * @Description: 获取下一页
	 * @return int
	 */
	public int getNextPageNo() {
		return this.pageNo + 1;
	}

	/**
	 * @Title: getFistIndex
	 * @Description: <p>获取查询的初始下标</p><p>等于当前页码</p>
	 * @return int
	 */
	public int getFirstIndex() {
		return ((this.pageNo-1)*this.pageSize)+1;
	}

	/**
	 * @Title: getEndIndex
	 * @Description: 获取查询的结束下标
	 * @return int
	 */
	public int getEndIndex() {
		return getFirstIndex()+this.pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	/**
	* @Title: setTotalPage
	* @Description: 获取总页数
	* @param totalPage
	* @return void
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalRecordNo() {
		return totalRecordNo;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", totalPage=" + totalPage + ", totalRecordNo="
				+ totalRecordNo + ", content=" + content + "]";
	}
	
	
}
