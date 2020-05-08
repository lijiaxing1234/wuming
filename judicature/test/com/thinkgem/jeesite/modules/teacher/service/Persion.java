package com.thinkgem.jeesite.modules.teacher.service;

public class Persion {
	private int id;
	private String name;
	private String age;
	private String mobile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Persion)){
			return false;
		}
		Persion persion = (Persion) obj; 
		return this.name.equals(persion.getName())&&this.mobile.equals(persion.getMobile());
	}
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.mobile.hashCode();
	}
}
