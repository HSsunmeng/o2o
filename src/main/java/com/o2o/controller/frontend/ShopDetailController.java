package com.o2o.controller.frontend;

import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.service.ProductCategoryService;
import com.o2o.service.ProductService;
import com.o2o.service.ShopService;
import com.o2o.util.HttpServletRequstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
/**
 * 获取店铺信息，以及店铺下的商品类别列表
 * */
@RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
@ResponseBody
private Map<String,Object>listShopDetailPageInfo(HttpServletRequest request){
    Map<String,Object> modleMap=new HashMap<>();
    //获取前台shopid
    Long shopId= HttpServletRequstUtil.getLong(request,"shopId");
    Shop shop=null;
    List<ProductCategory>productCategoryList=null;
    if (shopId!= -1){
        //获取shopid的店铺信息
        shop = shopService.getShopById(shopId);
        //获取店铺下面的商品类别信息
        productCategoryList = productCategoryService.getProductCategoryList(shopId);
        modleMap.put("shop",shop);
        modleMap.put("productCategoryList",productCategoryList);
        modleMap.put("success",true);
    }else {
        modleMap.put("success",false);
        modleMap.put("errMsg","listShopDetailPageInfo shopId and productCategoryList");
    }
    return modleMap;

}
/**
 * 依据条件查询分页列出店铺下的所有商品信息
 * */
@RequestMapping(value = "/listproductsbyshop",method = RequestMethod.GET)
@ResponseBody
        private Map<String,Object>listProductsByShop(HttpServletRequest request){
            Map<String,Object> modleMap=new HashMap<>();
            //获取页码
            int pageIndex=HttpServletRequstUtil.getInt(request,"pageIndex");
            //获取每页的显示条数
            int pageSize=HttpServletRequstUtil.getInt(request,"pageSize");
            //获取店铺的shopid
            Long shopId=HttpServletRequstUtil.getLong(request,"shopId");
            if (pageIndex>-1 && pageSize>-1 && shopId>-1l){
                //获取商品类别id
                Long productCategoryId=HttpServletRequstUtil.getLong(request,"productCategoryId");
                //模糊获取商品名字
                String productName=HttpServletRequstUtil.getString(request,"productName");
                Product productCondition=compaerToProductCondition(shopId,productCategoryId,productName);
                //按照传入的查询条件以及分页信息返回相应的商品列表以及总数
                ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
                modleMap.put("productList",productExecution.getProductList());
                modleMap.put("count",productExecution.getCount());
                modleMap.put("success",true);
            }else{
                modleMap.put("success",false);
                modleMap.put("errMsg","listProductsByShop productExecution pageIndex pageSize");
            }
            return modleMap;
        }

    private Product compaerToProductCondition(Long shopId, Long productCategoryId, String productName) {
        Product productCondition=new Product();
        Shop shop=new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId!=-1l){
            ProductCategory productCategory=new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName!=null){
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
        }
}
