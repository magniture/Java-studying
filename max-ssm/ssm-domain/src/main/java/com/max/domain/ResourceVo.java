package com.max.domain;

/**
 * 封装资源列表功能前端页面传递过来的参数
 */
public class ResourceVo {
    // 当前页
    private Integer currentPage;

    // 每页显示的条数
    private Integer pageSize;

    // 资源名称
    private String name;

    // 资源路径
    private String url;

    // 资源分类id
    private Integer categoryId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ResourceVo{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
