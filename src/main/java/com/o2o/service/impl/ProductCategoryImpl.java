package com.o2o.service.impl;

import com.o2o.dao.ProductCategoryDao;
import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.ProductCategory;
import com.o2o.enums.ProductCategoryStateEunm;
import com.o2o.exceptions.ProductCategoryOperationException;
import com.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductCategoryImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size()>0){
            try{
                int category = productCategoryDao.batchInsertProductCategory(productCategoryList);
                    if (category<=0){
                        throw new ProductCategoryOperationException("商品类别创建失败");
                    }else{
                        return new ProductCategoryExecution(ProductCategoryStateEunm.SUCCESS);
                    }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchInsertProductCategory error"+e.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEunm.EMPTY_LIST);
        }

    }

    @Override
    @Transactional//添加事物
    public ProductCategoryExecution deleteProductCategoey(Long productCategoryId, Long shopId) throws ProductCategoryOperationException {
        //TODO将此类别下的商品id置空
        if (productCategoryId!=null&&shopId!=null) {
          try{
              int i = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
              if (i>0){
                return new ProductCategoryExecution(ProductCategoryStateEunm.SUCCESS);
              }else {
                  throw new ProductCategoryOperationException("商品类别删除失败");
              }
          }catch (Exception e){
              throw new ProductCategoryOperationException("deleteProductCategoey error"+e.getMessage());
          }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEunm.INNER_ERROR);
        }

    }
}
