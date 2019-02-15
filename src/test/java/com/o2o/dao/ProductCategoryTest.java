package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void setProductCategoryDao1(){
        Long shopId=1l;
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("默认的测试类别="+productCategories.size());

    }
}
