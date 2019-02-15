package com.o2o.dao;


import com.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ShopDao {
    /**
     * 查询数据总数
     * */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
    /**
     * 分页查询店铺
     * @param shopCondition 查询条件{店铺名（模糊），店铺状态，店铺类别，区域id，owner}
     * @param rowIndex 起始条件
     * @param  pageSize 查询的条数
     * */
    List<Shop>queryShaoList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
    /**
     * 查询店铺信息
     * */
    Shop queryShop(@Param("shopId") Long shopId);
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
