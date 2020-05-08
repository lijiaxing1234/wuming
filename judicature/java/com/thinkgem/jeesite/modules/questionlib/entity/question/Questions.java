package com.thinkgem.jeesite.modules.questionlib.entity.question;

import java.util.List;

public class Questions {

	private String irstate ="";
	private String irtitle ="";
	private String irurldate ="";
    private Irtables irtables =null;
    
	private List<Cell> ircontent =null;
	public List<Cell> getIrcontent() {
		return ircontent;
	}
	public void setIrcontent(List<Cell> ircontent) {
		this.ircontent = ircontent;
	}

	public String getIrstate() {
		return irstate;
	}
	public void setIrstate(String irstate) {
		this.irstate = irstate;
	}
	public String getIrtitle() {
		return irtitle;
	}
	public void setIrtitle(String irtitle) {
		this.irtitle = irtitle;
	}
	public String getIrurldate() {
		return irurldate;
	}
	public void setIrurldate(String irurldate) {
		this.irurldate = irurldate;
	}
	 
	public Irtables getIrtables() {
		return irtables;
	}
	public void setIrtables(Irtables irtables) {
		this.irtables = irtables;
	}
	
	 
}
