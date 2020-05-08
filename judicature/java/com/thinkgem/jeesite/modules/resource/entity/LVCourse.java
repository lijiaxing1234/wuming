package com.thinkgem.jeesite.modules.resource.entity;

import java.util.Date;

public class LVCourse {
    private Long id;

    private String lvCourseId;

    private String courseId;
    
    private String courseTitle;

    private Date createTime;

    private double coursePrice;

    private Integer recommend;
    
    private String img;
    
    private int pumkinPay;
    
    private int otherPay;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLvCourseId() {
        return lvCourseId;
    }

    public void setLvCourseId(String lvCourseId) {
        this.lvCourseId = lvCourseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }
    
    
    public String getCourseTitle() {
		return courseTitle;
	}
    public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}


	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getPumkinPay() {
		return pumkinPay;
	}

	public void setPumkinPay(int pumkinPay) {
		this.pumkinPay = pumkinPay;
	}

	public int getOtherPay() {
		return otherPay;
	}

	public void setOtherPay(int otherPay) {
		this.otherPay = otherPay;
	}
    
}