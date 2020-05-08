package com.thinkgem.jeesite.modules.resource.entity;

import java.util.Date;

public class LVFeedback  {
    private Integer id;

    private String userId;

    private String userName;

    private String email;

    private String phone;

    private String content;

    private Date createTime;

    private Short comefrom;

    private Byte status;

    private String memo;

    private String lvCourseId;

    private Long score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getComefrom() {
        return comefrom;
    }

    public void setComefrom(Short comefrom) {
        this.comefrom = comefrom;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLvCourseId() {
        return lvCourseId;
    }

    public void setLvCourseId(String lvCourseId) {
        this.lvCourseId = lvCourseId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}