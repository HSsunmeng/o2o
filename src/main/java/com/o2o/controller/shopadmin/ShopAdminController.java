package com.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * 路由管理
 * */
@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {
    @RequestMapping(value = "/shopopreation")
    public String shopOpreation(){
        return"shop/shopopreation";
    }
    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return"shop/shoplist";
    }
    @RequestMapping(value = "/shopmanagement")
    public String shopManagementInfo(){
        return"shop/shopmanagement";
    }
        @RequestMapping(value = "/productcategorymanagement")
    public String productcategorymanagement(){
        return "productcategory/productcategorymanagement";
    }
}
