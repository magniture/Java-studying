package org.example;

import org.example.store.ICommodity;
import org.example.store.impl.CardCommodityService;
import org.example.store.impl.CouponCommodityService;
import org.example.store.impl.GoodsCommodityService;


public class StoreFactory {

    public ICommodity getCommodityService(Integer commodityType) {
        if (null == commodityType) return null;
        if (1 == commodityType) return (ICommodity) new CouponCommodityService();
        if (2 == commodityType) return new GoodsCommodityService();
        if (3 == commodityType) return new CardCommodityService();
        throw new RuntimeException("不存在的商品服务类型");
    }

}
