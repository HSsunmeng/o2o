package com.o2o.dao;

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
}
