package com.max.service.impl;

import com.max.dao.ResourceCategoryMapper;
import com.max.domain.ResourceCategory;
import com.max.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {

    @Autowired
    private ResourceCategoryMapper resourceCategoryMapper;

    /**
     * 查询所有资源分类信息
     * 在资源列表页面中的资源分类中进行回显数据
     * @return
     */
    @Override
    public List<ResourceCategory> findAll() {
        List<ResourceCategory> list = resourceCategoryMapper.findAll();

        return list;
    }

    /**
     * 保存资源分类信息
     * @param resourceCategory
     */
    @Override
    public void saveResourceCategory(ResourceCategory resourceCategory) {
        // 补全信息
        Date date = new Date();
        resourceCategory.setCreatedTime(date);
        resourceCategory.setUpdatedTime(date);
        resourceCategory.setCreatedBy("system");
        resourceCategory.setUpdatedBy("system");

        resourceCategoryMapper.saveResourceCategory(resourceCategory);
    }

    /**
     * 根据id修改资源分类信息
     * @param resourceCategory
     */
    @Override
    public void updateResourceCategory(ResourceCategory resourceCategory) {
        resourceCategory.setUpdatedTime(new Date());
        resourceCategoryMapper.updateResourceCategory(resourceCategory);
    }

    /**
     * 根据id删除资源分类信息
     * @param id
     */
    @Override
    public void deleteResourceCategory(Integer id) {
        resourceCategoryMapper.deleteResourceCategory(id);
    }
}
