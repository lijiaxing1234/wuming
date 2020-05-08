package com.thinkgem.jeesite.modules.resource.entity;

public class LVCourseImplement {
    private Long id;

    private Short type;

    private String content;

    private Integer seq;

    private String lvCourseId;

    private Short lvCourseCate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getLvCourseId() {
        return lvCourseId;
    }

    public void setLvCourseId(String lvCourseId) {
        this.lvCourseId = lvCourseId;
    }

    public Short getLvCourseCate() {
        return lvCourseCate;
    }

    public void setLvCourseCate(Short lvCourseCate) {
        this.lvCourseCate = lvCourseCate;
    }
}