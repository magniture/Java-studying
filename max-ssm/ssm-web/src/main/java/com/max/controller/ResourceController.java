package com.max.controller;
import com.github.pagehelper.PageInfo;
import com.max.domain.Resource;
import com.max.domain.ResourceVo;
import com.max.domain.ResponseResult;
import com.max.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查询所有资源信息/多条件查询资源信息 并且进行分页查询
     * @param resourceVo
     * @return
     */
    @RequestMapping("/findAllResource")
    public ResponseResult findAllResourceByPageAndCondition(@RequestBody ResourceVo resourceVo) {
        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourceVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有资源信息成功|多条件查询资源信息&分页查询成功！", pageInfo);
        return responseResult;
    }

    /**
     * 点击添加按钮，向后台插入一条新的资源数据
     * @param resource
     * @return
     */
    @RequestMapping("/saveOrUpdateResource")
    public ResponseResult saveOrUpdateResource(@RequestBody Resource resource) {
        if (resource.getId() == null) {
            // 新增操作
            resourceService.saveResource(resource);

            ResponseResult responseResult = new ResponseResult(true, 200, "新增资源成功！", null);
            return responseResult;
        } else {
            // 修改操作
            resourceService.updateResource(resource);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改资源信息成功！", null);
            return responseResult;
        }
    }

    /**
     * 点击删除按钮：根据id删除某一条资源信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteResource")
    public ResponseResult deleteResource(Integer id) {
        resourceService.deleteResource(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "删除资源信息成功!", null);
        return responseResult;
    }
}
