package com.zxw.mbg.mapper;

import com.zxw.mbg.model.PmsAlbumPic;

public interface PmsAlbumPicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsAlbumPic record);

    int insertSelective(PmsAlbumPic record);

    PmsAlbumPic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsAlbumPic record);

    int updateByPrimaryKey(PmsAlbumPic record);
}