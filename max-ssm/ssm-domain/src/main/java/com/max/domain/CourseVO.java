package com.max.domain;

import java.util.Date;

/*
* 在表现层对象：在表现层接收前台参数的
* */
public class CourseVO {
    //主键
    private Integer id;

    //课程名称   这里体现了在mybatis映射配置文件中配置的自动驼峰命名规则的映射
    // 数据库course表中课程的名称是 course_Name ，而实体类Course中表明课程名称的属性名是courseName
    // 经过自动驼峰命名规则的映射也是可以将数据库查询出来的信息自动封装到这个实体类上
    private String courseName;

    //课程一句话简介
    private String brief;

    //原价
    private double price;

    //原价标签
    private String priceTag;

    //优惠价
    private double discounts;

    //优惠价标签
    private String discountsTag;

    //课程内容markdown
    private String courseDescriptionMarkDown;

    //课程描述
    private String courseDescription;

    //课程分享图片url
    private String courseImgUrl;

    //是否新品
    private int isNew;

    //广告语
    private String isNewDes;

    //最后操作者
    private int lastOperatorId;

    //是否删除
    private int isDel;

    //总时长
    private int totalDuration;

    //课程列表展示图片
    private String courseListImg;

    //课程状态，0-草稿，1-上架
    private int status;

    //课程排序
    private int sortNum;

    //课程预览第一个字段
    private String previewFirstField;

    //课程预览第二个字段
    private String previewSecondField;

    //销量
    private int sales;

    private String teacherName;

    private String position;

    private String description;

    public CourseVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(String priceTag) {
        this.priceTag = priceTag;
    }

    public double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(double discounts) {
        this.discounts = discounts;
    }

    public String getDiscountsTag() {
        return discountsTag;
    }

    public void setDiscountsTag(String discountsTag) {
        this.discountsTag = discountsTag;
    }

    public String getCourseDescriptionMarkDown() {
        return courseDescriptionMarkDown;
    }

    public void setCourseDescriptionMarkDown(String courseDescriptionMarkDown) {
        this.courseDescriptionMarkDown = courseDescriptionMarkDown;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseImgUrl() {
        return courseImgUrl;
    }

    public void setCourseImgUrl(String courseImgUrl) {
        this.courseImgUrl = courseImgUrl;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getIsNewDes() {
        return isNewDes;
    }

    public void setIsNewDes(String isNewDes) {
        this.isNewDes = isNewDes;
    }

    public int getLastOperatorId() {
        return lastOperatorId;
    }

    public void setLastOperatorId(int lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getCourseListImg() {
        return courseListImg;
    }

    public void setCourseListImg(String courseListImg) {
        this.courseListImg = courseListImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getPreviewFirstField() {
        return previewFirstField;
    }

    public void setPreviewFirstField(String previewFirstField) {
        this.previewFirstField = previewFirstField;
    }

    public String getPreviewSecondField() {
        return previewSecondField;
    }

    public void setPreviewSecondField(String previewSecondField) {
        this.previewSecondField = previewSecondField;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CourseVO{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", brief='" + brief + '\'' +
                ", price=" + price +
                ", priceTag='" + priceTag + '\'' +
                ", discounts=" + discounts +
                ", discountsTag='" + discountsTag + '\'' +
                ", courseDescriptionMarkDown='" + courseDescriptionMarkDown + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseImgUrl='" + courseImgUrl + '\'' +
                ", isNew=" + isNew +
                ", isNewDes='" + isNewDes + '\'' +
                ", lastOperatorId=" + lastOperatorId +
                ", isDel=" + isDel +
                ", totalDuration=" + totalDuration +
                ", courseListImg='" + courseListImg + '\'' +
                ", status=" + status +
                ", sortNum=" + sortNum +
                ", previewFirstField='" + previewFirstField + '\'' +
                ", previewSecondField='" + previewSecondField + '\'' +
                ", sales=" + sales +
                ", teacherName='" + teacherName + '\'' +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public CourseVO(int id, String courseName, String brief, double price, String priceTag, double discounts, String discountsTag, String courseDescriptionMarkDown, String courseDescription, String courseImgUrl, int isNew, String isNewDes, int lastOperatorId, int isDel, int totalDuration, String courseListImg, int status, int sortNum, String previewFirstField, String previewSecondField, int sales, String teacherName, String position, String description) {
        this.id = id;
        this.courseName = courseName;
        this.brief = brief;
        this.price = price;
        this.priceTag = priceTag;
        this.discounts = discounts;
        this.discountsTag = discountsTag;
        this.courseDescriptionMarkDown = courseDescriptionMarkDown;
        this.courseDescription = courseDescription;
        this.courseImgUrl = courseImgUrl;
        this.isNew = isNew;
        this.isNewDes = isNewDes;
        this.lastOperatorId = lastOperatorId;
        this.isDel = isDel;
        this.totalDuration = totalDuration;
        this.courseListImg = courseListImg;
        this.status = status;
        this.sortNum = sortNum;
        this.previewFirstField = previewFirstField;
        this.previewSecondField = previewSecondField;
        this.sales = sales;
        this.teacherName = teacherName;
        this.position = position;
        this.description = description;
    }
}
