package com.o2o.service;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.ProductCategory;
import com.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 查询某个店铺下所有的商品类别信息
     */
    List<ProductCategory> getProductCategoryList(Long shopId);

    /**
     * 某个店铺下批量添加商品类别
     */
    ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)throws  ProductCategoryOperationException;
    /**
     * 将此类别下的商品类别id置空，在删除此商品类别
     * */
    ProductCategoryExecution deleteProductCategoey(Long productCategoryId,Long shopId)throws ProductCategoryOperationException;
}