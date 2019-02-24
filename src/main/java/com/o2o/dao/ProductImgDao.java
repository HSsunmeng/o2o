package com.o2o.dao;

import com.o2o.entity.Product;
import com.o2o.entity.ProductImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 详情页图片
 * */
public interface ProductImgDao {
    /**
     * 批量添加详情图片
     * */
    int batchInsertProductImg(@Param("productImgList") List<ProductImg>productImgList);
    /**
     * 删除图片的方法
     * */
    int deleteProductImg(Long productId);
    /**
     * 查询图片详情
     * */
    List<ProductImg> queryProductImgList(Long productId);
}
