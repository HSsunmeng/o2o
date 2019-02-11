package com.o2o.exceptions;

public class ShopOperationException extends RuntimeException {
    private static final long serialVersionUID = -7034897190745766939L;
    public ShopOperationException(String msg){
        super(msg);
    }
}
