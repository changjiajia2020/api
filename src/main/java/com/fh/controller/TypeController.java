package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.entity.po.Type;
import com.fh.service.TypeService;
import com.fh.utils.redis.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@Api(description="类型管理")
@RestController
@RequestMapping("type")
// @CrossOrigin("http://localhost:8085")   // 解决跨域问题
public class TypeController {

    @Autowired
    private TypeService typeService;

    // 查询所有数据       将数据存储在redis中
    @RequestMapping("queryTypeList")
    @ApiOperation("查询所有类型数据")
    public JsonData queryTypeList(){
/*      从数据库中查询数据
        List<Type> typeList = typeService.queryTypeList();
        return JsonData.getJsonSuccess(typeList);
 */
        Jedis jedis = RedisUtils.getJedis();
        String type_cjj = jedis.get("type_springboot");
        if(StringUtils.isEmpty(type_cjj)){
            // 说明：redis里面没有
            List<Type> typeList = typeService.queryTypeList();
            type_cjj = JSONObject.toJSONString(typeList);   // toJSONString把对象转为String
            jedis.set("type_springboot",type_cjj);
            RedisUtils.returnJedis(jedis);
            return JsonData.getJsonSuccess(typeList);
        }
        return JsonData.getJsonSuccess(type_cjj);
    }




}
