package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.PersionInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.enums.ShopStateEunm;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
    @Test
    public void getShopService() throws FileNotFoundException {
        Shop shop=new Shop();
        shop.setShopId(1l);
        shop.setShopName("修改后的名称");
        File file =new File("F:\\image\\baobao.jpg");
        InputStream inputStream=new FileInputStream(file);
        ShopExecution shopExecution = shopService.modifyShop(shop, inputStream, "baobao.jpg");
        System.out.println("图片地址为："+shopExecution.getShop().getShopImg());
    }
    @Test
    @Ignore
    public void shopAddr() throws FileNotFoundException {
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
        shop.setShopName("测试中的店铺3");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");

        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEunm.CHECK.getState());
        shop.setAdvice("审核中");

        File file=new File("F:\\image\\xiaozhu.jpg");
        InputStream inputStream=new FileInputStream(file);
        ShopExecution shopExecution = shopService.addShop(shop,inputStream,file.getName());
        assertEquals(ShopStateEunm.CHECK.getState(),shopExecution.getState());
    }
}
