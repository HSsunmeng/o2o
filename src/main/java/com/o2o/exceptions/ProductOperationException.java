package com.o2o.exceptions;

public class ProductOperationException extends RuntimeException {

    private static final long serialVersionUID = -5769690046100336116L;
   public ProductOperationException(String msg){
       super(msg);
   }
}
