package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsertProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    @Test
    public void insertAproductTest(){
        Shop shop=new Shop();
        ProductCategory productCategory=new ProductCategory();
        shop.setShopId(1l);
        productCategory.setProductCategoryId(1l);
        Product product=new Product();
        product.setProductName("测试1");
        product.setProductDesc("测试描述");
        product.setImgAddr("imgtest");
        product.setPriority(1);
        product.setEnableStatus(1);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setProductCategory(productCategory);
        Product product1=new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试描述");
        product1.setImgAddr("imgtest");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop);
        product1.setProductCategory(productCategory);
        int i = productDao.insertProduct(product);
        assertEquals(1,i);
        int x=productDao.insertProduct(product1);
    assertEquals(1,x);
    }
}
