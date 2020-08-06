package com.fh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration// 声明配置文件
@EnableSwagger2 // 声明swagger
public class SpringFoxConfig {

    // 具体的配置信息
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //  .any()有啥区别  .basePackage()
                .apis(RequestHandlerSelectors .basePackage("com.fh.controller"))
                // ant()可以指定扫描指定controller下的路径
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}
