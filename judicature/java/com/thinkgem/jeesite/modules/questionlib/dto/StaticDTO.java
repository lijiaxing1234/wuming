package com.thinkgem.jeesite.modules.questionlib.dto;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class StaticDTO  extends DataEntity<StaticDTO>{

	private String specialtyId;
	private String specialtyName;
	
	private String courseId;
	private String courseName;
	
	private String versionId;
	private String versionName;
	
	private int count1;
	private int count2;
	private int count3;
	private int count4;
	private int count5;
	private int count6;
	private int count7;
	private int count8;
	private int count9;
	private int count10;
	private int count11;
	private String  officeId ;
	
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	@ExcelField(title="概念题", align=2, sort=9)
	public int getCount6() {
		return count6;
	}
	public void setCount6(int count6) {
		this.count6 = count6;
	}
	@ExcelField(title="综合题", align=2, sort=10)
	public int getCount7() {
		return count7;
	}
	public void setCount7(int count7) {
		this.count7 = count7;
	}
	@ExcelField(title="作图题", align=2, sort=11)
	public int getCount8() {
		return count8;
	}
	public void setCount8(int count8) {
		this.count8 = count8;
	}
	@ExcelField(title="制表题", align=2, sort=12)
	public int getCount9() {
		return count9;
	}
	public void setCount9(int count9) {
		this.count9 = count9;
	}
	@ExcelField(title="识图题", align=2, sort=13)
	public int getCount10() {
		return count10;
	}
	public void setCount10(int count10) {
		this.count10 = count10;
	}
	@ExcelField(title="判断题", align=2, sort=14)
	public int getCount11() {
		return count11;
	}
	public void setCount11(int count11) {
		this.count11 = count11;
	}
	private int start;
	private int pageSize;
	public String getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	
	@ExcelField(title="专业名称", align=2, sort=1)
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@ExcelField(title="课程名称", align=2, sort=2)
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	@ExcelField(title="版本名称", align=2, sort=3)
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	
	@ExcelField(title="单选题", align=2, sort=4)
	public int getCount1() {
		return count1;
	}
	public void setCount1(int count1) {
		this.count1 = count1;
	}
	@ExcelField(title="填空题", align=2, sort=6)
	public int getCount2() {
		return count2;
	}
	public void setCount2(int count2) {
		this.count2 = count2;
	}
	@ExcelField(title="计算题", align=2, sort=7)
	public int getCount3() {
		return count3;
	}
	public void setCount3(int count3) {
		this.count3 = count3;
	}
	@ExcelField(title="简答题", align=2, sort=8)
	public int getCount4() {
		return count4;
	}
	public void setCount4(int count4) {
		this.count4 = count4;
	}
	@ExcelField(title="多选题", align=2, sort=5)
	public int getCount5() {
		return count5;
	}
	public void setCount5(int count5) {
		this.count5 = count5;
	}
	
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
