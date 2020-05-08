package com.thinkgem.jeesite.modules.student.utils;

import java.io.Serializable;
import java.util.Vector;

import com.thinkgem.jeesite.modules.questionlib.entity.Student;

public class OnlineStudentUtils {

	public static final  String SESSION_STUDENT_LOGIN_KEY="studentLogin_key";//学校端session 登录key
	
	static  OnlineStudentUtils   instance=new OnlineStudentUtils();
	
	Vector<Subject>  vector=null;
	
	OnlineStudentUtils(){
		vector=new Vector<Subject>();
	}
	
	
	public static OnlineStudentUtils  getIntance(){
		return instance;
	}
	
	public void  add(Subject obj){
		this.vector.add(obj);
	}
	
	public void remove(Subject obj){
		
		if(this.vector.contains(obj)){
			this.vector.remove(obj);
		}
	}
	
	
	public boolean contains(Subject obj){
		if(obj==null){
			return false;
		}
		return  this.vector.contains(obj);
	}
	
	
	public Vector<Subject> getVector(){
		return this.vector;
	}
	
	public int  Size(){
		return this.vector.size();
	}
	
	
	
	
    public static class Subject implements Serializable{
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		
		private String id;
		private String loginName; // 登录名
		private String name; // 姓名
    	private String schoolId; //学校id
    	
    	public  Subject(Student stu,String schoolId){
    		
    		this.id=stu.getId();
    		this.loginName=stu.getStdCode();
    		this.name=stu.getName();
    		this.schoolId=schoolId;
    	}


    	public String getId() {
			return id;
		}
    	public void setId(String id) {
			this.id = id;
		}
    	
    	
		public String getLoginName() {
			return loginName;
		}


		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getSchoolId() {
			return schoolId;
		}


		public void setSchoolId(String schoolId) {
			this.schoolId = schoolId;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((loginName == null) ? 0 : loginName.hashCode());
			result = prime * result
					+ ((schoolId == null) ? 0 : schoolId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Subject other = (Subject) obj;
			if (loginName == null) {
				if (other.loginName != null)
					return false;
			} else if (!loginName.equals(other.loginName))
				return false;
			if (schoolId == null) {
				if (other.schoolId != null)
					return false;
			} else if (!schoolId.equals(other.schoolId))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Subject [loginName=" + loginName + ", name=" + name
					+ ", schoolId=" + schoolId + "]";
		}



    }
	
	
}
