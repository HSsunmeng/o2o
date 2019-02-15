package com.o2o.dto;

import com.o2o.entity.Shop;
import com.o2o.enums.ShopStateEunm;

import java.io.InputStream;
import java.util.List;

public class ShopExecution {
    //结果状态
    private int state;
//状态标识
    private String stateInfo;
    //店铺数量
    private int count;
    //操作店铺Shop(店铺增删改查的时候用到)
    private Shop shop;
    //Shop列表（查看店铺的时候用）
    private List<Shop>shopList;
public ShopExecution(){

}
    public ShopExecution(Shop shop, InputStream inputStream, String name) {
    }
    /**
     * 店铺操作失败的时候使用的构造器
     * */
    public ShopExecution(ShopStateEunm shopStateEunm){
    this.state=shopStateEunm.getState();
    this.stateInfo=shopStateEunm.getStateInfo();
    }
    /**
     * 店铺操作成功的时候使用的构造器
     * */
    public ShopExecution(ShopStateEunm shopStateEunm,Shop shop){
        this.state=shopStateEunm.getState();
        this.stateInfo=shopStateEunm.getStateInfo();
        this.shop=shop;
    }
    /**
     * 店铺操作成功的时候使用的构造器
     * */
    public ShopExecution(ShopStateEunm shopStateEunm,List<Shop> shopList){
        this.state=shopStateEunm.getState();
        this.stateInfo=shopStateEunm.getStateInfo();
        this.shopList=shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
