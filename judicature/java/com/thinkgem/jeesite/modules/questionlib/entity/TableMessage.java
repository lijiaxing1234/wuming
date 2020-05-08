/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.thinkgem.jeesite.modules.sys.entity.Office;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统消息Entity
 * @author flychao
 * @version 2016-10-24
 */
public class TableMessage extends DataEntity<TableMessage> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private Date createtime;		// 发布时间
	private String isTop;		// 是否置顶 1-是　0-否
	private Office office;		// office_id
	
	public TableMessage() {
		super();
		this.isTop="0";
		this.createtime=new Date();
	}

	public TableMessage(String id){
		super(id);
	}

	@Length(min=1, max=30, message="标题长度必须介于 1 和 30 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Length(min=1, max=35, message="内容长度必须介于 1 和 35 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@NotNull(message="发布时间不能为空")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Length(min=0, max=4, message="是否置顶 1-是　0-否长度必须介于 0 和 4 之间")
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}