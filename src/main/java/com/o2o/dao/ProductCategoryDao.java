package com.o2o.dao;

import com.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     *
     * 通过shopid查询店铺商品类别
     * */
    List<ProductCategory>queryProductCategoryList(@Param("shopId")Long shopId);
}
