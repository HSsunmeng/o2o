package com.o2o.entity;

import java.util.Date;
import java.util.List;

/**
 * 商品
 * */
public class Product {
    //商品id
    private Long productId;
    //商品名称
    private String productName;
    //商品描述
    private String productDesc;
    //图片地址
    private String imgAddr;
    //商品原价
    private String normalPrice;
    //折扣价
    private String promationPrice;
    //商品权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
    //-1不可用，0：下架，1：在前端展示系统展示
    //商品状态
    private Integer enableStatus;
    //商品详情图片列表
    private List<ProductImg>productImgList;
    //商品类别
    private ProductCategory productCategory;
    //对应 的店铺
    private Shop shop;

    public Long getProductId() {
        return productId;
    }

    public void setProduct(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getPromationPrice() {
        return promationPrice;
    }

    public void setPromationPrice(String promationPrice) {
        this.promationPrice = promationPrice;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
