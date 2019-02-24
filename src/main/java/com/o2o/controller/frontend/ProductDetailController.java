package com.o2o.controller.frontend;

import com.o2o.entity.Product;
import com.o2o.service.ProductService;
import com.o2o.util.HttpServletRequstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
    @Autowired
    private ProductService productService;
    @RequestMapping(value = "/listproductdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>listProductDetailPageInfo(HttpServletRequest request){
        Map<String,Object>modleMap=new HashMap<>();
        Long productId= HttpServletRequstUtil.getLong(request,"productId");
        if (productId!=null){
            Product product = productService.getQueryProductByidService(productId);
            modleMap.put("product",product);
            modleMap.put("success",true);

        }else{
            modleMap.put("success",false);
            modleMap.put("errMsg","error listproductdetailpageinfo product");
        }
        return modleMap;
    }
}
