package com.zxw.service.impl;

import com.zxw.common.api.CommonResult;
import com.zxw.service.RedisService;
import com.zxw.service.UmsMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
/**
 * 会员管理Service实现类
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}") //将yaml文件中配置的值赋值给该属性
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Override
    public CommonResult generateAuthCode(String telephone) {
        //生成随机验证码
        //将验证码绑定到手机号
        //设置验证码过期时间
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        System.out.println(sb.toString());
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+telephone,sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+telephone,AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(telephone,"获取验证码成功");
    }

    /**
     * 校验验证码是否正确
     * @param telephone
     * @param authCode
     * @return
     */
    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        //判断验证码是否为空
        if(StringUtils.isBlank(authCode))
        {
            return CommonResult.failed("请输入验证码");
        }
        //获取验证码
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE+telephone);
        //对比验证码
        if(StringUtils.equals(realAuthCode,authCode))
        {
            return CommonResult.success(null, "验证码校验成功");
        }
        return CommonResult.failed("验证码不正确");
    }
}
