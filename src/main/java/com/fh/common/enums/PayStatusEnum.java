package com.fh.common.enums;

/**
 * 可以理解为：一个java类
 *      key value(类似于map) 用枚举
 *
 *   枚举的组成：
 *      1. 私有的有参构造
 *      2. get set方法
 *      3. 建议用大写
 */
public enum PayStatusEnum {

    PAY_STATUS_INIT(0,"未支付"),
    PAY_STATUS_ING(1,"支付中"),
    PAY_STATUS_SUCCESS(2,"支付成功"),
    PAY_STATUS_Error(3,"支付异常")
    ;

    // 私有化属性
    private Integer status; // 状态
    private String message; // 信息

    // 私有的有参构造
    private PayStatusEnum(Integer status, String message){
        this.message=message;
        this.status=status;
    }

    // get set 方法
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
