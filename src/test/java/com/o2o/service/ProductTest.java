package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductStateEunm;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductTest extends BaseTest {
    @Autowired
    private  ProductService productService;
    @Test
    public void getAProductServiceTest() throws FileNotFoundException {
        Product product=new Product();
        Shop shop=new Shop();
        ProductCategory productCategory=new ProductCategory();
        shop.setShopId(1l);
        productCategory.setProductCategoryId(1l);
        product.setProductName("测试商品1");
        product.setProductDesc("测试描述1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEunm.SUCCESS.getState());
        product.setShop(shop);
        product.setProductCategory(productCategory);
        File file=new File("F:\\image\\baobao.jpg");
        InputStream inputStream=new FileInputStream(file);
        ImageHolder imageHolder=new ImageHolder(file.getName(),inputStream);
        File file1=new File("F:\\image\\baobao.jpg");
        File file2=new File("F:\\image\\baobao.jpg");
        InputStream inputStream1=new FileInputStream(file1);
        InputStream inputStream2=new FileInputStream(file2);
        List<ImageHolder>imageHolderList=new ArrayList<>();
                imageHolderList.add(new ImageHolder(file1.getName(),inputStream1));
                imageHolderList.add(new ImageHolder(file2.getName(),inputStream2));
        ProductExecution productExecution = productService.addProduct(product, imageHolder, imageHolderList);
        assertEquals(ProductStateEunm.SUCCESS.getState(),productExecution.getState());

    }
}
