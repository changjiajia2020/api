package com.fh.annotation.annotationByLog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义的注解
// 声明你是修饰属性的    三个描述字段   列头   属性值  类型
@Target(ElementType.METHOD)  // field     annotation_type  constructor    local_varTable  method  package  parameter type
@Retention(RetentionPolicy.RUNTIME) // class  resource   runtime
public @interface LogFileds {
    String value(); // 获取在controller   @LogFileds("新增了节点")的信息
}
