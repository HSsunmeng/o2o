package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService  {
/**
 *添加商品信息以及图片处理
 * */
ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder>thumbnailList)throws ProductOperationException;
/**
 * 根据id查询商品信息
 * */

Product getQueryProductByidService(Long productId);
/**
 * 修改商品信息
 * */
ProductExecution updateProductService(Product product,ImageHolder thumbnail,List<ImageHolder>thumbnailList)throws ProductOperationException;
/**
 * 查询商品并进行分页操作，商品名（模糊查询），商品状态，店铺id，商品类别
 * */
ProductExecution getProductList(Product product,int pageIndex,int pageSize);
}
