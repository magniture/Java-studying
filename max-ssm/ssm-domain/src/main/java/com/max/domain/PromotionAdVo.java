package com.max.domain;

/**
 * 该类用于封装页面传递的分页参数：页面需要传递当前页，每页显示的条数
 */
public class PromotionAdVo {
    // 当前页
    private Integer currentPage;

    // 当前页显示的条数
    private Integer pageSize;

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

}
