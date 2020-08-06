package com.fh.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestWeiPay {
    //统一下单   返回二维码url
    @Test
    public void test() throws Exception {
        // 微信支付  natvie   商户生成二维码
        //配置配置信息
        FeiConfig config = new FeiConfig();
            //得到微信支付对象
            WXPay wxpay = new WXPay(config);
            //设置请求参数
            Map<String, String> data = new HashMap<String, String>();
        /**
         * "body"       "fee_type"      "time_expire"       "total_fee"     "notify_url"    "out_trade_no"
         *      引号中的内容，都是固定写法！
         * "飞狐电商666-订单支付"  中的内容，只能由字母，数字，下划线组成
         */
        //对订单信息描述
            data.put("body", "飞狐电商666-订单支付");
            //String payId = System.currentTimeMillis()+"";  id+System.currentTimeMillis()
         // String s = "weixin1_order_" + 1 + System.currentTimeMillis();
        //设置订单号 （保证唯一 ）
            data.put("out_trade_no","weixin1_order_"+2134565433);
            //设置币种
            data.put("fee_type", "CNY");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            Date d=new Date();
            String dateStr = sdf.format(new Date(d.getTime() + 120000000));
            //设置二维码的失效时间
            data.put("time_expire", dateStr);
            //设置订单金额   单位分
            data.put("total_fee","1");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            //设置支付方式
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            // 统一下单
            Map<String, String> resp = wxpay.unifiedOrder(data);
            //resp.put("s",s);
        System.out.println("下订单结果为:"+ JSONObject.toJSONString(resp));
    }

    @Test //查询订单的状态
    public void test2() throws Exception {
        FeiConfig config = new FeiConfig();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no","weixin1_order_"+2134565432);
            // 查询支付状态
            Map<String, String> resp = wxpay.orderQuery(data);
        System.out.println("查询结果："+JSONObject.toJSONString(resp));
    }
}
