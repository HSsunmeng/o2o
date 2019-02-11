package com.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequstUtil {
    public static int getInt(HttpServletRequest request,String key){
        try{
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }
    public static Long getLong(HttpServletRequest request,String key){
        try{
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1l;
        }
    }
    public static Double getDouble(HttpServletRequest request,String key){
        try{
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }
    public static boolean getBoolen(HttpServletRequest request,String key){
        try{
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }
    public static String getString(HttpServletRequest request,String key){
        try{
            String resout=request.getParameter(key);
            if (request!=null){
                return resout=resout.trim();
            }
            if ("".equals(resout)){
                return null;
            }
            return resout;
        }catch (Exception e){
            return null;
        }
    }
}
