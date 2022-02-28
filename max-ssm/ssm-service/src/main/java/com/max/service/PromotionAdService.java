package com.max.service;

import com.github.pagehelper.PageInfo;
import com.max.domain.PromotionAd;
import com.max.domain.PromotionAdVo;

import java.util.List;

/**
 * 广告 业务逻辑处理层
 */

public interface PromotionAdService {
    /**
     * 分页查询广告数据
     * 这里要进行分页的一些逻辑操作
     * @param promotionAdVo
     * @return
     */
    PageInfo<PromotionAd> findPromotionAdByPage(PromotionAdVo promotionAdVo);

    /**
     * 保存广告信息
     * @param promotionAd
     */
    void savePromotionAd(PromotionAd promotionAd);

    /**
     * 根据id查询广告信息
     * 目的是为了在修改广告信息之前进行数据回显
     * @param id)
     * @return
     */
    PromotionAd findPromotionAdById(Integer id);

    /**
     * 根据id修改广告信息
     * @param promotionAd
     */
    void updatePromotionAd(PromotionAd promotionAd);

    /**
     * 根据id修改广告状态
     * 需要注意的是：在这里需要将参数封装到promotionAd对象上
     * @param id
     * @param status
     */
    void updatePromotionAdStatus(Integer id,Integer status);
}
