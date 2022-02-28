package com.max.domain;

import java.util.Date;
import java.util.List;

/**
 * 章节类
 * */
public class CourseSection {

    //id
    private Integer id;

    //课程id
    private int courseId;

    //章节名
    private String sectionName;

    //章节描述
    private String description;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //是否删除
    private int isDe;

    //排序
    private int orderNum;

    //状态
    private int status;

    //课时集合
    private List<CourseLesson> lessonList;

    public List<CourseLesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<CourseLesson> lessonList) {
        this.lessonList = lessonList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDe() {
        return isDe;
    }

    public void setIsDe(int isDe) {
        this.isDe = isDe;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}