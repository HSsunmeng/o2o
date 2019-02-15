package com.o2o.controller.shopadmin;

import com.o2o.dto.Result;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductCategoryStateEunm;
import com.o2o.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;


@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>>getProductCategoryList(HttpServletRequest request){

        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory>list=null;
        if (currentShop!=null&&currentShop.getShopId()>0){
            list=productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true,list);
        }else{
            ProductCategoryStateEunm productCategoryStateEunm= ProductCategoryStateEunm.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,productCategoryStateEunm.getStateInfo(),productCategoryStateEunm.getState());
        }

    }

}