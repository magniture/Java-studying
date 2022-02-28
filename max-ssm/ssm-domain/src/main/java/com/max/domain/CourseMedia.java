package com.max.domain;

import java.util.Date;

/**
 * 课程媒体表
 * */
public class CourseMedia {

    //id
    private int id;
    
    //课程id
    private int courseId;

    //章节id
    private int sectionId;

    //课时id
    private int lessonId;

    //封面图URL
    private String coverImageUrl;

    //时长
    private String duration;

    //媒体资源文件对应的EDK
    private String fileEdk;

    //文件大小MB
    private double fileSize;

    //文件名称
    private String fileName;

    //媒体资源文件对应的DK
    private String fileDk;

    //时长，秒数
    private int durationNum;

    //媒体资源文件ID
    private String fileId;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //是否删除，0未删除，1删除
    private int isDel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileEdk() {
        return fileEdk;
    }

    public void setFileEdk(String fileEdk) {
        this.fileEdk = fileEdk;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDk() {
        return fileDk;
    }

    public void setFileDk(String fileDk) {
        this.fileDk = fileDk;
    }

    public int getDurationNum() {
        return durationNum;
    }

    public void setDurationNum(int durationNum) {
        this.durationNum = durationNum;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
