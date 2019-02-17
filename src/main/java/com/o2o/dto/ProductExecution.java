package com.o2o.dto;

import com.o2o.entity.Product;
import com.o2o.enums.ProductStateEunm;

import java.util.List;

/**
 * 商品操作状态信息
 * */
public class ProductExecution {
        //状态码
    private  Integer state;
    //状态信息
    private String stateInfo;
    //商品数量
    private Integer count;
    //操作（product）增删改商品的时候用
    private Product product;
    //批量查询的时候用
    private List<Product>productList;
    public ProductExecution(){

    }
    /**
     * 操作失败的构造器
     * */
    public  ProductExecution(ProductStateEunm stateEunm){
        this.state=stateEunm.getState();
        this.stateInfo=stateEunm.getStateInfo();
    }
    /**
     * 操作成功的构造器
     * */
    public ProductExecution(ProductStateEunm stateEunm,Product product){
        this.state=stateEunm.getState();
        this.stateInfo=stateEunm.getStateInfo();
        this.product=product;
    }
    /**
     * 操作多条信息成功的构造器
     * */
    public ProductExecution(ProductStateEunm stateEunm,List<Product> productList){
        this.state=stateEunm.getState();
        this.stateInfo=stateEunm.getStateInfo();
        this.productList=productList;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
