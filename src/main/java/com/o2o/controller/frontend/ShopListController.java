package com.o2o.controller.frontend;

import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.service.AreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopService;
import com.o2o.util.HttpServletRequstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;
    /**
     * 返回商品列表里的shopCategory列表（二级列表或者一级列表），以及区域信息列表
     * */
    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>listShopsPageInfo(HttpServletRequest request){
        Map<String,Object>modleMap=new HashMap<>();
        List<ShopCategory> shopCategoryList=new ArrayList<>();
        Long parentId= HttpServletRequstUtil.getLong(request,"parentId");

            if (parentId!=-1) {
                try {
                    ShopCategory shopCategoryCondition = new ShopCategory();
                    ShopCategory parent = new ShopCategory();
                    parent.setShopCategoryId(parentId);
                    shopCategoryCondition.setParent(parent);
                    shopCategoryList = shopCategoryService.getShopCategoryService(shopCategoryCondition);
                } catch (Exception e) {
                    modleMap.put("success", false);
                    modleMap.put("errMsg", e.getMessage());
                }
            }else {
                try {
                    //如果parentId不存在，则取出所有一级shopCategory
                    shopCategoryList = shopCategoryService.getShopCategoryService(null);

                } catch (Exception e) {
                    modleMap.put("success", false);
                    modleMap.put("errMsg", e.getMessage());
                }
            }

            modleMap.put("success",true);
            modleMap.put("shopCategoryList",shopCategoryList);

        try{
            List<Area> areaList=null;
            areaList=areaService.getAreaService();
            modleMap.put("areaList",areaList);
            modleMap.put("success",true);
            return modleMap;
        }catch (Exception e){
            modleMap.put("success",false);
            modleMap.put("errMsg",e.getMessage());
        }
        return modleMap;
    }
    /**
     * 获取指定条件下的店铺列表
     * */
    @RequestMapping(value = "/listshops",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>listShops(HttpServletRequest request){
        Map<String,Object>modleMap=new HashMap<>();
        //获取页码
        int pageIndex=HttpServletRequstUtil.getInt(request,"pageIndex");
        //获取每页显示的数据条数
        int pageSize=HttpServletRequstUtil.getInt(request,"pageSize");
        if (pageIndex>-1&&pageSize>-1){
            //获取一级类别id
            Long parentId=HttpServletRequstUtil.getLong(request,"parentId");
            //获取二级类别id
            Long shopCategoryId=HttpServletRequstUtil.getLong(request,"shopCategoryId");
            //获取区域id
            Integer areaId=HttpServletRequstUtil.getInt(request,"areaId");
            //获取模糊查询的名字
            String shopName=HttpServletRequstUtil.getString(request,"shopName");
            //获取组合之后的查询条件
            Shop ShopCondition=compaerToShopCondition(parentId,shopCategoryId,areaId,shopName);
            //根据查询条件和分页信息获取店铺列表，并返回总数
            ShopExecution shopExecution = shopService.getShopList(ShopCondition, pageIndex, pageSize);
            modleMap.put("shopList",shopExecution.getShopList());
            modleMap.put("count",shopExecution.getCount());
            modleMap.put("success",true);
        }else {
            modleMap.put("success",false);
            modleMap.put("errMsg","empty pageIndex or pageSize");
        }
        return modleMap;
    }

    private Shop compaerToShopCondition(Long parentId, Long shopCategoryId, Integer areaId, String shopName) {
        Shop shopCondition=new Shop();
        //查询某个一级shopCategory下的所有二级shopCategory的店铺列表
        if (parentId!=-1l){
            ShopCategory childCategory=new ShopCategory();
            ShopCategory parentCategory=new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId!=-1l){
            //查询某个二级shopCategory下面的店铺列表
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setShopCategoryId(shopCategoryId);
        shopCondition.setShopCategory(shopCategory);
        }
        if (areaId!=-1){
            //查询某个区域id下的所有店铺列表
            Area area=new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName!=null){
            //查询名字里包含shopName的店铺
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
