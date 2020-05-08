package com.thinkgem.jeesite.common.web;

import java.io.Serializable;

/**
 * 页面提示信息
 */
public class Message implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private  String message;
	private  Type type;
	
	
	public Message(String message,Type type){
		this.message=message;
		this.type=type;
	}
	
	public String getMessage() {
		return message;
	}
	public String getType() {
		if(type==null){
			return "";
		}
		return type.getValue();
	}
	
	public enum  Type{
		
		 LOADING("loading"),
		 INFO("info"),
		 WARNING("warning"),
		 ERROR("error"),
		 SUCCESS("success");
	    
	    private final String value;
	    
	    Type(String value) {
            this.value = value;
        }
	    
	    public String getValue() {
            return value;
        }

		@Override
		public String toString() {
			return this.value;
		}
	}
	
	
	@Override
	public String toString() {
		return "Message [message=" + message + ", type=" + type + "]";
	}

	public static void main(String[] args) {
		
		System.out.println(new Message("11", Message.Type.SUCCESS));
		
	}
}


