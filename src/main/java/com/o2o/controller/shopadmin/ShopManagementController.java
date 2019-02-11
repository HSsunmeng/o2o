package com.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.PersionInfo;
import com.o2o.entity.Shop;
import com.o2o.enums.ShopStateEunm;
import com.o2o.service.ShopService;
import com.o2o.util.HttpServletRequstUtil;
import com.o2o.util.ImgUtil;
import com.o2o.util.PathUtil;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @RequestMapping(value = "/regiterShop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object>registerShop(HttpServletRequest request)  {
        //接收并转化相应的参数，包括店铺信息以及图片信息
        Map<String ,Object>modleMap=new HashMap<>();
       String shopStr= HttpServletRequstUtil.getString(request,"shopStr");
        ObjectMapper objectMapper=new ObjectMapper();
       Shop shop=null;
        try{
            shop=objectMapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modleMap.put("success",false);
            modleMap.put("errMsg",e.getMessage());
            return modleMap;
        }
        CommonsMultipartFile shopImg=null;
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
            PersionInfo owner=new PersionInfo();
            owner.setUserId(1l);
            shop.setOwner(owner);
            File shopImgFile=new File(PathUtil.getImgBasePath()+ ImgUtil.getRandomName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                modleMap.put("success",false);
                modleMap.put("errMsg",e.getMessage());
                return modleMap;
            }
            try {

                getInputFile(shopImg.getInputStream(),shopImgFile);
            } catch (IOException e) {
                modleMap.put("success",false);
                modleMap.put("errMsg",e.getMessage());
                return modleMap;
            }
            ShopExecution shopExecution= shopService.addShop(shop,shopImgFile);
            if (shopExecution.getState()== ShopStateEunm.CHECK.getState()){
                modleMap.put("success",true);
            }else{
                modleMap.put("success",false);
                //返回错误状态
                modleMap.put("errMsg",shopExecution.getStateInfo());
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
    private static void getInputFile(InputStream inputStream, File file){
        OutputStream outputStream=null;
        try{
            outputStream=new FileOutputStream(file);
            int len=0;
            byte[]bytes=new byte[1024];
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
        }catch (IOException e){
        throw new RuntimeException("调用getInputFile出现异常"+e.getMessage());
        }finally {
            try{
                if (outputStream!=null){
                    outputStream.close();
                }
            }catch (IOException e){
                throw new RuntimeException("关闭OutputStream失败"+e.getMessage());
            }
            try{
                if (inputStream!=null){
                    inputStream.close();
                }
            }catch (IOException e){
                throw new RuntimeException("关闭InputStream失败"+e.getMessage());
            }
        }
    }
}
