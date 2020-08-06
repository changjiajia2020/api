package com.fh.common;
// 返回接口的统一数据规范
public class JsonData {

    private Integer code;

    private String message;

    private Object data;

    private JsonData(Object data){
        this.data=data;
        this.code=200;
        this.message="success";
    }
    private JsonData(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    private JsonData(String message){
        this.code=500;
        this.message=message;
    }
    public static JsonData getJsonError(Integer code, String message){
        return new JsonData(code,message);
    }
    public static JsonData getJsonSuccess(Object data){
        return  new JsonData(data);
    }

    public static JsonData getJsonError(String message){
        return new JsonData(message);
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
