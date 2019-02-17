package com.o2o.service.impl;

import com.o2o.dao.ProductDao;
import com.o2o.dao.ProductImgDao;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;

import com.o2o.entity.ProductImg;
import com.o2o.enums.ProductStateEunm;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.service.ProductService;
import com.o2o.util.ImageUtil;
import com.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    /**
     * 1:处理缩略图，获取缩略图相对路径并赋值给product
     * 2：往tb_product写入信息，并获取productid
     *3：结合productid批量处理详情图
     * 4：将商品详情图列表批量插入tb_product_img中
     * */
    @Override
    //添加事物
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> thumbnailList) throws ProductOperationException {
        //空值判断
        if (product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            //给商品添加默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //商品默认值上架
            product.setEnableStatus(1);
            //若商品缩略图不为空则添加
            if (thumbnail!=null){
                addTumbnail(product,thumbnail);
            }try{
                int insertProduct = productDao.insertProduct(product);
                if (insertProduct<=0){
                    throw new ProductOperationException("创建商品失败");
                }

            }catch (ProductOperationException e){
                throw new ProductOperationException("创建商品失败"+e.toString());
            }
            if (thumbnailList!=null&& thumbnailList.size()>0){
                addProductImg(product,thumbnailList);
            }
            return new ProductExecution(ProductStateEunm.SUCCESS);
        }else{
            return new ProductExecution(ProductStateEunm.INNER_ERROR);
        }

    }
/**
 * 批量添加图片
 * */
    private void addProductImg(Product product, List<ImageHolder> thumbnailList) {
        //获取图片的根路径
        String urlName=PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg>productImgList=new ArrayList<>();
        //遍历图片处理，并添加到productImg
        for (ImageHolder image:thumbnailList
             ) {
            String imageAddr=ImageUtil.generateThumbnails(image,urlName);
            ProductImg productImg=new ProductImg();
            productImg.setImgAddr(imageAddr);
            productImg.setProductId(product.getProductId());
            product.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果有图片需要添加就执行批量操作
        if (thumbnailList.size()>0){
          try{
              int insertProductImg = productImgDao.batchInsertProductImg(productImgList);
              if (insertProductImg<=0){
                  throw new ProductOperationException("添加图片失败");
              }
          }catch (Exception e){
              throw new ProductOperationException("操作失败"+e.getMessage());
          }
        }
    }
/**
 *
 * 添加缩略图
 * */
    private void addTumbnail(Product product, ImageHolder thumbnail) {
        String urlName= PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr= ImageUtil.generateThumbnails(thumbnail,urlName);
        product.setImgAddr(thumbnailAddr);
    }
}
