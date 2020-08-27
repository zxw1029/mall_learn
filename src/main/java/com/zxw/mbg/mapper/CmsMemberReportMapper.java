package com.zxw.mbg.mapper;

import com.zxw.mbg.model.CmsMemberReport;

public interface CmsMemberReportMapper {
    int insert(CmsMemberReport record);

    int insertSelective(CmsMemberReport record);
}