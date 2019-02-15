package com.o2o.controller.shopadmin;

import  com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.PersionInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.enums.ShopStateEunm;
import com.o2o.service.AreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopService;
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
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>getShopManagementInfo(HttpServletRequest request){
        Map<String,Object>modleMap=new HashMap<>();
        Long shopId=HttpServletRequstUtil.getLong(request,"shopId");
            if (shopId<=0){
                Object carrentShopObj=request.getSession().getAttribute("carrentShopObj");
                    if (carrentShopObj==null){
                        modleMap.put("redirect",true);
                        modleMap.put("url","/o2o/shop/shoplist");
                    }else{
                        Shop carrentShop= (Shop) carrentShopObj;
                        modleMap.put("redirect",false);
                        modleMap.put("shopId",carrentShop.getShopId());
                    }
            }else {
                Shop carrentShop=new Shop();
                    carrentShop.setShopId(shopId);
                    request.getSession().setAttribute("carrentShop",carrentShop);
                    modleMap.put("redirect",false);
            }
            return modleMap;
    }

    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>getShopList(HttpServletRequest request){
        Map<String,Object>modleMap=new HashMap<>();
        PersionInfo user=new PersionInfo();
        user.setUserId(1l);
        user.setName("test");
        request.getSession().setAttribute("user",user);
        user= (PersionInfo) request.getSession().getAttribute("user");

        try{
            Shop shopCondition=new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shopCondition, 0, 100);
            modleMap.put("shopList",shopExecution.getShopList());
            modleMap.put("user",user);
            modleMap.put("success",true);
        }catch (Exception e){
            modleMap.put("success",false);
            modleMap.put("errMsg",e.getMessage());
        }
        return modleMap;
    }
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String ,Object>getShopById(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<>();
        Long shopId = HttpServletRequstUtil.getLong(request, "shopId");
        if (shopId>-1){
            try{
                Shop shop = shopService.getShopById(shopId);
                List<Area> areaList = this.areaService.getAreaService();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }
    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String ,Object>getShopInitInfo(){
        Map<String ,Object>modleMap=new HashMap<>();
            try{
                List<ShopCategory> shopCategoryServiceList=  shopCategoryService.getShopCategoryService(new ShopCategory());
                List<Area>  areaServiceList=areaService.getAreaService();
                modleMap.put("shopCategoryServiceList",shopCategoryServiceList);
                modleMap.put("areaServiceList",areaServiceList);
                modleMap.put("success",true);
            }catch (Exception e){
                modleMap.put("success",false);
                modleMap.put("errMsg",e.getMessage());
            }
            return modleMap;
    }
    /**
     * 注册店铺信息
     * */
    @RequestMapping(value = "/regitershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object>registersShop(HttpServletRequest request)  {
        Map<String ,Object>modleMap=new HashMap<>();

        if (!CodeUtil.changeVerifyCode(request)){
            modleMap.put("success",false);
            modleMap.put("errMsg","验证码错误");
            return modleMap;
        }
        //接收并转化相应的参数，包括店铺信息以及图片信息
       String shopStr= HttpServletRequstUtil.getString(request,"shopStr");
        ObjectMapper objectMapper=new ObjectMapper();
        Shop  shop=null;
        try{
            shop=objectMapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modleMap.put("success",false);
            modleMap.put("errMsg",e.getMessage());
            return modleMap;
        }
        CommonsMultipartFile shopImg;
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
            shopImg= (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else{
            modleMap.put("success",false);
            modleMap.put("errMsg","上传图片不能为空");
            return modleMap;
        }
        /**
         * 注册店铺
         * */
        if (shop !=null && shopImg!=null){
            PersionInfo owner= (PersionInfo) request.getSession().getAttribute("user");

            shop.setOwner(owner);
            ShopExecution  shopExecution;
            try {
                 shopExecution = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if (shopExecution.getState()== ShopStateEunm.CHECK.getState()){
                    modleMap.put("success",true);
                    //该用户可以操作的店铺列表
                    List<Shop>shopList= (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList==null|| shopList.size()==0) {
                        shopList = new ArrayList<>();
                    }
                        shopList.add(shopExecution.getShop());
                        request.getSession().setAttribute("shopList",shopList);

                }else{
                    modleMap.put("success",false);
                    //返回错误状态
                    modleMap.put("errMsg",shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modleMap.put("success",false);
                modleMap.put("errMsg",e.getMessage());
                return modleMap;
            }

            /**
             * 返回结果
             * */
            return modleMap;
        }else{
            modleMap.put("success",false);
            modleMap.put("errMsg","店铺信息不能为空");
            return modleMap;
        }

    }
    /**
     * 修改店铺信息
     * */
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object>modifyShop(HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<>();

        if (!CodeUtil.changeVerifyCode(request)) {
            modleMap.put("success", false);
            modleMap.put("errMsg", "验证码错误");
            return modleMap;
        }
        /**
         * 接收并转化相应的参数，包括店铺信息以及图片信息
         * */
        String shopStr = HttpServletRequstUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modleMap.put("success", false);
            modleMap.put("errMsg", e.getMessage());
            return modleMap;
        }
        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        /**
         * 修改店铺信息
         * */
        if (shop != null && shop.getShopId() != null) {

            ShopExecution shopExecution;
            try {
                if (shopImg == null){
                    shopExecution = shopService.modifyShop(shop, null, null);
                }else {
                    shopExecution = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                }

                if (shopExecution.getState() == ShopStateEunm.SUCCESS.getState()) {
                    modleMap.put("success", true);
                } else {
                    modleMap.put("success", false);
                    //返回错误状态
                    modleMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modleMap.put("success", false);
                modleMap.put("errMsg", e.getMessage());
                return modleMap;
            }

            /**
             * 返回结果
             * */
            return modleMap;
        } else {
            modleMap.put("success", false);
            modleMap.put("errMsg", "店铺ID不能为空");
            return modleMap;
        }
    }
//    private static void getInputFile(InputStream inputStream, File file){
//        OutputStream outputStream=null;
//        try{
//            outputStream=new FileOutputStream(file);
//            int len=0;
//            byte[]bytes=new byte[1024];
//            while ((len=inputStream.read(bytes))!=-1){
//                outputStream.write(bytes,0,len);
//            }
//        }catch (IOException e){
//        throw new RuntimeException("调用getInputFile出现异常"+e.getMessage());
//        }finally {
//            try{
//                if (outputStream!=null){
//                    outputStream.close();
//                }
//            }catch (IOException e){
//                throw new RuntimeException("关闭OutputStream失败"+e.getMessage());
//            }
//            try{
//                if (inputStream!=null){
//                    inputStream.close();
//                }
//            }catch (IOException e){
//                throw new RuntimeException("关闭InputStream失败"+e.getMessage());
//            }
//        }
//    }
}
