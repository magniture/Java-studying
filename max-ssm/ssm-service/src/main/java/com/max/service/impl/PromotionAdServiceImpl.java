package com.max.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.dao.PromotionAdMapper;
import com.max.domain.PromotionAd;
import com.max.domain.PromotionAdVo;
import com.max.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PromotionAdServiceImpl implements PromotionAdService {

    @Autowired
    private PromotionAdMapper promotionAdMapper;

    /**
     * 使用分页插件，对广告列表进行分页查询
     * @param promotionAdVo
     * @return
     */
    public PageInfo<PromotionAd> findPromotionAdByPage(PromotionAdVo promotionAdVo) {
        // 向分页组件中传递当前页，和每页显示的条数
        PageHelper.startPage(promotionAdVo.getCurrentPage(),promotionAdVo.getPageSize());

        List<PromotionAd> allPromotionAdAndPromotionSpace = promotionAdMapper.findAllPromotionAdAndPromotionSpace();
        // 创建一个pageInfo对象,然后使用有参构造将集合放入对象中，这样分页的一些其他属性就都设置好了
        PageInfo<PromotionAd> pageInfo = new PageInfo<>(allPromotionAdAndPromotionSpace);
        return pageInfo;
    }

    /**
     * 新建广告信息
     * @param promotionAd
     */
    @Override
    public void savePromotionAd(PromotionAd promotionAd) {
        // 补充信息
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);

        // 调用dao完成插入操作
        promotionAdMapper.savePromotionAd(promotionAd);
    }

    /**
     * 广告信息回显
     * @param id
     * @return
     */
    @Override
    public PromotionAd findPromotionAdById(Integer id) {
        PromotionAd promotionAd = promotionAdMapper.findPromotionAdById(id);
        return promotionAd;
    }

    /**
     * 根据id修改广告信息
     * @param promotionAd
     */
    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {
        // 补全信息
        promotionAd.setUpdateTime(new Date());

        promotionAdMapper.updatePromotionAd(promotionAd);
    }

    /**
     * 根据id修改广告状态
     * 需要注意的是：在这里需要将参数封装到promotionAd对象上
     * @param id
     * @param status
     */
    @Override
    public void updatePromotionAdStatus(Integer id, Integer status) {
        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setId(id);
        promotionAd.setStatus(status);
        promotionAd.setUpdateTime(new Date());


        promotionAdMapper.updatePromotionAdStatus(promotionAd);
    }
}
