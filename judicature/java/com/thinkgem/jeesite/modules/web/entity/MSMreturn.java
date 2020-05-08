package com.thinkgem.jeesite.modules.web.entity;

public class MSMreturn {

	private MT mt;
	
	
	public MT getMt() {
		return mt;
	}


	public void setMt(MT mt) {
		this.mt = mt;
	}


	public class MT {
		private int status;
		private String msgid;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getMsgid() {
			return msgid;
		}
		public void setMsgid(String msgid) {
			this.msgid = msgid;
		}
		
	}
}
