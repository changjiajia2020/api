package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.entity.vo.AddressInfo;
import com.fh.service.AddressService;
import com.fh.utils.redis.RedisUse;
import com.fh.utils.redis.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController // 相当于@ResponseBody + @Controller
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // 查看当前用户的收货地址 (根据当前用户查询收货地址)

    /**
     * 括号中，不用传用户id
     *      因为：前端shopcart.html页面，通过ajax全局设置中的
     *   'token': sessionStorage.getItem("login_token") 已经将token传过去了(把token放进sessionStorage中)
     *   token 相当于session，用来存放登录人的信息
     */
    /**
     * "[
     *      {\"detailAddressInfo\":\"中国,山西,郑州,河南思维\",\"id\":1,\"iphone\":\"13453511561\",\"isCheck\":1,\"name\":\"佳佳\"},
     *      {\"detailAddressInfo\":\"中国,山西,登封,中原\",\"id\":2,\"iphone\":\"123456\",\"isCheck\":0,\"name\":\"久违亦如初见\"}
     * ]"
     */
    // [{},{}]  将对象放到list中
    @RequestMapping("queryAddress")
    public JsonData queryAddress(){
        Jedis jedis = RedisUtils.getJedis();
        String address = jedis.get("address_cjj");
        if(StringUtils.isEmpty(address) ==true){
            // ??????
            List<AddressInfo> addressInfos = addressService.queryAddressInfoList();
            address = JSONObject.toJSONString(addressInfos);
            RedisUse.set("address_cjj",address);
            RedisUtils.returnJedis(jedis);
            return JsonData.getJsonSuccess(addressInfos);
        }
        return JsonData.getJsonSuccess(address);
    }
}
