package com.max.service.impl;

import com.max.dao.PromotionSpaceMapper;
import com.max.domain.PromotionSpace;
import com.max.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class PromotionSpaceServiceImpl implements PromotionSpaceService {
    @Autowired
    private PromotionSpaceMapper promotionSpaceMapper;
    /**
     * 调用dao查询所有广告位列表
     * @return
     */
    @Override
    public List<PromotionSpace> findAllPromotionSpace() {
        List<PromotionSpace> promotionSpaceList = promotionSpaceMapper.findAllPromotionSpace();
        return promotionSpaceList;
    }

    /**
     * 保存广告位信息
     * 需要注意的是：页面只传递了广告位的名称，需要我们自己补全信息
     * @param promotionSpace
     */
    @Override
    public void savePromotion(PromotionSpace promotionSpace) {
        // 使用UUID工具类生成一个随机数
        promotionSpace.setSpaceKey(UUID.randomUUID().toString());
        Date date = new Date();
        promotionSpace.setCreateTime(date);
        promotionSpace.setUpdateTime(date);
        // 0表示逻辑上没有被删除，0 也是默认值
        promotionSpace.setIsDel(0);

        promotionSpaceMapper.savePromotionSpace(promotionSpace);


    }

    /**
     * 根据id查询广告位信息
     * @param id
     * @return
     */
    @Override
    public PromotionSpace findPromotionSpaceById(Integer id) {
        PromotionSpace promotionSpace = promotionSpaceMapper.findPromotionSpaceById(id);
        return promotionSpace;
    }

    /**
     * 根据广告位id修改广告位信息
     * @param promotionSpace
     */
    @Override
    public void updatePromotionSpace(PromotionSpace promotionSpace) {
        // 需要补全信息
        promotionSpace.setUpdateTime(new Date());

        promotionSpaceMapper.updatePromotionSpace(promotionSpace);
    }
}
