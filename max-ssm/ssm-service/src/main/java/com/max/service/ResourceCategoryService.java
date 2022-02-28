package com.max.service;

import com.max.domain.ResourceCategory;

import java.util.List;

public interface ResourceCategoryService {

    /**
     * 查询所有资源分类信息
     * 在资源列表页面中的资源分类中进行回显数据
     * @return
     */
    List<ResourceCategory> findAll();

    /**
     * 保存资源分类信息
     * @param resourceCategory
     */
    void saveResourceCategory(ResourceCategory resourceCategory);

    /**
     * 修改资源分类信息（根据id）
     * @param resourceCategory
     */
    void updateResourceCategory(ResourceCategory resourceCategory);

    /**
     * 根据id删除资源分类信息
     * @param id
     */
    void deleteResourceCategory(Integer id);
}
