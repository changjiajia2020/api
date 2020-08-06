package com.fh.controller;

import com.fh.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 若不写@Api(description = "这是测试类")，则默认是Test Controller
 */
@Api(description = "这是测试类") // 括号中，要加description,否则它无法起作用
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private UserService userService;
    @RequestMapping("test")
    @ApiOperation("测试方法")
    /**
     * paramType ="test"中test与下面的方法名称一致
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "name",value ="姓名",required = true,dataType ="java.lang.String",paramType ="test"),
            @ApiImplicitParam(name = "id",value = "ID",required = true,dataType ="java.lang.Integer",paramType ="test")
    })

    @ApiResponses({
            @ApiResponse(code = 404 ,message = "请求路径不对"),
            @ApiResponse(code = 1000 ,message = "用户没有登入")
    })
    /**
     * 括号中的参数  与上面name="name"中第一个对应
     */
    public List test(String name, Integer id){
        System.out.println("hello world");
        return userService.queryUserList();
    }

}
