package com.max.service;

import com.max.domain.PromotionSpace;

import java.util.List;


public interface PromotionSpaceService {
    /**
     * 调用dao完成查询所有广告位列表
     * @return
     */
    List<PromotionSpace> findAllPromotionSpace();

    /**
     * 保存广告位信息
     * 需要注意的是：页面只传递了广告位的名称，需要我们自己补全信息
     * @param promotionSpace
     */
    void savePromotion(PromotionSpace promotionSpace);

    /**
     * 根据id查询广告位信息
     * @param id
     * @return
     */
    PromotionSpace findPromotionSpaceById(Integer id);

    /**
     * 根据广告位id修改广告位信息
     * @param promotionSpace
     */
    void updatePromotionSpace(PromotionSpace promotionSpace);
}
