package com.fh.common;

import com.fh.common.exception.NologinException;
import com.fh.common.exception.NotEnoughStockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller报错  会跳转至
 */
//springmvc的全局异常处理
@ControllerAdvice
public class ControllerException {


    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public JsonData test(){
        return JsonData.getJsonError("参数为0");
    }


    // 没有登录异常
    @ExceptionHandler(NologinException.class)
    @ResponseBody
    public JsonData handleNoLoginException(NologinException e){
        return JsonData.getJsonError(1000,e.getMessage());
    }

    // 库存不足异常
    @ExceptionHandler(NotEnoughStockException.class) //
    @ResponseBody
    public JsonData handleCountException(NotEnoughStockException e){
        return JsonData.getJsonError(2000,e.getMessage());
    }

    /**
     * 处理所有不可知异常
     *
     * @param e 异常
     * @return json结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonData handleException(Exception e) {
        return JsonData.getJsonError(e.getMessage());
    }

    public static void main(String[] args) {
        int a=1/0;
    }

}
