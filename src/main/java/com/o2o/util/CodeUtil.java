package com.o2o.util;
import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 校队前端验证码
 * */
public class CodeUtil {
    public static boolean changeVerifyCode(HttpServletRequest request){
        String verifyCodeActual = HttpServletRequstUtil.getString(request,"verifyCodeActual");
        System.out.println(verifyCodeActual);
        String attribute = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (verifyCodeActual == null || !verifyCodeActual.equals(attribute)) {
            return false;
        }
        return true;


    }
}
