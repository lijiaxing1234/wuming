package com.thinkgem.jeesite.modules.questionlib.entity.question;

import java.util.List;

public class Irtables {
	private int count=0;
	private  List<Qtable> table =null;
	public List<Qtable> getTable() {
		return table;
	}
	public void setTable(List<Qtable> table) {
		this.table = table;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
 
   
}
