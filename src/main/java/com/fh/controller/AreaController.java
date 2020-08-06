package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.entity.po.Area;
import com.fh.service.AreaService;
import com.fh.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.List;

@Controller
@RequestMapping("area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    // 查询地区数据
    @RequestMapping("queryAreaList")
    @ResponseBody
    public JsonData queryAreaList(){
        List<Area> areaList = areaService.queryAreaList();
        return JsonData.getJsonSuccess(areaList);
    }

    //刷新缓存
    @RequestMapping("refreshRedis")
    @ResponseBody
    public JsonData refreshRedis(){
        List<Area> areaList = areaService.queryAreaList();
        //创建redis连接
        Jedis jedis = RedisUtils.getJedis();
        //字符串的用length  对象数组用size
        for (int i = 0; i <areaList.size() ; i++) {
            //获取循环的每个对象
            Area area = areaList.get(i);
            //转字符串
            String jsonString = JSONObject.toJSONString(area);
            //存入redis  用hash的类型（key ：fieid,value,fieid,value,fieid,value）
            jedis.hset("area_cjj",area.getId()+"",jsonString);
        }
        //把连接放进连接池里
        RedisUtils.returnJedis(jedis);
        return   JsonData.getJsonSuccess(areaList);
    }


}
