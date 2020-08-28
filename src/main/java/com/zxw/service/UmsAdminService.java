package com.zxw.service;

import com.zxw.mbg.model.UmsAdmin;
import com.zxw.mbg.model.UmsPermission;

import java.util.List;

public interface UmsAdminService {
    int deleteByPrimaryKey(Long id);

    int insert(UmsAdmin record);

    int insertSelective(UmsAdmin record);

    UmsAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsAdmin record);

    int updateByPrimaryKey(UmsAdmin record);

    /**
     * g根据用户名获取用户
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 根据用户id获取用户权限
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(Long adminId);
    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdmin umsAdmin);
    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);
}
