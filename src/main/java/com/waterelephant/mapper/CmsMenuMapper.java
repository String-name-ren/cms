package com.waterelephant.mapper;

import com.waterelephant.entity.CmsMenu;

public interface CmsMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsMenu record);

    int insertSelective(CmsMenu record);

    CmsMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsMenu record);

    int updateByPrimaryKey(CmsMenu record);
}