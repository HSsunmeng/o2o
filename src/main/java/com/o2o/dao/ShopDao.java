package com.o2o.dao;


import com.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

public interface ShopDao {
    /**
     * 插入店铺信息
     * */
    int insertArea( Shop shop);
    /**
     *
     * 更新店铺信息
     * */
    int updateShop( Shop shop);
}
