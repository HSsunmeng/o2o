package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.Area;
import com.o2o.entity.PersionInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;


public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;
    @Test
    public void setShopDao(){
        Long shopId=1l;
        Shop shop = shopDao.queryShop(shopId);
        System.out.println(shop.getArea().getAreaId());
        System.out.println(shop.getArea().getAreaName());
    }
    @Test
    @Ignore
    public void shupTest(){
        Shop shop=new Shop();
        Area area=new Area();
        PersionInfo persionInfo=new PersionInfo();
        ShopCategory shopCategory=new ShopCategory();
        area.setAreaId(1);
        persionInfo.setUserId(1l);
        shopCategory.setShopCategoryId(1l);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setOwner(persionInfo);
        shop.setShopName("测试中的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int i=shopDao.insertArea(shop);
        assertEquals(1,i);
        System.out.println(i);

    }

    @Test
    @Ignore
    public void shupUpdateTest(){
        Shop shop=new Shop();
        shop.setShopId(1l);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());

        int i=shopDao.updateShop(shop);
        assertEquals(1,i);
        System.out.println(i);

    }

}
