package com.thinkgem.jeesite.modules.web.util;

import java.io.Serializable;
import java.util.Date;


/**
 * @author lvsheng
 * @date 16/11/02
 */
public class ReturnData implements Serializable {
    private boolean status;
    private String message;
    private Object data;
    private Date date;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
    
    
}
