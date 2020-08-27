package com.zxw.mbg.mapper;

import com.zxw.mbg.model.PmsBrand;
import com.zxw.mbg.model.PmsBrandExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsBrand record);

    int insertSelective(PmsBrand record);

    PmsBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsBrand record);

    int updateByPrimaryKeyWithBLOBs(PmsBrand record);

    int updateByPrimaryKey(PmsBrand record);

    List<PmsBrand> selectByExample(PmsBrandExample example);
}