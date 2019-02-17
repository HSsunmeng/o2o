package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.ProductCategory;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testCDeleteProductCategory(){
       Long shopId=2l;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory p:productCategoryList
             ) {
            if ("新增1".equals(p.getProductCategoryName())&& "新增2".equals(p.getProductCategoryName())){
                int i = productCategoryDao.deleteProductCategory(p.getProductCategoryId(), shopId);
                assertEquals(2,i);
            }
        }
    }
    @Test

    public void setAProductCategoryDao(){

        ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryName("新增1");
        productCategory.setPriority(20);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(2l);

        ProductCategory productCategory1=new ProductCategory();
        productCategory1.setProductCategoryName("新增2");
        productCategory1.setPriority(15);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(2l);
        List<ProductCategory> productCategoryList=new ArrayList<>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory1);
       int i= productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2,i);

    }



    @Test

    public void setBProductCategoryDao1(){
        Long shopId=1l;
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("默认的测试类别="+productCategories.size());

    }
}
