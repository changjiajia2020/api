package com.fh.utils.message;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageUtils {
    public static String sendMsg(String iphoneNum){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G5PyCcM4uNNMvyQJPcp", "pktv6qV6TVGurt4BnP1QXSlNocUBgM");
        IAcsClient client = new DefaultAcsClient(profile);
        //生成随机的数字
        String fourRandom = getFourRandom();
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", iphoneNum);
        request.putQueryParameter("TemplateCode", "SMS_196654431");
        request.putQueryParameter("SignName", "ABC商城");
        Map map=new HashMap();
        map.put("code",fourRandom);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return fourRandom;
    }

    /**
     * 产生4位随机数(0000-9999)
     * @return 4位随机数
     */
    public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
            for(int i=1; i<=4-randLength; i++)
                fourRandom = "0" + fourRandom ;
        }
        return fourRandom;
    }

    public static void main(String[] args) {
        String s = sendMsg("13938433630");
        System.out.println(s);
    }
}
