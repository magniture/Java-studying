package com.max.dao;

import com.max.domain.ResourceCategory;

import java.util.List;

public interface ResourceCategoryMapper {
    /**
     * 查询所有资源分类信息
     * 在资源列表页面中的资源分类中进行回显数据
     * @return
     */
    List<ResourceCategory> findAll();

    /**
     * 新增资源分类信息
     * @param resourceCategory
     */
    void saveResourceCategory(ResourceCategory resourceCategory);

    /**
     * 修改资源分类信息
     * @param resourceCategory
     */
    void updateResourceCategory(ResourceCategory resourceCategory);

    /**
     * 根据id删除资源分类信息
     * @param id
     */
    void deleteResourceCategory(Integer id);
}


