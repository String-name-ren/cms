package com.waterelephant.mapper;

import com.waterelephant.entity.SysUuid;

public interface SysUuidMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUuid record);

    int insertOrUpdate(SysUuid record);

    int insertSelective(SysUuid record);

    SysUuid selectByPrimaryKey(Long userId);

    String getUuid(Long userId);

    int updateByPrimaryKeySelective(SysUuid record);

    int updateByPrimaryKey(SysUuid record);
}