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
}
