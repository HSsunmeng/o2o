package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void setShopCategoryDao(){
        ShopCategory shopCategory=new ShopCategory();


        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(shopCategory);
        assertEquals(1,shopCategories.size());
    }
}
