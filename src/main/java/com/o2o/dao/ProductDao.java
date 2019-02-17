package com.o2o.dao;

import com.o2o.entity.Product;

/**
 * 商品详情页
 * */
public interface ProductDao {
    /**
     * 添加商品详情
     * */
    int insertProduct(Product product);
}
