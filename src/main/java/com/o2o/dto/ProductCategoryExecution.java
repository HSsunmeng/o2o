package com.o2o.dto;

import com.o2o.entity.ProductCategory;
import com.o2o.enums.ProductCategoryStateEunm;

import java.util.List;

/**
 * 店铺类别状态信息
 * 操作成功和失败的信息
 * */
public class ProductCategoryExecution {
    //状态码
    private Integer state;
    //状态信息
    private String stateInfo;
    //状态列表
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }
        /**
         * 失败的时候使用的状态信息
         * */
    public ProductCategoryExecution(ProductCategoryStateEunm productCategoryStateEunm) {
        this.state = productCategoryStateEunm.getState();
        this.stateInfo = productCategoryStateEunm.getStateInfo();
    }
    /**
     * 操作成功的状态信息
     * */
    public ProductCategoryExecution(ProductCategoryStateEunm productCategoryStateEunm,List<ProductCategory>productCategoryList){
        this.state=productCategoryStateEunm.getState();
        this.stateInfo=productCategoryStateEunm.getStateInfo();
        this.productCategoryList=productCategoryList;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
