package com.max.controller;

import com.max.domain.ResourceCategory;
import com.max.domain.ResponseResult;
import com.max.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ResourceCategory")
public class ResourceCategoryController {

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @RequestMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory() {
        List<ResourceCategory> list = resourceCategoryService.findAll();

        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有资源分类信息成功！", list);
        return responseResult;
    }

    /**
     * 保存或者修改分类信息
     * @return
     */
    @RequestMapping("/saveOrUpdateResourceCategory")
    public ResponseResult saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory) {
        if (resourceCategory.getId() == null) {
            // 新增操作
            resourceCategoryService.saveResourceCategory(resourceCategory);
            return new ResponseResult(true,200,"保存资源分类信息成功！",null);

        } else {
            // 修改操作
            resourceCategoryService.updateResourceCategory(resourceCategory);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改资源分类信息成功！", null);
            return responseResult;
        }

    }

    @RequestMapping("/deleteResourceCategory")
    public ResponseResult deleteResourceCategory(Integer id) {
        resourceCategoryService.deleteResourceCategory(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "根据id删除资源分类信息成功！", null);
        return responseResult;
    }
}
