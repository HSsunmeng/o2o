package com.o2o.enums;

public enum ProductCategoryStateEunm {
    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),PASS(2,"审核通过"),INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"Shop为空"),NULL_AREA(-1004,"area店铺区域为空"),
    NULL_CATEGORY(-1005,"shopCategory店铺类别为空"),NULL_PERSIONINFO(-1006,"persion用户信息为空");

    private int state;
    private String stateInfo;
    private ProductCategoryStateEunm(int state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }
    /**
     * 根据传入的state返回相应的eunm值
     * */
    public static ProductCategoryStateEunm stateOf(int state){
        for (ProductCategoryStateEunm productCategoryStateEunm:values()){
            if (productCategoryStateEunm.getState()==state){
                return productCategoryStateEunm;
            }

        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
