package com.fh.controller;

import com.fh.common.JsonData;
import com.fh.utils.jwt.JWT;
import com.fh.utils.redis.RedisUse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Api(description="VIP管理")
@RestController
@RequestMapping("vip")
// @CrossOrigin("http://localhost:8085")   // 解决跨域问题
public class VipController {

    /**
     * 为什么  将验证码放在redis中
     *  因为：验证码有过期时间，恰巧redis可以设置过期时间
     *
     */
    @RequestMapping("sendMessage")
    @ApiOperation("发送短信")
    public JsonData sendMessage(String iphone){
        //发送短信   阿里提供的短信服务  必须连网!!!!!!
        //  String code = MessageUtils.sendMsg(iphone);
        // 每次在页面点击"验证码"之后，输入"1111",才能"登录成功";因为：如果"不点击验证码"，直接输入"1111",代表：根本都没有发送验证码
        // 但是：当你已经把当前用户存入到redis中时，可以直接输入验证码，然后也能成功
        String code="1111";
        //将验证码存到redis中            存了五分钟
        RedisUse.set(iphone+"_cjj",code,60*5);
        return JsonData.getJsonSuccess("短信发送成功");
    }

    //  status  message
    @RequestMapping("login")
    @ApiOperation("登录")
    public JsonData login(String iphone, String code, HttpServletRequest request){
        Map logMap=new HashMap();
        //正确将用户信息存入session中
        //获取code
        String redis_code = RedisUse.get(iphone+"_cjj");
        if(redis_code!=null&&redis_code.equals(code)){
            // 生成一个秘钥   对应一个信息
            Map user=new HashMap();
            user.put("iphone",iphone);
            String sign = JWT.sign(user,1000 * 60 * 60 * 24);
            //加签  手机号+sign值  目的是为了防止篡改数据
            String token = Base64.getEncoder().encodeToString((iphone + "," + sign).getBytes());

            // 将最新的密钥保存到redis中  因为：生成了多个密钥，但是我们只需要将最新的保存到redis中即可
            RedisUse.set("token_"+iphone,sign,60*30);
            logMap.put("status","200");
            logMap.put("message","登录成功");
            logMap.put("token",token);
        }else{
            logMap.put("status","300");
            logMap.put("message","用户不存在 或者 验证码错误");
        }
        return JsonData.getJsonSuccess(logMap);

    }

    //  根据手机号查询用户名
}
