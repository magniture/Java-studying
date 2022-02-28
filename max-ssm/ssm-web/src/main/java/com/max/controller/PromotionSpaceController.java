package com.max.controller;

import com.max.domain.PromotionSpace;
import com.max.domain.ResponseResult;
import com.max.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 广告位controller
 */
@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {

    @Autowired
    private PromotionSpaceService promotionSpaceService;

    /**
     * 查询所有的广告位信息，就是在页面上展示广告位列表
     * @return
     */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace() {
        // 调用service
        List<PromotionSpace> promotionSpaceList = promotionSpaceService.findAllPromotionSpace();

        // 根据接口文档响应数据
        ResponseResult responseResult = new ResponseResult(true, 200, "查询广告位列表成功！", promotionSpaceList);
        return responseResult;
    }

    /**
     * 保存或修改广告位信息
     * 修改广告位信息会传递id到controller
     * @param promotionSpace
     * @return
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace) {
        if (promotionSpace.getId() == null) {
            promotionSpaceService.savePromotion(promotionSpace);

            ResponseResult responseResult = new ResponseResult(true, 200, "新建广告位信息成功！", null);
            return responseResult;
        } else {
            promotionSpaceService.updatePromotionSpace(promotionSpace);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改广告位信息成功！", null);
            return responseResult;
        }
    }

    /**
     * 广告位信息回显功能
     * @param id
     * @return
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(Integer id) {
        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "回显广告位信息成功！", promotionSpace);
        return responseResult;
    }

}