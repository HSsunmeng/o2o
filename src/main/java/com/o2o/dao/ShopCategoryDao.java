package com.o2o.dao;

import com.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 店铺类别
 * */
public interface ShopCategoryDao {
    List<ShopCategory>queryShopCategory(@Param("shopCategoryContent")ShopCategory shopCategoryContent);
}
