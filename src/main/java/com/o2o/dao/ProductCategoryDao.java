package com.o2o.dao;

import com.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 通过shopid查询店铺商品类别
     */
    List<ProductCategory> queryProductCategoryList(@Param("shopId") Long shopId);

    /**
     * 批量添加商品类别，返回受影响的行
     */
    int batchInsertProductCategory(@Param("productCategoryList") List<ProductCategory> productCategoryList);
/**
 * 通过指定id删除商品类别
 * */
        int deleteProductCategory(@Param("productCategoryId")Long productCategoryId,@Param("shopId") Long shopId);
}