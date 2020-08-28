package com.zxw.controller;

import com.zxw.common.api.CommonResult;
import com.zxw.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "UmsMemberController",tags = "会员登录注册管理")
@RestController
@RequestMapping(value = "/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;
    //@RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）
//    语法：@RequestParam(value=”参数名”,required=”true/false”,defaultValue=””)
//    value：参数名
//    required：是否包含该参数，默认为true，表示该请求路径中必须包含该参数，如果不包含就报错。
//    defaultValue：默认参数值，如果设置了该值，required=true将失效，自动为false,如果没有传该参数，就使用默认值
    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam String telephone)
    {
        return  umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation(value = "判断验证码是否正确")
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    public CommonResult updatePassword(@RequestParam String telephone, @RequestParam String authCode)
    {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }
}
