package com.o2o.service;

import com.o2o.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService  {
    /**
     * 查询某个店铺下所有的商品类别信息
     *
     * */
    List<ProductCategory>getProductCategoryList(Long shopId);
    }
