package com.fh.common.interceptor;

import com.fh.common.exception.NologinException;
import com.fh.entity.po.Register;
import com.fh.utils.jwt.JWT;
import com.fh.utils.redis.RedisUse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

//登录的拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 因为：登录时，先通过jwt加密，然后又经过base64编码加密
     * 所以：解密时，也要先解base64,再解jwt
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从header里取数据               "token"与前端ajax全局设置headers对应
        String token = request.getHeader("token");
        //验证头信息是否完整
        if(StringUtils.isEmpty(token)){
           // throw new Exception("头信息不完整");
            //把你的请求路径记录一下  存入redis
            String requestURI = request.getRequestURI();
            throw new NologinException("没有登录");
        }
        //解密  字节数组
        byte[] decode = Base64.getDecoder().decode(token);
        // 得到字符串  字节数组转为字符串  字符串是什么格式  iphone+","+sign
        String signToken=new String(decode);
        //判断是否被篡改
        // iphone + "," + sign   分割之后，还剩iphone  sign    所以长度是2
        String[] split = signToken.split(",");
        if(split.length!=2){
            // throw new Exception("token格式不正确");
            throw new NologinException("没有登录");
        }
        String iphone = split[0];
        // jwt的秘钥
        String sign = split[1];
        // map和实体类区别
        // Map user = (Map) JWT.unsign(sign, Register.class);
        Register user = JWT.unsign(sign, Register.class);
        if(user==null){
            System.out.println("没有登录");
            //返回json字符串
            throw new NologinException("没有登录");
         /*   response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(JsonData.getJsonError(1000,"没有登录")));*/

        }
        if(user!=null){//jwt验证过了
            String sign_redis = RedisUse.get("token_" +iphone);
            if(!sign.equals(sign_redis)){//验证秘钥是不是最新
                //返回json字符串
                throw new NologinException("验证过期 重新登录");
            }

        }
        //前面逻辑验证过了  设置redis key值的有效时间 为30
        RedisUse.set("token_"+user.getIphone(),sign,60*30);
        //将用户信息放入request中  方便后面需求处理
// ！！！！！只有经过拦截器 才能将"login_user"放在request中，其他地方才能取出来！！！！！！
        request.setAttribute("login_user",user);
        return true;
    }


}
