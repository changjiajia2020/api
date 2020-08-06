package com.fh.common.config;

import com.fh.common.interceptor.LoginInterceptor;
import com.fh.common.interceptor.TestInterceptor;
import com.fh.common.interceptor.TransdomainInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration  // 声明是配置文件类
/**
 * WebMvcConfigurer  总的拦截器类
 * 重写人家方法，然后注册一个拦截器。所以，要说清楚：注册哪个拦截器
 *  所以，new ()自己定义的拦截器类
 */
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        /**
         * InterceptorRegistration  总的注册中心
         * 将自定义的拦截器注册在：总的注册中心了
         */
        // 跨域拦截器
        InterceptorRegistration registration = registry.addInterceptor(new TransdomainInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截

        //cart之后的路径都被拦截
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new LoginInterceptor());
        interceptorRegistration.addPathPatterns("/cart/**");
        interceptorRegistration.addPathPatterns("/order/**");                      //order
        interceptorRegistration.addPathPatterns("/address/**");                    //address

        // 登录拦截器    "加入购物车"之后，所有操作都要进行登录拦截      拦截器拦截的是：所有的请求
 //       registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(Arrays.asList(
 //               "/type/queryTypeList",  // 查询类型     不需要拦截
  //             "/product/*",           // 查询商品相关内容
                                        // 查询购物车  需要拦截

 //               "/area/queryAreaList",  // 查询地区列表
 //               "/vip/**",               //登录
 //               "/**/success.html",            //html静态资源    不需要拦截
  //              "/**/index.html",            //html静态资源    不需要拦截
 //               "/**/commodity.html",            //html静态资源    不需要拦截
 //               "/**/details.html",            //html静态资源    不需要拦截
 //               "/**/*.js",              //js静态资源
 //               "/**/*.css"              //css静态资源
  //              )
  //     );

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        // 解决 SWAGGER 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
