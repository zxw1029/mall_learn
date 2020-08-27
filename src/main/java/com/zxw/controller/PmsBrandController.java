package com.zxw.controller;

import com.zxw.common.api.CommonResult;
import com.zxw.mbg.model.PmsBrand;
import com.zxw.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
@Api()
用于类；表示标识这个类是swagger的资源
        tags–表示说明
        value–也是说明，可以使用tags替代
        但是tags如果有多个值，会生成多个list
@ApiOperation() 用于方法；表示一个http请求的操作
        value用于方法描述
        notes用于提示内容
        tags可以重新分组（视情况而用）
@ApiParam() 用于方法，参数，字段说明；表示对参数的添加元数据（说明或是否必填等）
        name–参数名
        value–参数说明
        required–是否必填
@ApiModel()用于类 ；表示对类进行说明，用于参数用实体类接收
        value–表示对象名
        description–描述
        都可省略
@ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
        value–字段说明
        name–重写属性名字
        dataType–重写属性类型
        required–是否必填
        example–举例说明
        hidden–隐藏
@ApiIgnore()用于类或者方法上，可以不被swagger显示在页面上
@ApiImplicitParam() 用于方法
    表示单独的请求参数
@ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
    name–参数ming
    value–参数说明
    dataType–数据类型
    paramType–参数类型
    example–举例说明
*/
@Api(value = "PmsBrandController", tags = "商品品牌管理")
@RestController
@RequestMapping(value = "/brand")
public class PmsBrandController {
    @Autowired
    PmsBrandService pmsBrandService;

    @ApiOperation(value = "获取全部品牌列表")
    @ResponseBody
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<PmsBrand>> getList() {
        return CommonResult.success(pmsBrandService.listAllBrand());
    }
}
