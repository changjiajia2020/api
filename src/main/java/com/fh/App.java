package com.fh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   // 声明是启动类
//@MapperScan("com.fh.dao")
public class App 
{
    /**
     * springApplication.run （当前类的类对象,main方法的参数）
     * 默认扫描路径  （启动类的目录下）
     */
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
