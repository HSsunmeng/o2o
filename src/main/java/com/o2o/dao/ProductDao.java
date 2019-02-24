package com.o2o.dao;

import com.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品详情页
 * */
public interface ProductDao {
    /**
     * 添加商品详情
     * */
    int insertProduct(Product product);
    /**
     * 根据id查看商品
     * */
    Product queryProductByidDao(Long productId);
    /**
     * 修改商品信息
     * */
    int updateProductDao(Product product);
        /**
         * 查询商品列表并分页显示，可输入条件：商品名（模糊查询），商品状态，店铺id，商品类别
         *
         *
         * */
        List<Product>queryProductList(@Param("productCondition")Product productCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
        /**
         * 先查询商品总数
         * */
        int queryProductCount(@Param("productCondition") Product productCondition);
        /**
         * 删除商品之前，先将商品类别id置空
         * */
        int updateProdactCategoryToNull(Long productCategoryId);
}
