package com.javapandeng.po;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private Integer id;
    /**
     * 姓名
     */
    private  String name;
    /**
     * 内容
     */
    private String content;

    /**
     * 手机号
     */
    private String phone;

    public Message() {
    }

    public Message(Integer id, String name, String content, String phone) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
