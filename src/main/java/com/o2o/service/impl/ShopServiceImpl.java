package com.o2o.service.impl;

import com.o2o.dao.ShopDao;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;
import com.o2o.enums.ShopStateEunm;
import com.o2o.exceptions.ShopOperationException;
import com.o2o.service.ShopService;
import com.o2o.util.ImgUtil;
import com.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {
@Autowired
private ShopDao shopDao;

    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream ,String fileName) {
        /**
         * @if 空值判断
         * */
        if (shop==null){
            return new ShopExecution(ShopStateEunm.NULL_SHOP);

        }else if (shop.getArea()==null){
            return new ShopExecution(ShopStateEunm.NULL_AREA);
        }
        else if (shop.getShopCategory()==null){
            return new ShopExecution(ShopStateEunm.NULL_CATEGORY);

        }
        else if (shop.getOwner()==null){
            return new ShopExecution(ShopStateEunm.NULL_PERSIONINFO);
        }
        try{
            /**
             * 给店铺信息赋初始值
             * */
                shop.setEnableStatus(0);
                shop.setCreateTime(new Date());
                shop.setLastEditTime(new Date());
                /**
                 * 添加店铺信息
                 * */
                int effectedNum=shopDao.insertArea(shop);
                if (effectedNum <=0){
                    throw new ShopOperationException("店铺创建失败");
                }else{
                    if (shopImgInputStream!=null) {
                        //存入图片
                        try {
                            addShopImage(shop, shopImgInputStream,fileName);
                        }catch (Exception e){
                            throw new ShopOperationException("addShopImage error:"+e.getMessage());
                        }
                        //更新图片地址
                       effectedNum=shopDao.updateShop(shop);
                        if (effectedNum<=0){
                            throw new ShopOperationException("图片更新失败");
                        }
                    }
                }
        }catch (Exception e){
            throw new ShopOperationException("addShop error:"+e.getMessage());
        }

        return new ShopExecution(ShopStateEunm.CHECK,shop);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        //

        if (shop == null && shop.getShopId() == null) {
            return new ShopExecution(ShopStateEunm.NULL_SHOP);
        } else {
            //判断是否需要处理图片
            try {
                if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
                    Shop tempShop = shopDao.queryShop(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImgUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImage(shop, shopImgInputStream, fileName);
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEunm.INNER_ERROR);
                } else {
                    shop = shopDao.queryShop(shop.getShopId());
                    return new ShopExecution(ShopStateEunm.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error" + e.getMessage());
            }


        }
    }

    @Override
    public Shop getShopById(Long shopId) {
        return shopDao.queryShop(shopId);
    }

    private void addShopImage(Shop shop, InputStream shopImgshopImgInputStream,String fileName) {
        //获取shop图片目录的相对路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr= ImgUtil.generateThumbnails(shopImgshopImgInputStream,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }
}
