package com.o2o.enums;

public enum ProductStateEunm {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失败"),EMPTY_LIST(-1002,"添加数少于1");
    private Integer state;
    private String stateInfo;
    ProductStateEunm(){

    }
    private ProductStateEunm(Integer state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }
    public static ProductStateEunm stateInfo(Integer index){
        for (ProductStateEunm state:values()){
            if (state.getState()==index){
                return state;
            }
        }
        return null;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
