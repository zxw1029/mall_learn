package com.zxw.service.impl;

import com.zxw.mbg.mapper.PmsBrandMapper;
import com.zxw.mbg.model.PmsBrand;
import com.zxw.mbg.model.PmsBrandExample;
import com.zxw.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired
    private PmsBrandMapper pmsBrandMapper;
    @Override
    public List<PmsBrand> listAllBrand() {
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }
}
