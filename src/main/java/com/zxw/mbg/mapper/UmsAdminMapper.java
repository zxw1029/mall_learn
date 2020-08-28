package com.zxw.mbg.mapper;

import com.zxw.dto.UmsAdminExample;
import com.zxw.mbg.model.UmsAdmin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsAdminMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UmsAdmin record);

    int insertSelective(UmsAdmin record);

    UmsAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsAdmin record);

    int updateByPrimaryKey(UmsAdmin record);

    List<UmsAdmin> selectByExample(UmsAdminExample example);

    UmsAdmin getAdminByUsername(String username);
}