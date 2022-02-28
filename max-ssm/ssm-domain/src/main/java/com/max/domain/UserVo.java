package com.max.domain;

import java.util.Date;
import java.util.List;

/**
 * 该实体类用于封装页面传递过来的5个参数
 * 当前页
 * 每页显示的条数
 * 手机号码
 * 开始时间
 * 结束时间
 */
public class UserVo {

    private Integer currentPage;
    private Integer pageSize;

    // 先不加@DateTimeFormat注解
    private String username;
    private Date startCreateTime;
    private Date endCreateTime;

    private Integer userId; // 用户id
    private List<Integer> roleIdList;   // 用户关联的角色id信息

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
