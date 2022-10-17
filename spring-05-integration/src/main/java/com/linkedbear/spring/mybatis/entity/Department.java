package com.linkedbear.spring.mybatis.entity;

import java.util.Objects;

public class Department {
    
    private Integer id;
    
    private String name;
    
    private String tel;
    
    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", name='" + name + '\'' + ", tel='" + tel + '\'' + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
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
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
}
