package com.o2o.controller.shopadmin;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.dto.Result;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductCategoryStateEunm;
import com.o2o.exceptions.ProductCategoryOperationException;
import com.o2o.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryController {
    @Autowired
    /**
     * 某个店铺商品类别管理
     * */
    private ProductCategoryService productCategoryService;
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>>getProductCategoryList(HttpServletRequest request){

        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");

        if (currentShop!=null&&currentShop.getShopId()>0){
            List<ProductCategory> list=productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<>(true,list);
        }else{
            ProductCategoryStateEunm productCategoryStateEunm= ProductCategoryStateEunm.INNER_ERROR;
            return new Result<>(false,productCategoryStateEunm.getStateInfo(),productCategoryStateEunm.getState());
        }

    }
    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    private Map<String ,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){
        Map<String ,Object> modleMap=new HashMap<>();
        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory productCategory:productCategoryList
             ) {
            productCategory.setShopId(currentShop.getShopId());
        }
        if (productCategoryList !=null && productCategoryList.size()>0){
                try{
                    ProductCategoryExecution productCategoryExecution = productCategoryService.batchInsertProductCategory(productCategoryList);
                    if (productCategoryExecution.getState()==ProductCategoryStateEunm.SUCCESS.getState()){
                        modleMap.put("success",true);
                    }else {
                        modleMap.put("success",false);
                        modleMap.put("errMsg",productCategoryExecution.getStateInfo());
                    }
                }catch (ProductCategoryOperationException e){
                    modleMap.put("success",false);
                    modleMap.put("errMsg",e.toString());
                    return modleMap;
                }
        }else {
            modleMap.put("success",false);
            modleMap.put("errMsg","请至少输入一个商品类别");

        }
        return modleMap;
    }
    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
        private Map<String ,Object>removeProductCategory(Long productCategoryId,HttpServletRequest request){
            Map<String ,Object> modleMap=new HashMap<>();
            if (productCategoryId!=null&&productCategoryId>0) {
                try {
                    Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
                    ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategoey(productCategoryId, currentShop.getShopId());
                    if (productCategoryExecution.getState()==ProductCategoryStateEunm.SUCCESS.getState()){
                        modleMap.put("success",true);
                    }else {
                        modleMap.put("success",false);
                        modleMap.put("errMsg",productCategoryExecution.getStateInfo());
                    }

                }catch (ProductCategoryOperationException e){
                    modleMap.put("success",false);
                    modleMap.put("errMsg",e.toString());
                }
            }else {
                modleMap.put("success",false);
                modleMap.put("errMsg","请至少选择一个商品类别");
            }
            return modleMap;
        }
}
