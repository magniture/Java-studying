package com.javapandeng.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论
 */
public class Comment implements Serializable {

    private Integer id;

    private Integer userId;

    private User user;

    private Integer itemId;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date addTime;


    public Comment() {
    }

    public Comment(Integer id, Integer userId, Integer itemId, String content, Date addTime) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.content = content;
        this.addTime = addTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", content='" + content + '\'' +
                ", addTime=" + addTime +
                '}';
    }
}
