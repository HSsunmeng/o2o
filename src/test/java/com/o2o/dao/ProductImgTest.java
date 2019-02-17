package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
    @Test
    public void batchAProductImgTest(){
        ProductImg productImg=new ProductImg();
        productImg.setImgAddr("测试图片地址1");
        productImg.setImgDesc("测试图片描述1");
        productImg.setCreateTime(new Date());
        productImg.setPriority(1);
        productImg.setProductId(1l);
        ProductImg productImg1=new ProductImg();
        productImg1.setImgAddr("测试图片地址2");
        productImg1.setImgDesc("测试图片描述2");
        productImg1.setCreateTime(new Date());
        productImg1.setPriority(2);
        productImg1.setProductId(1l);
        List<ProductImg> list=new ArrayList();
        list.add(productImg);
        list.add(productImg1);
        int i = productImgDao.batchInsertProductImg(list);
        assertEquals(2,i);
    }
}
