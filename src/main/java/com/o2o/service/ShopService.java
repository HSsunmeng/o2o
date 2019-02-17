package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;
import com.o2o.exceptions.ShopOperationException;


import java.io.InputStream;

public interface ShopService {
    /**
     * 根据shopCondition分页返回相应的店铺数量
     * */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
    /**
     * 注册店铺信息。。包括图片处理
     *
     * */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail)throws ShopOperationException;
    /**
     * 更新店铺信息，包括图片处理
     * */
    ShopExecution modifyShop(Shop shop ,ImageHolder thumbnail)throws ShopOperationException;
    /**
     * 根据id获取店铺信息
     * */
    Shop getShopById(Long shopId);
}
