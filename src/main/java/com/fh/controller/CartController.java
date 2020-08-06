package com.fh.controller;

import com.fh.common.JsonData;
import com.fh.entity.po.Cart;
import com.fh.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(description="购物车管理")
@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 加入购物车  (将商品加入购物车)
    @RequestMapping("addCart")
    @ResponseBody
    @ApiOperation("将商品加入购物车")
    public JsonData addCart(@RequestParam("id")Integer proId, Integer count){

        Integer count_type = cartService.addProductToCart(proId, count);
        return JsonData.getJsonSuccess(count_type);
    }

    // 根据用户查询购物车
    @RequestMapping("queryCartListByUser")
    @ResponseBody
    @ApiOperation("根据用户查询购物车")
    public JsonData queryCartListByUser(){
        List list = cartService.queryCartListByUser();
        return JsonData.getJsonSuccess(list);
    }

    // 从购物车中删除商品
    @RequestMapping("deleteCartFromProduct")
    @ResponseBody
    @ApiOperation("从购物车中删除此商品")
    public JsonData deleteCartFromProduct(Integer proId, HttpServletRequest request){
        Long count = cartService.deleteCartFromProduct(proId);
        return JsonData.getJsonSuccess(count);
    }

    // 更新购物车中商品的状态
    @RequestMapping("updateProductCartState")
    @ResponseBody
    public JsonData updateProductCartState(String pids){
        cartService.updateProductCartState(pids);
        return JsonData.getJsonSuccess("修改状态成功");
    }

    // 获取购物车中选中商品的信息
    @RequestMapping("queryCartCheckedProduct")
    @ResponseBody
    public JsonData queryCartCheckedProduct(){
        List<Cart> carts = cartService.queryCartCheckedProduct();
        return JsonData.getJsonSuccess(carts);
    }
}
