package com.o2o.controller.frontend;

import com.o2o.entity.HeadLine;
import com.o2o.entity.ShopCategory;
import com.o2o.service.HeadLineService;
import com.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;
    @RequestMapping(value = "/listmainpageinfo")
    @ResponseBody
    private Map<String,Object>listMainPageInfo(){
        Map<String,Object>modleMao=new HashMap<>();
        List<ShopCategory>shopCategoryList=new ArrayList<>();
        try{
            shopCategoryList=shopCategoryService.getShopCategoryService(null);
            modleMao.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modleMao.put("success",false);
            modleMao.put("errMsg",e.getMessage());
            return modleMao;
        }
        List<HeadLine>headLineList=new ArrayList<>();
        try{
            HeadLine headLineCondition=new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineService(headLineCondition);
            modleMao.put("headLineList",headLineList);
        } catch (IOException e) {
            modleMao.put("success",false);
            modleMao.put("errMsg",e.getMessage());

            return modleMao;
        }
        modleMao.put("success",true);
        return modleMao;
    }
}
