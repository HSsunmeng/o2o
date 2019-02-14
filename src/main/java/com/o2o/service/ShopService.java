package com.o2o.service;

import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;
import com.o2o.exceptions.ShopOperationException;


import java.io.InputStream;

public interface ShopService {
    /**
     * 注册店铺信息。。包括图片处理
     *
     * */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName)throws ShopOperationException;
    /**
     * 更新店铺信息，包括图片处理
     * */
    ShopExecution modifyShop(Shop shop ,InputStream shopImgInputStream,String fileName)throws ShopOperationException;
    /**
     * 根据id获取店铺信息
     * */
    Shop getShopById(Long shopId);
}
