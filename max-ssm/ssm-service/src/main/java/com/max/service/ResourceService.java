package com.max.service;


import com.github.pagehelper.PageInfo;
import com.max.domain.Resource;
import com.max.domain.ResourceVo;

public interface ResourceService {
    /**
     * 查询所有资源信息/多条件查询资源信息 并且进行分页查询
     *
     * @param resourceVo
     * @return
     */
    PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo);

    /**
     * 保存资源信息
     *
     * @param resource
     */
    void saveResource(Resource resource);

    /**
     * 修改资源信息
     *
     * @param resource
     */
    void updateResource(Resource resource);

    /**
     * 根据id删除资源信息
     *
     * @param id
     */
    void deleteResource(Integer id);

}

