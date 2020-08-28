package com.zxw.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zxw.dao.UmsAdminRoleRelationDao;
import com.zxw.dto.AdminUserDetails;
import com.zxw.dto.UmsAdminExample;
import com.zxw.mbg.mapper.UmsAdminMapper;
import com.zxw.mbg.model.UmsAdmin;
import com.zxw.mbg.model.UmsPermission;
import com.zxw.mbg.model.UmsResource;
import com.zxw.service.UmsAdminService;
import com.zxw.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return umsAdminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UmsAdmin record) {
        return umsAdminMapper.insert(record);
    }

    @Override
    public int insertSelective(UmsAdmin record) {
        return umsAdminMapper.insertSelective(record);
    }

    @Override
    public UmsAdmin selectByPrimaryKey(Long id) {
        return umsAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UmsAdmin record) {
        return umsAdminMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UmsAdmin record) {
        return umsAdminMapper.updateByPrimaryKey(record);
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        return umsAdminMapper.getAdminByUsername(username);
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            System.out.println("userDetails.getPassword() == " + userDetails.getPassword());
//            UmsAdminExample example = new UmsAdminExample();
//            example.createCriteria().andUsernameEqualTo(username);
//            List<UmsAdmin> adminList = umsAdminMapper.selectByExample(example);
//            if (adminList == null || adminList.isEmpty()) {
//                throw new UsernameNotFoundException("用户名或密码错误");
//            }
//            UmsAdmin umsAdmin = adminList.get(0);
            UmsAdmin umsAdmin = getAdminByUsername(username);
            List<UmsPermission> getPermissionList = adminRoleRelationDao.getPermissionList(umsAdmin.getId());
            AdminUserDetails userDetails = new AdminUserDetails(umsAdmin,getPermissionList);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
//            LOGGER.warn("登录异常:{}", e.getMessage());
            System.out.println("登录异常:{"+e.getMessage()+"}" );
        }
        return token;
    }
}
