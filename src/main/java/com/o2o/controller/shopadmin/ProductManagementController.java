package com.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductStateEunm;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.service.ProductCategoryService;
import com.o2o.service.ProductService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpServletRequstUtil;
import com.o2o.util.PageCalculatro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
/**
 * 商品详情
 * */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    //最大上传的详情图片数量
    private static final Integer IMAGEMAXCOUNT = 6;
        @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
        @ResponseBody
        /**
         * 添加商品详情
         * */
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
        ImageHolder thumbnail = null;
        List<ImageHolder> imageHolderList = new ArrayList<>();
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //如果文件存在就取出文件
            if (commonsMultipartResolver.isMultipart(request)) {

                thumbnail = getImageHolder((MultipartHttpServletRequest) request, thumbnail, imageHolderList);
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
        if (product!=null&&thumbnail!=null && imageHolderList.size()>0){
            try{
                //从session中获取前端传来的信息，减少对前端的依赖
                Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
                Shop shop=new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                //执行添加操作
                ProductExecution productExecution = productService.addProduct(product, thumbnail, imageHolderList);
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
    /**
     * 根据id查询商品信息
     * 并查看店铺分类
     * */
    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>getProductQueryByid(@RequestParam Long productId){
        Map<String,Object>modleMap=new HashMap<>();
        try{
            if (productId > 0){
                //获取商品信息
                Product product = productService.getQueryProductByidService(productId);
                if (product != null){
                    //获取商品类别列表
                    List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
                    modleMap.put("product",product);
                    modleMap.put("productCategoryList",productCategoryList);
                    modleMap.put("success",true);
                }
            }else{
                modleMap.put("success",false);
                modleMap.put("errMsg","商品信息获取失败");
            }
        }catch (Exception e){
            modleMap.put("success",false);
            modleMap.put("errMsg",e.getMessage());
        }
        return modleMap;
    }
    /**
     * 更新店铺信息
     * */
    @RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object>modifyproduct(HttpServletRequest request){
        Map<String,Object>modleMap=new HashMap<>();
        //判断是商品编辑的时候调用，，还是下架的时候调用
        //若为前者则进行验证码判断，后者则跳过
        Boolean statusChange=HttpServletRequstUtil.getBoolen(request,"statusChange");
        if (!statusChange &&!CodeUtil.changeVerifyCode(request)){
            modleMap.put("success",false);
            modleMap.put("errMsg","验证码输入错误");
            return modleMap;
        }
//        接收前端初始化变量的初始化，缩略图，详情图等信息
            ObjectMapper objectMapper=new ObjectMapper();
        Product product=null;
        ImageHolder thumbnail=null;

        List<ImageHolder>imageHolderList=new ArrayList<>();
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
          try{
              if (commonsMultipartResolver.isMultipart(request)) {
                  thumbnail = getImageHolder(request, thumbnail, imageHolderList);
              }
          }catch (Exception e){
              modleMap.put("success",false);
              modleMap.put("errMsg",e.getMessage());
              return modleMap;
          }
          try{
              String productStr= HttpServletRequstUtil.getString(request,"productStr");
              //接收前端传送的string，并赋值给product实体类
           product=objectMapper.readValue(productStr,Product.class);
          }catch (Exception e){
            modleMap.put("success",false);
            modleMap.put("errMsg",e.getMessage());
            return modleMap;
          }
          if (product!=null){
              try{
                  //从session中获取 当前店铺的id并赋值给product，减少对前端的依赖
                  Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
                  product.setShop(currentShop);
                  ProductExecution productExecution = productService.updateProductService(product, thumbnail, imageHolderList);
                  if (productExecution.getState()==ProductStateEunm.SUCCESS.getState()){
                      modleMap.put("success",true);
                  }else {
                      modleMap.put("success",false);
                      modleMap.put("errMsg",productExecution.getStateInfo());
                      return modleMap;
                  }
              }catch (Exception e){
                  modleMap.put("success",false);
                  modleMap.put("errMsg",e.getMessage());
                  return modleMap;
              }
          }else {
              modleMap.put("success",false);
              modleMap.put("errMsg","更新商品失败");
              return modleMap;
          }
          return modleMap;
    }

    private ImageHolder getImageHolder(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> imageHolderList) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
        CommonsMultipartFile commonsMultipartFile= (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
        if (commonsMultipartFile!=null){
            thumbnail=new ImageHolder(commonsMultipartFile.getOriginalFilename(),commonsMultipartFile.getInputStream());
        }
        for (int i=0;i<IMAGEMAXCOUNT;i++) {
            CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
            if (thumbnailFile != null) {
                ImageHolder  thumbnailTo = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                imageHolderList.add(thumbnailTo);
            }else{
                break;
            }
        }
        return thumbnail;
    }
    @RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object>getProductListByShop(HttpServletRequest request){
        Map<String,Object>modleMap=new HashMap<>();
        //获取前台传过来的页码
        int pageIndex = HttpServletRequstUtil.getInt(request, "pageIndex");
        //获取每页显示的数据量
        int pageSize=HttpServletRequstUtil.getInt(request,"pageSize");
        //从session中获取店铺信息，主要获取shopid
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //空值判断
        if (pageIndex!=-1&&pageSize!=-1 &&currentShop!=null&&currentShop.getShopId()!=null){
            //获取要传入的检索条件，包括某个商品类别及模糊查询商品名。去筛选某店铺下的商品列表
            //筛选的条件可以进行排列组合
            Long productCategoryId=HttpServletRequstUtil.getLong(request,"productCategoryId");
            String productName=HttpServletRequstUtil.getString(request,"productName");
            Product productCondition=compaerToProductCondition(currentShop.getShopId(),productCategoryId,productName);
            //传入条件以及分页信息进行查询，返回相应的商品列表总数
            ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
            modleMap.put("productList",productExecution.getProductList());
            modleMap.put("count",productExecution.getCount());
            modleMap.put("success",true);
        }else {
            modleMap.put("success",false);
            modleMap.put("errMsg","getProductListByShop pageIndex pageSize currentShop");
            return modleMap;
        }
        return  modleMap;
    }

    private Product compaerToProductCondition(Long  currentShopId, Long productCategoryId, String productName) {
        Product product=new Product();
        Shop shop=new Shop();
       shop.setShopId(currentShopId);
       product.setShop(shop);
       //若有指定的类别，则添加进去
       if (productCategoryId!=-1){
           ProductCategory productCategory=new ProductCategory();
           productCategory.setProductCategoryId(productCategoryId);
           product.setProductCategory(productCategory);
       }
       //若有商品名称，则添加进行模糊查询
       if (productName!=null){
           product.setProductName(productName);
       }

        return product;
    }

}
