package com.fh.common.exception;

/**
 * 库存不足异常
 */
public class NotEnoughStockException extends Exception{

    // 参数咋确定啊？
    public NotEnoughStockException(String info){
        super(info);
    }
}
