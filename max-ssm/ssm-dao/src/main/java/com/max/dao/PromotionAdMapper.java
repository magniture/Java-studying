package com.max.dao;

import com.max.domain.PromotionAd;

import java.util.List;

/**
 * 广告 dao层
 */
public interface PromotionAdMapper {
    /**
     * 查询所有广告信息
     * 需要注意的是：在这里要进行嵌套查询（根据广告表中的spaceId外键查询广告位信息）
     * 根据id查询广告位信息在广告位dao层已经编写了，所以使用嵌套查询
     * @return
     */
    List<PromotionAd> findAllPromotionAdAndPromotionSpace();

    /**
     * 添加广告
     * @param promotionAd
     */
    void savePromotionAd(PromotionAd promotionAd);

    /**
     * 根据id查询广告信息进行数据的回显
     * 这个工作是修改广告信息的前提操作
     * @param id
     * @return
     */
    PromotionAd findPromotionAdById(Integer id);

    /**
     * 修改广告信息
     * @param promotionAd
     */
    void updatePromotionAd(PromotionAd promotionAd);

    /**
     * 修改广告状态：根据id修改
     * @param promotionAd
     */
    void updatePromotionAdStatus(PromotionAd promotionAd);
}
