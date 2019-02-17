package com.o2o.enums;

public enum ProductCategoryStateEunm {
SUCCESS(1,"创建成功"),INNER_ERROR(-1001,"操作失败"),EMPTY_LIST(-1002,"添加数少于1");
/**
 * state状态码
 * stateInfo 状态信息
 * */
private Integer state;
private String stateInfo;
   ProductCategoryStateEunm(){

}

   private  ProductCategoryStateEunm(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
    public static ProductCategoryStateEunm stateOF(Integer index){
       for (ProductCategoryStateEunm state:values()){
            if (state.getState()==index){
                return state;
            }
       }
       return null;
    }
}
