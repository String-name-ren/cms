package com.waterelephant.mapper;

import com.waterelephant.entity.CmsGlobalField;

public interface CmsGlobalFieldMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsGlobalField record);

    int insertSelective(CmsGlobalField record);

    CmsGlobalField selectByPrimaryKey(Integer id);

    CmsGlobalField getGlobalField();

    int updateByPrimaryKeySelective(CmsGlobalField record);

    int updateByPrimaryKey(CmsGlobalField record);
}