package com.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductStateEunm;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.service.ProductService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpServletRequstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    //最大上传的详情图片数量
    private static final Integer IMAGEMAXCOUNT = 6;
        @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
        @ResponseBody
    private Map<String, Object> productManageController(HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<>();
        //验证码校正
        if (!CodeUtil.changeVerifyCode(request)) {
            modleMap.put("success", false);
            modleMap.put("errMsg", "验证码输入错误");
            return modleMap;
        }
        //接收前端参数的变量：图片，缩略图。详情等信息
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequstUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        ImageHolder imageHolder = null;
        List<ImageHolder> imageHolderList = new ArrayList<>();
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //如果文件存在就取出文件
            if (commonsMultipartResolver.isMultipart(request)) {
                multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //取出缩略图构建imageHolder对象
                CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                imageHolder = new ImageHolder(commonsMultipartFile.getOriginalFilename(), commonsMultipartFile.getInputStream());
                //取出图片。并构建详情图,最大支持6张图片
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile  productFile= (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (productFile != null) {
                        //若图片不为空则加入到详情列表中
                      ImageHolder  tumbnail = new ImageHolder(productFile.getOriginalFilename(), productFile.getInputStream());
                        imageHolderList.add(tumbnail);
                    } else {
                        break;
                    }
                }
            } else {
                modleMap.put("success", false);
                modleMap.put("errMsg", "上传图片不能为空");
                return modleMap;
            }

        } catch (ProductOperationException e) {
            modleMap.put("success", false);
            modleMap.put("errMsg", e.toString());
            return modleMap;


        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
                //尝试前端穿过来的表单，并将其转换成producct实例
                product=objectMapper.readValue(productStr,Product.class);
            }catch (Exception e){
                modleMap.put("success",false);
                modleMap.put("errMsg",e.getMessage());
                return modleMap;
            }
            //若信息和图片不为空，则进行添加操作
        if (product!=null&&imageHolder!=null && imageHolderList.size()>0){
            try{
                //从session中获取前端传来的信息，减少对前端的依赖
                Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
                Shop shop=new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                //执行添加操作
                ProductExecution productExecution = productService.addProduct(product, imageHolder, imageHolderList);
                if (productExecution.getState()== ProductStateEunm.SUCCESS.getState()){
                    modleMap.put("success",true);
                }else {
                    modleMap.put("success", false);
                    modleMap.put("errMsg", productExecution.getStateInfo());
                    return modleMap;
                }
            }catch (ProductOperationException e){
                modleMap.put("success", false);
                modleMap.put("errMsg", e.toString());
            }
        }else {
            modleMap.put("success", false);
            modleMap.put("errMsg","信息不能为空");
            return modleMap;
        }
        return modleMap;
    }
}
